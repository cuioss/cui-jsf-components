/**
 * <h2>Widget Component Models Package</h2>
 * <p>
 * Provides model interfaces and implementations for widget-based components in the CUI JSF library.
 * Widgets are self-contained UI components that display focused content with standardized
 * interaction patterns, commonly used on dashboards and overview pages.
 * </p>
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.model.widget.WidgetModel} - Base interface for widget components</li>
 *   <li>{@link de.cuioss.jsf.api.components.model.widget.BaseWidget} - Abstract base implementation with sensible defaults</li>
 *   <li>{@link de.cuioss.jsf.api.components.model.widget.BaseDeferredLoadingWidget} - Support for lazy-loaded widget content</li>
 *   <li>{@link de.cuioss.jsf.api.components.model.widget.DashboardWidgetModel} - Extension for dashboard integration</li>
 *   <li>{@link de.cuioss.jsf.api.components.model.widget.ListItem} - Interface for list-based widgets</li>
 *   <li>{@link de.cuioss.jsf.api.components.model.widget.ListItemWidgetModel} - Specialized widget for list items</li>
 * </ul>
 * 
 * <h3>Widget Architecture</h3>
 * <p>
 * The widget architecture follows a clear separation between:
 * </p>
 * <ul>
 *   <li><strong>Data Models</strong>: Classes in this package define the data structure and behavior</li>
 *   <li><strong>View Templates</strong>: Referenced by composite component IDs</li>
 *   <li><strong>Dashboard Integration</strong>: Through the DashboardWidgetModel interface</li>
 * </ul>
 * 
 * <h3>Widget Types</h3>
 * <p>
 * The framework supports several types of widgets:
 * </p>
 * <ul>
 *   <li><strong>Standard Widgets</strong>: Basic content display with optional actions</li>
 *   <li><strong>List Widgets</strong>: Display collections of related items</li>
 *   <li><strong>Deferred Loading Widgets</strong>: Load content asynchronously for better performance</li>
 * </ul>
 * 
 * <h3>Creating Custom Widgets</h3>
 * <p>
 * To create a custom widget:
 * </p>
 * <ol>
 *   <li>Extend {@link de.cuioss.jsf.api.components.model.widget.BaseWidget} or 
 *       {@link de.cuioss.jsf.api.components.model.widget.BaseDeferredLoadingWidget}</li>
 *   <li>Implement required methods (getId(), getTitle(), etc.)</li>
 *   <li>For dashboard integration, implement {@link de.cuioss.jsf.api.components.model.widget.DashboardWidgetModel}</li>
 *   <li>Create a corresponding composite component for rendering</li>
 * </ol>
 * 
 * <p>
 * Classes in this package provide thread safety when documented as such. Implementation 
 * classes should consider thread safety for their specific use cases.
 * </p>
 *
 * @author Matthias Walliczek
 * @author Oliver Wolff
 * @since 1.0
 * @see de.cuioss.jsf.api.components.model.lazyloading.LazyLoadingModel
 */
package de.cuioss.jsf.api.components.model.widget;