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
package de.cuioss.jsf.api.components.css;

/**
 * Interface defining a builder for constructing and manipulating CSS style class strings.
 * <p>
 * This interface provides a fluent API for dynamically modifying style class strings
 * by appending, removing, or toggling CSS classes. It's particularly useful when
 * building complex component styling that depends on runtime conditions.
 * </p>
 * <p>
 * The builder ensures proper handling of space-separated CSS classes, avoiding duplicates
 * and managing the combination of multiple style sources. All methods return the builder
 * instance to support method chaining.
 * </p>
 * <p>
 * Implementation note: Implementations of this interface should be thread-safe if they
 * are intended to be shared between threads.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * StyleClassBuilder builder = new StyleClassBuilderImpl();
 * builder.append("base-class")
 *        .appendIfTrue(ContextSize.MD, isResponsive)
 *        .append(otherBuilder)
 *        .toggle("active", isActive);
 * String cssClasses = builder.getStyleClass();
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see StyleClassProvider
 */
public interface StyleClassBuilder {

    /**
     * Retrieves the constructed style class string.
     * <p>
     * The returned string contains all the CSS classes that have been added to this builder,
     * properly formatted with space separators.
     * </p>
     *
     * @return the complete style class string, never {@code null} but may be empty if no
     *         style classes are present
     */
    String getStyleClass();

    /**
     * Checks if the builder contains any style classes.
     * <p>
     * This method can be used to determine if any style classes have been added to the builder.
     * </p>
     *
     * @return {@code true} if the builder contains at least one style class,
     *         {@code false} if it is empty
     */
    boolean isAvailable();

    /**
     * Appends a CSS class to the existing style classes.
     * <p>
     * If the provided style class is already present in the builder, it will not be added again.
     * This ensures that the resulting CSS class string doesn't contain duplicates.
     * </p>
     *
     * @param styleClass the CSS class to be appended, if {@code null} or empty it will be ignored
     * @return this builder instance for method chaining
     */
    StyleClassBuilder append(String styleClass);

    /**
     * Appends all style classes from another StyleClassBuilder.
     * <p>
     * This method combines the content of both builders while ensuring no duplicate
     * classes are added.
     * </p>
     *
     * @param styleClassBuilder the StyleClassBuilder whose classes should be appended,
     *                          if {@code null} or empty it will be ignored
     * @return this builder instance for method chaining
     */
    StyleClassBuilder append(StyleClassBuilder styleClassBuilder);

    /**
     * Appends styles from a StyleClassProvider.
     * <p>
     * This is a convenience method that extracts the style class from the provider
     * and appends it to this builder.
     * </p>
     *
     * @param styleClassProvider the provider of the style class to be appended,
     *                           if {@code null} or providing an empty value it will be ignored
     * @return this builder instance for method chaining
     * @see StyleClassProvider#getStyleClass()
     */
    StyleClassBuilder append(StyleClassProvider styleClassProvider);

    /**
     * Conditionally appends styles from a StyleClassProvider.
     * <p>
     * This method allows for conditional styling based on runtime conditions.
     * The style class is only appended if the specified condition is true.
     * </p>
     *
     * @param styleClassProvider the provider of the style class to be appended,
     *                           if {@code null} or providing an empty value it will be ignored
     * @param condition the boolean condition that determines whether the style class should be appended
     * @return this builder instance for method chaining
     */
    StyleClassBuilder appendIfTrue(StyleClassProvider styleClassProvider, boolean condition);

    /**
     * Removes a CSS class from the existing style classes.
     * <p>
     * If the specified style class is present in the builder, it will be removed.
     * </p>
     *
     * @param styleClass the CSS class to be removed, if {@code null} or empty it will be ignored
     * @return this builder instance for method chaining
     */
    StyleClassBuilder remove(String styleClass);

    /**
     * Removes all style classes from the specified StyleClassBuilder.
     * <p>
     * This method removes each class in the provided builder from this builder.
     * </p>
     *
     * @param styleClassBuilder the StyleClassBuilder whose classes should be removed,
     *                          if {@code null} or empty it will be ignored
     * @return this builder instance for method chaining
     */
    StyleClassBuilder remove(StyleClassBuilder styleClassBuilder);

    /**
     * Removes styles from a StyleClassProvider.
     * <p>
     * This is a convenience method that extracts the style class from the provider
     * and removes it from this builder.
     * </p>
     *
     * @param styleClassProvider the provider of the style class to be removed,
     *                           if {@code null} or providing an empty value it will be ignored
     * @return this builder instance for method chaining
     */
    StyleClassBuilder remove(StyleClassProvider styleClassProvider);

    /**
     * Toggles a CSS class in the existing style classes.
     * <p>
     * If the specified style class is present in the builder, it will be removed;
     * if it's not present, it will be added. This behavior is analogous to jQuery's
     * toggleClass() function.
     * </p>
     *
     * @param styleClass the CSS class to be toggled, if {@code null} or empty it will be ignored
     * @return this builder instance for method chaining
     */
    StyleClassBuilder toggle(String styleClass);

    /**
     * Toggles all style classes from the specified StyleClassBuilder.
     * <p>
     * For each class in the provided builder, if it's present in this builder it will be removed;
     * if it's not present, it will be added.
     * </p>
     *
     * @param styleClassBuilder the StyleClassBuilder whose classes should be toggled,
     *                          if {@code null} or empty it will be ignored
     * @return this builder instance for method chaining
     */
    StyleClassBuilder toggle(StyleClassBuilder styleClassBuilder);

    /**
     * Toggles styles from a StyleClassProvider.
     * <p>
     * This is a convenience method that extracts the style class from the provider
     * and toggles it in this builder.
     * </p>
     *
     * @param styleClassProvider the provider of the style class to be toggled,
     *                           if {@code null} or providing an empty value it will be ignored
     * @return this builder instance for method chaining
     */
    StyleClassBuilder toggle(StyleClassProvider styleClassProvider);
}
