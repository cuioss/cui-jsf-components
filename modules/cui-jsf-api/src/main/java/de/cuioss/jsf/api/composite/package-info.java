/**
 * <h2>Overview</h2>
 * <p>
 * Provides classes and utilities for simplifying the implementation and usage of JSF Composite
 * Components. This package focuses on type-safe access to component attributes and
 * streamlining common composite component patterns.
 * </p>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.composite.AttributeAccessor} - Core interface for accessing 
 *       component attributes in a type-safe manner</li>
 *   <li>{@link de.cuioss.jsf.api.composite.AttributeAccessorImpl} - Standard implementation 
 *       of the AttributeAccessor interface</li>
 *   <li>{@link de.cuioss.jsf.api.composite.accessor.StringAttributeAccessor} - Specialized accessor 
 *       for String attributes</li>
 *   <li>{@link de.cuioss.jsf.api.composite.accessor.BooleanAttributeAccessor} - Specialized accessor 
 *       for Boolean attributes</li>
 *   <li>{@link de.cuioss.jsf.api.composite.accessor.StyleClassAttributeAccessor} - Specialized accessor 
 *       for CSS style class attributes</li>
 * </ul>
 *
 * <h2>Usage Examples</h2>
 * <h3>Basic Attribute Access</h3>
 * <pre>
 * // Define an accessor for a String attribute named "styleClass"
 * private final AttributeAccessor&lt;String&gt; styleAccessor = 
 *     new AttributeAccessorImpl&lt;&gt;("styleClass", String.class, false);
 *     
 * // Access the attribute value
 * String styleClass = styleAccessor.value(getAttributes());
 *     
 * // Check if attribute is available
 * if (styleAccessor.available(getAttributes())) {
 *     // Use the attribute value
 * }
 * </pre>
 *
 * <h3>Using Specialized Accessors</h3>
 * <pre>
 * // For String attributes
 * private final StringAttributeAccessor labelAccessor = 
 *     new StringAttributeAccessor("label", false);
 *     
 * // For Boolean attributes
 * private final BooleanAttributeAccessor requiredAccessor = 
 *     new BooleanAttributeAccessor("required", false);
 * </pre>
 *
 * <h2>Best Practices</h2>
 * <ul>
 *   <li>Define attribute accessors as final fields in your component classes</li>
 *   <li>Use specialized accessors when available instead of the generic implementation</li>
 *   <li>Set alwaysResolve to true for dynamic attributes that may change during component lifecycle</li>
 *   <li>Set alwaysResolve to false for static attributes to improve performance</li>
 *   <li>Always check attribute availability before accessing values that might be null</li>
 * </ul>
 *
 * <h2>Related Packages</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components} - Core JSF component implementations</li>
 *   <li>{@link de.cuioss.jsf.api.components.base} - Base classes for JSF components</li>
 *   <li>{@link de.cuioss.jsf.api.components.partial} - Partial component implementations</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.composite;
