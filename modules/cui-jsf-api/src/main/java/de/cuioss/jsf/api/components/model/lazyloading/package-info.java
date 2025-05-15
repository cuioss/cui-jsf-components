/**
 * <h2>Lazy Loading Component Models Package</h2>
 * <p>
 * Provides interfaces and implementations for components that support deferred
 * (lazy) loading of content. This package enables the creation of more responsive
 * user interfaces by allowing components to defer resource-intensive operations
 * until they are actually needed.
 * </p>
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.model.lazyloading.LazyLoadingModel} - Base interface for lazy loading components</li>
 *   <li>{@link de.cuioss.jsf.api.components.model.lazyloading.LazyLoadingThreadModel} - Interface supporting asynchronous loading</li>
 * </ul>
 * 
 * <h3>Lazy Loading Patterns</h3>
 * <p>
 * This package supports two primary lazy loading patterns:
 * </p>
 * <ul>
 *   <li><strong>On-Demand Loading</strong> - Content is loaded when a component is first displayed or activated</li>
 *   <li><strong>Asynchronous Loading</strong> - Content is loaded in a background thread while displaying a placeholder</li>
 * </ul>
 * 
 * <h3>Integration with JSF Lifecycle</h3>
 * <p>
 * The lazy loading models integrate with the JSF request processing lifecycle through:
 * </p>
 * <ul>
 *   <li>ActionListener interface implementation for handling loading events</li>
 *   <li>Initialization state tracking to prevent redundant loading operations</li>
 *   <li>Support for notifications to display errors or loading status</li>
 * </ul>
 * 
 * <h3>Thread Safety Considerations</h3>
 * <p>
 * When using asynchronous loading via {@link de.cuioss.jsf.api.components.model.lazyloading.LazyLoadingThreadModel},
 * implementations must ensure thread safety as the model will be accessed concurrently
 * by the JSF thread and background loading threads.
 * </p>
 * 
 * <h3>Usage with Widget Models</h3>
 * <p>
 * These models are commonly used with the widget framework in 
 * {@link de.cuioss.jsf.api.components.model.widget} to create dashboard components
 * that load their content efficiently.
 * </p>
 *
 * @author Oliver Wolff
 * @author Matthias Walliczek
 * @since 1.0
 * @see de.cuioss.jsf.api.components.model.widget.BaseDeferredLoadingWidget
 * @see de.cuioss.uimodel.result.ResultObject
 */
package de.cuioss.jsf.api.components.model.lazyloading;