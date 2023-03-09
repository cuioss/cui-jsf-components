package com.icw.ehf.cui.components.bootstrap.tag.support;

import static de.cuioss.tools.collect.CollectionLiterals.mutableSortedSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import javax.faces.context.FacesContext;

import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.components.bootstrap.tag.TagComponent;
import com.icw.ehf.cui.components.bootstrap.taglist.TagComponentComparator;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import lombok.experimental.UtilityClass;

/**
 * @author Sven Haag
 */
@UtilityClass
public class TagHelper {

    /** Default exception message for an invalid value. */
    public static final String INVALID_VALUE_EXCEPTION =
        "Neither java.util.Collection, ConceptKeyType, nor String found for the value-attribute: {}";

    /**
     * @param types
     * @param locale
     * @param contentEscape
     * @param contextSize
     * @param contextState
     *
     * @return a {@link List} of {@link TagComponent}s representing the given ConceptKeys
     */
    public static List<TagComponent> createFromConceptKeys(final SortedSet<ConceptKeyType> types,
            final Locale locale,
            final boolean contentEscape,
            final String contextSize,
            final String contextState) {
        final List<TagComponent> result = new ArrayList<>();
        for (final ConceptKeyType type : types) {
            result.add(createFromConceptKey(type, locale, contentEscape, contextSize, contextState));
        }
        return result;
    }

    /**
     * @param type
     * @param locale
     * @param contentEscape
     * @param contextSize
     * @param contextState
     *
     * @return an {@link TagComponent} instance representing the given ConceptKey
     */
    public static TagComponent createFromConceptKey(final ConceptKeyType type,
            final Locale locale,
            final boolean contentEscape,
            final String contextSize,
            final String contextState) {
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
     * Creates an {@link ConceptKeyType} representation of a given {@link Object}.
     *
     * @param value to be used for creating a {@link ConceptKeyType} from.
     *
     * @return {@link Optional} with created {@link ConceptKeyType} if the the given element is a
     *         {@link String} or {@link ConceptKeyType} otherwise it will return
     *         {@link Optional#empty()}
     */
    public static Optional<ConceptKeyType> resolveFrom(final Object value) {
        if (value instanceof ConceptKeyType) {
            return Optional.of((ConceptKeyType) value);
        }
        if (value instanceof String) {
            var elementAsString = (String) value;
            if (elementAsString.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(ConceptKeyTypeImpl.builder()
                    .identifier(elementAsString)
                    .labelResolver(new I18nDisplayNameProvider(elementAsString))
                    .category(new MissingTagConceptKeyCategory()).build());
        }
        return Optional.empty();
    }

    /**
     * @param input submittedValue and/or value. First non-null input value is used.
     *
     * @return the {@link Set} of ConceptKeyType representing the actual value
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
            if (finalValue instanceof Collection<?>) {
                final Collection<?> set = (Collection<?>) finalValue;
                for (final Object element : set) {
                    values.add(resolveFrom(element).orElseThrow(
                            () -> new IllegalArgumentException(String.format(INVALID_VALUE_EXCEPTION,
                                    element))));
                }

            } else {
                values.add(resolveFrom(finalValue).orElseThrow(
                        () -> new IllegalArgumentException(String.format(INVALID_VALUE_EXCEPTION,
                                finalValue))));
            }
        }

        return values;

    }

    /**
     * @param context
     * @param writer
     * @param tagsToDisplay
     * @param style
     * @param styleClass
     *
     * @throws IOException
     */
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    public static void writeDisabled(final FacesContext context,
            final DecoratingResponseWriter<?> writer,
            final List<TagComponent> tagsToDisplay,
            final String style,
            final String styleClass)
        throws IOException {
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
