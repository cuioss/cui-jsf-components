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
package de.cuioss.jsf.api.components.css.impl;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.tools.string.Splitter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard implementation of the {@link StyleClassBuilder} interface.
 * <p>
 * This implementation manages a collection of CSS class names with the following features:
 * <ul>
 *   <li>Maintains a list of unique CSS class names</li>
 *   <li>Provides methods to append, remove, or toggle CSS classes</li>
 *   <li>Supports conditional class application with {@code appendIfTrue}</li>
 *   <li>Handles both single classes and space-separated class lists</li>
 *   <li>Properly formats the final style class string with space separators</li>
 * </ul>
 * 
 * <p>
 * The implementation automatically handles input sanitization:
 * <ul>
 *   <li>Trims all class names</li>
 *   <li>Filters out duplicates</li>
 *   <li>Ignores null or blank inputs</li>
 * </ul>
 * 
 * <p>
 * Usage example:
 * <pre>
 * // Create a builder with initial classes
 * StyleClassBuilder builder = new StyleClassBuilderImpl("btn btn-primary");
 * 
 * // Add conditional classes
 * builder.appendIfTrue(CssCommon.DISABLED, isDisabled);
 * 
 * // Add classes from another provider
 * builder.append(otherComponent);
 * 
 * // Remove classes
 * builder.remove("btn-primary").append("btn-secondary");
 * 
 * // Get the final space-separated class string
 * String classes = builder.getStyleClass();  // "btn btn-secondary disabled"
 * </pre>
 * 
 * <p>
 * This class is not thread-safe. Synchronization must be handled externally
 * if instances are shared between threads.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@EqualsAndHashCode
@ToString
public class StyleClassBuilderImpl implements StyleClassBuilder, Serializable {

    /**
     * Character used to separate CSS classes.
     */
    private static final char SEPARATOR = ' ';
    
    @Serial
    private static final long serialVersionUID = 8303808310618200785L;
    
    /**
     * Internal storage for CSS class names.
     * <p>
     * Each element represents a single CSS class name.
     * </p>
     */
    private final List<String> classes;

    /**
     * Default constructor that creates an empty builder.
     * <p>
     * Creates a new instance with no initial CSS classes.
     * </p>
     */
    public StyleClassBuilderImpl() {
        classes = new ArrayList<>();
    }

    /**
     * Constructor that initializes the builder with CSS classes.
     * <p>
     * Creates a new instance with the specified initial CSS classes.
     * If multiple classes are provided, they should be separated by spaces.
     * </p>
     *
     * @param initialClass to be added to the builder. Can be a single class or
     *                     multiple space-separated classes. May be null or empty.
     */
    public StyleClassBuilderImpl(final String initialClass) {
        this();
        safelyAddStyleClass(initialClass);
    }

    /**
     * Safely adds CSS class names to the internal collection.
     * <p>
     * This method handles null/blank checking, splitting space-separated classes,
     * trimming, and uniqueness enforcement.
     * </p>
     *
     * @param styleClass the CSS class name(s) to add, may be null, empty, or contain multiple
     *                   space-separated classes
     */
    private void safelyAddStyleClass(final String styleClass) {
        if (!MoreStrings.isBlank(styleClass)) {
            for (String splittedClass : Splitter.on(SEPARATOR).splitToList(styleClass.trim())) {
                var trimmedClass = splittedClass.trim();
                if (!classes.contains(trimmedClass)) {
                    classes.add(trimmedClass);
                }
            }
        }
    }

    /**
     * Safely removes CSS class names from the internal collection.
     * <p>
     * This method handles null/blank checking and splitting of space-separated classes.
     * </p>
     *
     * @param styleClass the CSS class name(s) to remove, may be null, empty, or contain multiple
     *                   space-separated classes
     */
    private void safelyRemoveStyleClass(final String styleClass) {
        if (!MoreStrings.isBlank(styleClass)) {
            for (String splittedClass : Splitter.on(SEPARATOR).splitToList(styleClass.trim())) {
                var trimmedClass = splittedClass.trim();
                classes.remove(trimmedClass);
            }
        }
    }

    /**
     * Toggles CSS class names in the internal collection.
     * <p>
     * If a class is present, it will be removed. If not present, it will be added.
     * This method handles null/blank checking and splitting of space-separated classes.
     * </p>
     *
     * @param styleClass the CSS class name(s) to toggle, may be null, empty, or contain multiple
     *                   space-separated classes
     */
    private void safelyToggleStyleClass(final String styleClass) {
        if (!MoreStrings.isBlank(styleClass)) {
            for (String splittedClass : Splitter.on(SEPARATOR).splitToList(styleClass.trim())) {
                var trimmedClass = splittedClass.trim();
                if (classes.contains(trimmedClass)) {
                    classes.remove(trimmedClass);
                } else {
                    classes.add(trimmedClass);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns all CSS classes as a single space-separated string.
     * If no classes are present, returns an empty string.
     * </p>
     * 
     * @return a space-separated string of CSS classes, or an empty string if no classes are present
     */
    @Override
    public String getStyleClass() {
        if (classes.isEmpty()) {
            return "";
        }
        return Joiner.on(SEPARATOR).skipNulls().join(classes);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Appends the given CSS class(es) to this builder. If the input contains multiple
     * space-separated classes, all will be added. Duplicate classes are ignored.
     * </p>
     * 
     * @param styleClass CSS class(es) to append, may be null or empty
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder append(final String styleClass) {
        safelyAddStyleClass(styleClass);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Appends all CSS classes from the given builder. If the input builder is null,
     * no changes are made.
     * </p>
     * 
     * @param styleClassBuilder builder whose classes should be appended, may be null
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder append(final StyleClassBuilder styleClassBuilder) {
        if (null != styleClassBuilder) {
            safelyAddStyleClass(styleClassBuilder.getStyleClass());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Appends CSS class(es) from the given provider. If the input provider is null,
     * no changes are made.
     * </p>
     * 
     * @param styleClassProvider provider whose CSS class(es) should be appended, may be null
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder append(final StyleClassProvider styleClassProvider) {
        if (null != styleClassProvider) {
            safelyAddStyleClass(styleClassProvider.getStyleClass());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Conditionally appends CSS class(es) from the given provider based on the condition.
     * The provider's classes are only added if the condition is true.
     * </p>
     * 
     * @param styleClassProvider provider whose CSS class(es) should be conditionally appended, may be null
     * @param condition the boolean condition determining if the class(es) should be appended
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder appendIfTrue(final StyleClassProvider styleClassProvider, final boolean condition) {
        if (condition) {
            append(styleClassProvider);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Checks if this builder has any CSS classes.
     * </p>
     * 
     * @return true if at least one CSS class has been added, false otherwise
     */
    @Override
    public boolean isAvailable() {
        return !classes.isEmpty();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Removes the specified CSS class(es) from this builder. If the input contains
     * multiple space-separated classes, all will be removed.
     * </p>
     * 
     * @param styleClass CSS class(es) to remove, may be null or empty
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder remove(final String styleClass) {
        safelyRemoveStyleClass(styleClass);
        return this;

    }

    /**
     * {@inheritDoc}
     * <p>
     * Removes all CSS classes from the given builder. If the input builder is null,
     * no changes are made.
     * </p>
     * 
     * @param styleClassBuilder builder whose classes should be removed, may be null
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder remove(final StyleClassBuilder styleClassBuilder) {
        if (null != styleClassBuilder) {
            safelyRemoveStyleClass(styleClassBuilder.getStyleClass());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Removes CSS class(es) from the given provider. If the input provider is null,
     * no changes are made.
     * </p>
     * 
     * @param styleClassProvider provider whose CSS class(es) should be removed, may be null
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder remove(final StyleClassProvider styleClassProvider) {
        if (null != styleClassProvider) {
            safelyRemoveStyleClass(styleClassProvider.getStyleClass());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Toggles the specified CSS class(es) in this builder. If a class is present, it will
     * be removed; if absent, it will be added. If the input contains multiple space-separated
     * classes, each will be toggled independently.
     * </p>
     * 
     * @param styleClass CSS class(es) to toggle, may be null or empty
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder toggle(final String styleClass) {
        safelyToggleStyleClass(styleClass);
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Toggles all CSS classes from the given builder. If the input builder is null,
     * no changes are made.
     * </p>
     * 
     * @param styleClassBuilder builder whose classes should be toggled, may be null
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder toggle(final StyleClassBuilder styleClassBuilder) {
        if (null != styleClassBuilder) {
            safelyToggleStyleClass(styleClassBuilder.getStyleClass());
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Toggles CSS class(es) from the given provider. If the input provider is null,
     * no changes are made.
     * </p>
     * 
     * @param styleClassProvider provider whose CSS class(es) should be toggled, may be null
     * @return this builder instance for method chaining
     */
    @Override
    public StyleClassBuilder toggle(final StyleClassProvider styleClassProvider) {
        if (null != styleClassProvider) {
            safelyToggleStyleClass(styleClassProvider.getStyleClass());
        }
        return this;
    }

}
