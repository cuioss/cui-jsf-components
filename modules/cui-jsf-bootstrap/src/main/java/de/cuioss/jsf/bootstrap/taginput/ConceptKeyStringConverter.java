/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.taginput;

import static de.cuioss.jsf.bootstrap.selectize.Selectize.CLIENT_CREATED_SUFFIX;
import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.collect.CollectionLiterals.*;
import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.jsf.bootstrap.tag.support.MissingTagConceptKeyCategory;
import de.cuioss.tools.codec.DecoderException;
import de.cuioss.tools.codec.Hex;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.tools.string.Splitter;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import java.util.*;

/**
 * Converter implementation that transforms between a collection of {@link ConceptKeyType} objects 
 * and a delimited String representation for use with the {@link TagInputComponent}.
 * 
 * <h2>Conversion Logic</h2>
 * <p>From objects to string: Hex-encodes identifier strings and joins them with the component's delimiter.</p>
 * <p>From string to objects: Splits by delimiter, matches with existing objects or creates new ones when needed.</p>
 * 
 * <h2>User-Created Tags</h2>
 * <p>Handles user-created tags by recognizing elements prefixed with 
 * {@link de.cuioss.jsf.bootstrap.selectize.Selectize#CLIENT_CREATED_SUFFIX}, creating appropriate 
 * {@link ConceptKeyType} objects, and maintaining them in the component's state.</p>
 * 
 * <h2>Security</h2>
 * <p>All user input is sanitized using {@link CuiSanitizer#PLAIN_TEXT} to prevent security issues.</p>
 * 
 * <h2>Usage Example</h2>
 * <pre>
 * &lt;boot:tagInput id="tags" 
 *               value="#{bean.selectedTags}"
 *               sourceSet="#{bean.availableTags}" 
 *               allowUserValues="true"&gt;
 *     &lt;f:converter converterId="ConceptKeyStringConverter" /&gt;
 * &lt;/boot:tagInput&gt;
 * </pre>
 *
 * @author Sven Haag
 * @since 1.0
 * @see TagInputComponent
 * @see ConceptKeyType
 * @see MissingTagConceptKeyCategory
 */
@FacesConverter("ConceptKeyStringConverter")
public class ConceptKeyStringConverter extends AbstractConverter<Collection<ConceptKeyType>> {

    private static final CuiLogger log = new CuiLogger(ConceptKeyStringConverter.class);

    /**
     * Converts a collection of ConceptKeyType objects to a delimited string.
     * 
     * @param context         FacesContext for the request being processed
     * @param component       The TagInputComponent instance, must not be null
     * @param conceptKeyTypes The collection to convert, may be null
     *
     * @return A string of delimiter-separated hex-encoded identifiers, or null if input is null
     * @throws NullPointerException if context or component is null
     * @throws ConverterException if the component is not a TagInputComponent
     * @throws IllegalArgumentException if the component's delimiter is null
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final Collection<ConceptKeyType> conceptKeyTypes) {
        requireNonNull(context);
        checkComponent(component);
        final var tagInputComponent = (TagInputComponent) component;
        final var delimiter = tagInputComponent.getDelimiter();
        checkArgument(null != delimiter, "delimiter must not be null");

        if (null == conceptKeyTypes) {
            return null;
        }

        final List<String> strings = new ArrayList<>(conceptKeyTypes.size());
        for (final ConceptKeyType codeType : conceptKeyTypes) {
            strings.add(Hex.encodeHexString(codeType.getIdentifier().getBytes()));
        }
        return Joiner.on(delimiter).skipNulls().join(strings);
    }

    /**
     * Validates that the provided component is a {@link TagInputComponent}.
     * <p>
     * This internal validation method ensures that the converter is used with
     * the correct component type.
     *
     * @param component The component to validate
     * @throws NullPointerException if component is null
     * @throws ConverterException if the component is not a TagInputComponent
     */
    private static void checkComponent(final UIComponent component) {
        requireNonNull(component);
        if (!(component instanceof TagInputComponent)) {
            final var msg = "Component must be of type " + TagInputComponent.class.getSimpleName();
            log.error(msg);
            throw new ConverterException(msg);
        }
    }

    /**
     * Converts a delimiter-separated string to a collection of ConceptKeyType objects.
     * Handles empty strings and creates new tags when necessary.
     *
     * @param context   FacesContext for the request being processed
     * @param component The TagInputComponent instance, must not be null
     * @param value     The string to convert, may be null or empty
     *
     * @return A sorted set of ConceptKeyType objects
     * @throws NullPointerException if context or component is null
     * @throws ConverterException if the component is invalid or value cannot be converted
     */
    @Override
    public SortedSet<ConceptKeyType> getAsObject(final FacesContext context, final UIComponent component,
            final String value) {
        requireNonNull(context);
        checkComponent(component);

        final var tagInput = (TagInputComponent) component;

        if (MoreStrings.isEmpty(value)) {
            return mutableSortedSet();
        }

        final Set<ConceptKeyType> previouslyClientCreated = immutableSet(tagInput.getClientCreated());
        final Set<ConceptKeyType> combinedSources = mutableSet(previouslyClientCreated);
        combinedSources.addAll(tagInput.getUndefinedValues());
        combinedSources.addAll(tagInput.getSourceSet());

        final SortedSet<ConceptKeyType> selectedCodedValues = mutableSortedSet();
        final Set<ConceptKeyType> clientCreated = mutableSet();
        final Iterable<String> splitted = Splitter.on(tagInput.getDelimiter()).splitToList(value);
        final var itemConverter = tagInput.getItemConverter();

        for (final String element : splitted) {
            if (!MoreStrings.isEmpty(element)) {
                selectedCodedValues.add(convertToConceptKeyType(element, combinedSources, clientCreated, context,
                        component, itemConverter));
            }
        }

        // Check whether there are additional elements added. if so update clientCreated
        if (!clientCreated.isEmpty()) {
            clientCreated.addAll(previouslyClientCreated);
            tagInput.setClientCreated(clientCreated);
        }

        return selectedCodedValues;
    }

    /**
     * Converts a single hex-encoded element to a ConceptKeyType object.
     * Attempts to find a match or create a new tag when needed.
     *
     * @param element The hex-encoded element to convert
     * @param source The collection of existing ConceptKeyType objects 
     * @param clientCreated The collection to add newly created tags to
     * @param context The current FacesContext
     * @param component The current component
     * @param itemConverter Optional converter for client-created items
     * 
     * @return The matching or newly created ConceptKeyType
     * @throws ConverterException if the element cannot be converted
     */
    private static ConceptKeyType convertToConceptKeyType(final String element, final Collection<ConceptKeyType> source,
            final Collection<ConceptKeyType> clientCreated, final FacesContext context, final UIComponent component,
            final Optional<Converter> itemConverter) {
        // find existing
        for (final ConceptKeyType codeType : source) {
            if (Hex.encodeHexString(codeType.getIdentifier().getBytes()).equals(element)) {
                return codeType;
            }
        }

        // create new custom tag
        final var userTag = createNewUserTagIfClientCreated(element, context, component, itemConverter);
        if (userTag.isPresent()) {
            // source.add(clientCreatedConceptKey);
            clientCreated.add(userTag.get());
            return userTag.get();
        }

        // create old custom tag
        try {
            final var decoded = new String(Hex.decodeHex(element));
            final var userTagDecoded = createNewUserTagIfClientCreated(decoded, context, component, itemConverter);
            if (userTagDecoded.isPresent()) {
                clientCreated.add(userTagDecoded.get());
                return userTagDecoded.get();
            }
        } catch (final DecoderException e) {
            log.error("Could not decode: " + element, e);
        }

        final var msg = "Unable to match element with name: " + element;
        log.error(msg);
        throw new ConverterException(msg);
    }

    /**
     * Creates a new user-defined ConceptKeyType if the provided value has the client-created prefix.
     * 
     * @param value Value to check and potentially convert
     * @param context The current FacesContext
     * @param component The current component
     * @param itemConverter Optional converter for custom conversion
     * 
     * @return Optional containing new ConceptKeyType if created, or empty if not client-created
     * @throws ConverterException if the value is client-created but invalid (empty name)
     */
    private static Optional<ConceptKeyType> createNewUserTagIfClientCreated(final String value,
            final FacesContext context, final UIComponent component, final Optional<Converter> itemConverter) {
        if (value.startsWith(CLIENT_CREATED_SUFFIX)) {
            final var name = value.substring(CLIENT_CREATED_SUFFIX.length());
            if (MoreStrings.isEmpty(name)) {
                final var msg = "Invalid input value found: " + value;
                log.error(msg);
                throw new ConverterException(msg);
            }

            return itemConverter.map(converter -> (ConceptKeyType) converter.getAsObject(context, component, name))
                    .or(() -> Optional.of(createMissingTagConceptKey(value, name)));
        }
        return Optional.empty();
    }

    /**
     * Creates a new ConceptKeyType representing a user-created tag with proper sanitization.
     * 
     * @param identifier The identifier for the ConceptKeyType, will be sanitized
     * @param label The display label for the ConceptKeyType, will be sanitized
     * 
     * @return A new ConceptKeyType instance with the sanitized values
     */
    private static ConceptKeyType createMissingTagConceptKey(final String identifier, final String label) {
        return ConceptKeyTypeImpl.builder().identifier(CuiSanitizer.PLAIN_TEXT.apply(identifier))
                .labelResolver(new I18nDisplayNameProvider(CuiSanitizer.PLAIN_TEXT.apply(label)))
                .category(new MissingTagConceptKeyCategory()).build();
    }
}
