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
/**
 * <h2>Overview</h2>
 * <p>
 * This package provides specialized implementations of {@link de.cuioss.jsf.api.composite.AttributeAccessor}
 * for common attribute types and usage patterns in JSF composite components.
 * These implementations simplify attribute access by providing type-specific functionality
 * and handling common use cases like default values.
 * </p>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.composite.accessor.BooleanAttributeAccessor} - Specialized accessor 
 *       for boolean attributes with default value support</li>
 *   <li>{@link de.cuioss.jsf.api.composite.accessor.StringAttributeAccessor} - Specialized accessor 
 *       for String attributes with empty-check functionality</li>
 *   <li>{@link de.cuioss.jsf.api.composite.accessor.StyleClassAttributeAccessor} - Specialized accessor 
 *       for CSS style class attributes with support for combining multiple classes</li>
 *   <li>{@link de.cuioss.jsf.api.composite.accessor.DefaultAwareAttributeAccessorImpl} - Base implementation 
 *       for accessors that support default values</li>
 *   <li>{@link de.cuioss.jsf.api.composite.accessor.DefaultAwareStringAccessorImpl} - Specialized accessor 
 *       for String attributes that supports default values</li>
 * </ul>
 *
 * <h2>Usage Examples</h2>
 * <h3>Boolean Attribute Access</h3>
 * <pre>
 * // Create an accessor with a default value of 'false'
 * BooleanAttributeAccessor disabledAccessor = 
 *     new BooleanAttributeAccessor("disabled", false);
 *     
 * // Access the attribute value (returns default if not set)
 * boolean isDisabled = disabledAccessor.value(getAttributes());
 * </pre>
 *
 * <h3>String Attribute Access</h3>
 * <pre>
 * // Create an accessor that checks for empty strings
 * StringAttributeAccessor labelAccessor = 
 *     new StringAttributeAccessor("label", true);
 *     
 * // Access the attribute value
 * String label = labelAccessor.value(getAttributes());
 * </pre>
 *
 * <h3>Style Class Attribute Access</h3>
 * <pre>
 * // Create an accessor for style classes with default value
 * StyleClassAttributeAccessor styleClassAccessor = 
 *     new StyleClassAttributeAccessor("styleClass", "default-class");
 *     
 * // Access the combined style classes
 * String combinedClasses = styleClassAccessor.value(getAttributes());
 * </pre>
 *
 * <h2>Best Practices</h2>
 * <ul>
 *   <li>Choose the most specific accessor type for your attribute to benefit from built-in functionality</li>
 *   <li>Use default values to ensure component graceful degradation when attributes are not specified</li>
 *   <li>For style classes, use the StyleClassAttributeAccessor to properly handle multiple CSS classes</li>
 *   <li>When working with optional String attributes, set checkForEmpty to true in StringAttributeAccessor</li>
 * </ul>
 *
 * <h2>Related Packages</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.composite} - Core composite component support</li>
 *   <li>{@link de.cuioss.jsf.api.components.css} - CSS and style handling utilities</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.composite.accessor;
