/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.tag.support;

import static de.cuioss.tools.collect.CollectionLiterals.mutableSortedSet;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.tag.TagComponent;
import de.cuioss.jsf.bootstrap.taglist.TagComponentComparator;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import jakarta.faces.context.FacesContext;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.*;

/**
 * Utility class for working with {@link TagComponent}s and {@link ConceptKeyType}s.
 * Provides methods for creating, converting, and rendering tag components.
 *
 * @author Sven Haag
 * @since 1.0
 */
@UtilityClass
public class TagHelper {

    /**
     * Default exception message for an invalid value.
     */
    public static final String INVALID_VALUE_EXCEPTION = "Neither java.util.Collection, ConceptKeyType, nor String found for the value-attribute: %s";

    /**
     * Creates a list of {@link TagComponent}s from a set of {@link ConceptKeyType}s.
     * 
     * @param types the concept keys to convert to tag components
     * @param locale the locale to use for resolving display values
     * @param contentEscape whether the tag content should be HTML-escaped
     * @param contextSize the Bootstrap size variant for the tags (e.g., "lg", "sm")
     * @param contextState the Bootstrap contextual state for the tags (e.g., "primary", "info")
     * @return a {@link List} of {@link TagComponent}s representing the given ConceptKeys
     */
    public static List<TagComponent> createFromConceptKeys(final SortedSet<ConceptKeyType> types, final Locale locale,
            final boolean contentEscape, final String contextSize, final String contextState) {
        final List<TagComponent> result = new ArrayList<>();
        for (final ConceptKeyType type : types) {
            result.add(createFromConceptKey(type, locale, contentEscape, contextSize, contextState));
        }
        return result;
    }

    /**
     * Creates a single {@link TagComponent} from a {@link ConceptKeyType}.
     * 
     * @param type the concept key to convert to a tag component
     * @param locale the locale to use for resolving the display value
     * @param contentEscape whether the tag content should be HTML-escaped
     * @param contextSize the Bootstrap size variant for the tag (e.g., "lg", "sm")
     * @param contextState the Bootstrap contextual state for the tag (e.g., "primary", "info")
     * @return a {@link TagComponent} instance representing the given ConceptKey
     */
    public static TagComponent createFromConceptKey(final ConceptKeyType type, final Locale locale,
            final boolean contentEscape, final String contextSize, final String contextState) {
        final var tagComponent = new TagComponent();
        tagComponent.setContentEscape(contentEscape);
        final var resolved = type.getResolved(locale);

        tagComponent.setContentValue(resolved);
        tagComponent.setTitleValue(resolved);

        tagComponent.setSize(contextSize);
        tagComponent.setState(contextState);
        return tagComponent;
    }

    /**
     * Creates a {@link ConceptKeyType} representation from a given object.
     * <p>
     * If the input is already a {@link ConceptKeyType}, it is returned as is.
     * If the input is a non-empty {@link String}, it is converted to a {@link ConceptKeyType}
     * with the string as both the identifier and display value.
     * </p>
     *
     * @param value the object to be converted to a {@link ConceptKeyType}
     * @return an {@link Optional} containing the created {@link ConceptKeyType} if successful,
     *         or {@link Optional#empty()} if conversion is not possible
     */
    public static Optional<ConceptKeyType> resolveFrom(final Object value) {
        if (value instanceof ConceptKeyType type) {
            return Optional.of(type);
        }
        if (value instanceof String elementAsString) {
            if (elementAsString.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(ConceptKeyTypeImpl.builder().identifier(elementAsString)
                    .labelResolver(new I18nDisplayNameProvider(elementAsString))
                    .category(new MissingTagConceptKeyCategory()).build());
        }
        return Optional.empty();
    }

    /**
     * Converts input objects to a {@link SortedSet} of {@link ConceptKeyType}s.
     * 
     * This method processes various input types:
     * <ul>
     *   <li>If the input is a {@link Collection}, each element is converted to a {@link ConceptKeyType}</li>
     *   <li>If the input is a single object, it is converted to a {@link ConceptKeyType}</li>
     * </ul>
     * The first non-null value from the input array is used.
     * 
     * @param input the values to process (typically submittedValue and/or value)
     * @return a {@link SortedSet} of {@link ConceptKeyType}s representing the actual value
     * @throws IllegalArgumentException if an input value cannot be converted to a {@link ConceptKeyType}
     */
    public static SortedSet<ConceptKeyType> getValueAsSet(Object... input) {
        final SortedSet<ConceptKeyType> values = mutableSortedSet();

        Object value = null;
        for (Object val : input) {
            if (null != val) {
                value = val;
                break;
            }
        }
        final var finalValue = value;
        if (null != finalValue) {
            if (finalValue instanceof Collection<?> set) {
                for (final Object element : set) {
                    values.add(resolveFrom(element).orElseThrow(
                            () -> new IllegalArgumentException(INVALID_VALUE_EXCEPTION.formatted(element))));
                }

            } else {
                values.add(resolveFrom(finalValue).orElseThrow(
                        () -> new IllegalArgumentException(INVALID_VALUE_EXCEPTION.formatted(finalValue))));
            }
        }

        return values;

    }

    /**
     * Renders a list of {@link TagComponent}s as disabled (read-only) HTML elements.
     * 
     * This method writes a UL element with Bootstrap's "list-inline" class and renders
     * each tag component as an LI element. The tags are sorted using {@link TagComponentComparator}
     * before rendering.
     *
     * @param context the current FacesContext
     * @param writer the response writer to use for output
     * @param tagsToDisplay the list of TagComponents to render
     * @param style optional CSS style to apply to the container
     * @param styleClass optional CSS class to append to the container
     * @throws IOException if an error occurs during writing to the response
     */
    public static void writeDisabled(final FacesContext context, final DecoratingResponseWriter<?> writer,
            final List<TagComponent> tagsToDisplay, final String style, final String styleClass) throws IOException {
        writer.withStartElement(Node.UL);
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();
        writer.withAttributeStyle(style);
        writer.withStyleClass(CssBootstrap.LIST_INLINE.getStyleClassBuilder().append(styleClass));

        tagsToDisplay.sort(new TagComponentComparator());
        for (final TagComponent tagComponent : tagsToDisplay) {
            writer.withStartElement(Node.LI);
            tagComponent.encodeAll(context);
            writer.withEndElement(Node.LI);
        }

        writer.withEndElement(Node.UL);
    }
}
