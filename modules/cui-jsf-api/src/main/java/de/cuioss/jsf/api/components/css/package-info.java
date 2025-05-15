/**
 * <h2>CSS Component Support Package</h2>
 * <p>
 * This package provides a comprehensive set of utilities and models for managing CSS styling
 * in JSF components. It includes classes for building style strings, defining contextual states,
 * resolving style classes, and working with icon libraries.
 * </p>
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.css.StyleClassBuilder} - Fluent API for building 
 *       CSS class strings with proper spacing and conditional addition</li>
 *   <li>{@link de.cuioss.jsf.api.components.css.StyleClassProvider} - Interface for components 
 *       that can provide CSS class strings</li>
 *   <li>{@link de.cuioss.jsf.api.components.css.StyleClassResolver} - Resolves style classes from 
 *       various inputs including strings and StyleClassProviders</li>
 *   <li>{@link de.cuioss.jsf.api.components.css.CssCommon} - Constants for common CSS classes 
 *       used throughout the framework</li>
 * </ul>
 * 
 * <h3>Contextual Styling Support</h3>
 * <p>
 * The package provides enums that map to Bootstrap's contextual styling system:
 * </p>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.css.ContextState} - Semantic states like SUCCESS, 
 *       WARNING, DANGER for color-based contextual styling</li>
 *   <li>{@link de.cuioss.jsf.api.components.css.ContextSize} - Size modifiers like LG, SM, XS 
 *       for component sizing</li>
 *   <li>{@link de.cuioss.jsf.api.components.css.AlignHolder} - Alignment options like LEFT, RIGHT, 
 *       CENTER for text and component alignment</li>
 * </ul>
 * 
 * <h3>Icon Support</h3>
 * <p>
 * {@link de.cuioss.jsf.api.components.css.IconLibrary} provides an abstraction for icon libraries,
 * enabling components to work with different icon sets like Font Awesome or Bootstrap Icons through
 * a consistent API.
 * </p>
 * 
 * <h3>Usage Example</h3>
 * <pre>
 * // Create a button style class with contextual state and size
 * StyleClassBuilder builder = new StyleClassBuilderImpl("btn");
 * builder.append(ContextState.PRIMARY.getStyleClassWithPrefix("btn"));
 * builder.appendIfNotEmpty(ContextSize.LARGE.getStyleClassWithPrefix("btn"));
 * 
 * // Add an icon
 * String iconClass = IconLibrary.FONT_AWESOME.getIconClass("user");
 * builder.append(iconClass);
 * 
 * String buttonClass = builder.getStyleClass(); // "btn btn-primary btn-lg fa fa-user"
 * </pre>
 * 
 * <p>
 * All classes in this package are thread-safe unless explicitly documented otherwise.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.components.css;
