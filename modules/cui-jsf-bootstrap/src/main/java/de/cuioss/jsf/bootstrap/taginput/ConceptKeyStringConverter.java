package de.cuioss.jsf.bootstrap.taginput;

import static de.cuioss.jsf.bootstrap.selectize.Selectize.CLIENT_CREATED_SUFFIX;
import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;
import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;
import static de.cuioss.tools.collect.CollectionLiterals.mutableSortedSet;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

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

/**
 * Converts a {@link ConceptKeyType} collection to {@link String} and vice versa
 * utilizing the {@link TagInputComponent}.
 *
 * @author Sven Haag
 */
@FacesConverter("ConceptKeyStringConverter")
public class ConceptKeyStringConverter extends AbstractConverter<Collection<ConceptKeyType>> {

    private static final CuiLogger log = new CuiLogger(ConceptKeyStringConverter.class);

    /**
     * @param context         {@link FacesContext} for the request being processed
     * @param component       {@link TagInputComponent}
     * @param conceptKeyTypes the values to be converted
     *
     * @return String of {@link TagInputComponent#getDelimiter()} separated
     *         identifiers
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

    private static void checkComponent(final UIComponent component) {
        requireNonNull(component);
        if (!(component instanceof TagInputComponent)) {
            final var msg = "Component must be of type " + TagInputComponent.class.getSimpleName();
            log.error(msg);
            throw new ConverterException(msg);
        }
    }

    /**
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link TagInputComponent}
     * @param value     to be converted
     *
     * @return ConceptKeyTypes
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

    private static Optional<ConceptKeyType> createNewUserTagIfClientCreated(final String value,
            final FacesContext context, final UIComponent component, final Optional<Converter> itemConverter) {
        if (value.startsWith(CLIENT_CREATED_SUFFIX)) {
            final var name = value.substring(CLIENT_CREATED_SUFFIX.length());
            if (MoreStrings.isEmpty(name)) {
                final var msg = "Invalid input value found: " + value;
                log.error(msg);
                throw new ConverterException(msg);
            }

            return itemConverter
                    .map(converter -> Optional.of((ConceptKeyType) converter.getAsObject(context, component, name)))
                    .orElseGet(() -> Optional.of(createMissingTagConceptKey(value, name)));
        }
        return Optional.empty();
    }

    private static ConceptKeyType createMissingTagConceptKey(final String identifier, final String label) {
        return ConceptKeyTypeImpl.builder().identifier(CuiSanitizer.PLAIN_TEXT.apply(identifier))
                .labelResolver(new I18nDisplayNameProvider(CuiSanitizer.PLAIN_TEXT.apply(label)))
                .category(new MissingTagConceptKeyCategory()).build();
    }
}
