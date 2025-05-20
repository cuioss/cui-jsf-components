/**
 * <p>
 * This package provides property wrapper classes for JSF composite component metadata.
 * These wrappers standardize access to different types of component properties and
 * organize their metadata for display in development tools and documentation.
 * </p>
 * 
 * <p>
 * The package implements a wrapper hierarchy based on {@link de.cuioss.jsf.dev.metadata.composite.attributes.AbstractPropertyWrapper}
 * to handle different types of component properties:
 * </p>
 * <ul>
 *   <li>Attributes - Standard component configuration properties</li>
 *   <li>Facets - Named insertion points for child components</li>
 *   <li>Attached Objects - Validators, converters, and behaviors that can be attached to components</li>
 *   <li>Root Component - The composite component itself</li>
 * </ul>
 * 
 * <h2>Key Classes</h2>
 * 
 * <h3>AbstractPropertyWrapper</h3>
 * <p>
 * The base class for all property wrappers, providing common functionality for extracting
 * and organizing property metadata from {@link java.beans.FeatureDescriptor} objects.
 * </p>
 * 
 * <h3>PropertyType</h3>
 * <p>
 * An enum defining the different types of properties that can be represented by the wrappers:
 * ATTRIBUTE, FACET, ATTACHED_OBJECT, ROOT, and OTHER.
 * </p>
 * 
 * <h3>Concrete Wrapper Implementations</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite.attributes.AttributePropertyWrapper} - For standard attributes</li>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite.attributes.FacetPropertyWrapper} - For facets</li>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite.attributes.AttachedObjectPropertyWrapper} - For validators, converters, etc.</li>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite.attributes.RootComponentPropertyWrapper} - For the root component</li>
 * </ul>
 * 
 * <h3>Support Classes</h3>
 * <p>
 * {@link de.cuioss.jsf.dev.metadata.composite.attributes.ComponentPropertiesWrapper} provides a convenient
 * wrapper for all properties of a component, organizing them by type.
 * </p>
 * 
 * <h2>Usage Example</h2>
 * <pre>{@code
 * // Get component descriptor
 * BeanInfo beanInfo = Introspector.getBeanInfo(component.getClass());
 * PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
 * 
 * // Wrap properties
 * List<AbstractPropertyWrapper> wrappers = new ArrayList<>();
 * for (PropertyDescriptor descriptor : propertyDescriptors) {
 *     wrappers.add(new AttributePropertyWrapper(descriptor));
 * }
 * 
 * // Access metadata
 * for (AbstractPropertyWrapper wrapper : wrappers) {
 *     List<LabelValueDisplay> metadata = wrapper.getDisplayData();
 *     PropertyType type = wrapper.getPropertyType();
 *     
 *     // Process metadata
 * }
 * }</pre>
 * 
 * <h2>Integration with Component Documentation</h2>
 * <p>
 * These wrapper classes are used in the CUI component development tools to:
 * </p>
 * <ul>
 *   <li>Generate documentation for component attributes</li>
 *   <li>Provide metadata displays in development environments</li>
 *   <li>Perform quality assurance checks on component definitions</li>
 *   <li>Create standardized property displays for sample applications</li>
 * </ul>
 * 
 * <h2>Related Packages</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.dev.metadata} - Core metadata classes</li>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite} - Composite component metadata</li>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite.util} - Utility classes for metadata display</li>
 * </ul>
 *
 * @since 1.0
 */
package de.cuioss.jsf.dev.metadata.composite.attributes;
