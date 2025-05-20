/**
 * <h2>JSF View Object Representation</h2>
 * <p>
 * This package provides a unified object-oriented approach for representing JSF views
 * at runtime. The key abstractions and implementations enable applications to work with
 * views as first-class objects rather than simple strings.
 * </p>
 * 
 * <h3>Core Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.common.view.ViewDescriptor}: Interface defining 
 *       the contract for objects that represent a JSF view, including its identifiers
 *       and associated URL parameters</li>
 *   <li>{@link de.cuioss.jsf.api.common.view.ViewDescriptorImpl}: Standard implementation
 *       of the ViewDescriptor interface, providing immutable view representation with
 *       utility methods for parameter filtering</li>
 * </ul>
 * 
 * <h3>Key Features</h3>
 * <ul>
 *   <li>Unified access to physical and logical view IDs</li>
 *   <li>Support for view-associated URL parameters</li>
 *   <li>Parameter filtering capabilities</li>
 *   <li>Automatic generation of CSS-friendly view identifiers</li>
 *   <li>Immutable implementation for thread safety</li>
 *   <li>Builder pattern for convenient object creation</li>
 * </ul>
 * 
 * <h3>Usage Scenarios</h3>
 * <p>
 * The ViewDescriptor model is particularly useful for:
 * </p>
 * <ul>
 *   <li>Navigation components and services that need to track the current view</li>
 *   <li>View-specific styling and rendering logic</li>
 *   <li>Integration with view matching services (see application.view.matcher package)</li>
 *   <li>Maintaining view state across user interactions</li>
 *   <li>Managing view parameters in a type-safe manner</li>
 * </ul>
 * 
 * <h3>Usage Example</h3>
 * <pre>
 * // Create a view descriptor
 * ViewDescriptor descriptor = ViewDescriptorImpl.builder()
 *     .withViewId("/pages/user/profile.xhtml")
 *     .withLogicalViewId("/pages/user/profile.jsf")
 *     .withUrlParameter(List.of(new UrlParameter("userId", "123")))
 *     .build();
 *     
 * // Use the descriptor
 * String cssClass = "view-" + descriptor.getShortIdentifier();
 * String linkUrl = descriptor.getLogicalViewId();
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see de.cuioss.jsf.api.application.view.matcher
 */
package de.cuioss.jsf.api.common.view;
