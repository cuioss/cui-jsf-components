package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;
import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import de.cuioss.tools.logging.CuiLogger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Provides the facets supported by {@link LabeledContainerComponent}
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ContainerFacets {

    /** Appends an element to the wrapped input. Usually used for Buttons. */
    APPEND("append"),

    /** Prepends an element to the wrapped input. Usually used for Buttons. */
    PREPEND("prepend"),

    /** Used for providing custom labels. */
    LABEL("label"),

    /** Used for providing custom help texts. */
    HELP_TEXT("helpText");

    private static final CuiLogger log = new CuiLogger(ContainerFacets.class);

    @Getter
    private final String name;

    /**
     * @param name must not be null
     * @return the found {@link ContainerFacets}. In case it can not be found it returns an
     *         {@link Optional#empty()}
     */
    public static final Optional<ContainerFacets> parse(String name) {
        if (isEmpty(name)) {
            return Optional.empty();
        }
        for (ContainerFacets facet : values()) {
            if (name.equalsIgnoreCase(facet.name)) {
                return Optional.of(facet);
            }
        }
        log.error("Invalid name given, expected one of '{}' (Case Insensitive)", Arrays.asList(values()));
        return Optional.empty();
    }

    /**
     * @param name of the facet to be resolved. Must be one of 'append' or 'prepend'
     * @param attributeName this facet is resolved for: used for creating message
     * @return the resolved facet, throw an {@link IllegalArgumentException} if it can not be
     *         resolved
     */
    public static final ContainerFacets parseButtonAlign(String name, String attributeName) {
        var resolved = parse(name);
        if (!resolved.isPresent() || !INPUT_DECORATOR.contains(resolved.get())) {
            throw new IllegalArgumentException(
                    "The attribute '" + attributeName + "' must either be 'append' or 'prepend' but was: " + name);
        }
        return resolved.get();
    }

    /** Defines decorator for the input: {@link #PREPEND} and {@link #APPEND} . */
    @SuppressWarnings("squid:S2386") // owolff: False positive -> immutable
    public static final Set<ContainerFacets> INPUT_DECORATOR = immutableSet(APPEND, PREPEND);
}
