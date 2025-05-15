/**
 * <h2>View Matcher API</h2>
 * <p>
 * This package provides a strategy pattern implementation for matching JSF views against
 * application-defined criteria. The view matchers can be used to determine if a view
 * (represented by a {@link de.cuioss.jsf.api.common.view.ViewDescriptor}) matches
 * specific criteria, which are typically configured at the application level.
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.application.view.matcher.ViewMatcher}: The core interface
 *       defining the strategy pattern for view matching.</li>
 *   <li>{@link de.cuioss.jsf.api.application.view.matcher.ViewMatcherImpl}: The standard
 *       implementation that matches views based on path prefixes.</li>
 *   <li>{@link de.cuioss.jsf.api.application.view.matcher.EmptyViewMatcher}: A constant
 *       matcher that always returns the same result.</li>
 *   <li>{@link de.cuioss.jsf.api.application.view.matcher.OutcomeBasedViewMatcher}: A matcher
 *       that compares views based on navigation outcomes.</li>
 * </ul>
 * 
 * <h3>Use Cases</h3>
 * <p>
 * View matchers serve several important functions in JSF applications:
 * <ul>
 *   <li>Determining if views belong to specific application sections</li>
 *   <li>Filtering views based on URL patterns</li>
 *   <li>Applying security constraints to groups of views</li>
 *   <li>Customizing UI components based on the current view</li>
 *   <li>Implementing conditional navigation logic</li>
 * </ul>
 * 
 * <h3>Implementation Examples</h3>
 * <p>
 * Typical usage pattern with path-based matching:
 * <pre>
 * // Create a matcher for admin views
 * List&lt;String&gt; adminPaths = Arrays.asList("/admin/", "/management/");
 * ViewMatcher adminMatcher = new ViewMatcherImpl(adminPaths);
 *
 * // Check if current view is an admin view
 * ViewDescriptor currentView = NavigationUtils.getCurrentView(facesContext);
 * boolean isAdminView = adminMatcher.match(currentView);
 * </pre>
 * 
 * <p>
 * For outcome-based matching:
 * <pre>
 * // Create a matcher for a specific outcome
 * ViewMatcher dashboardMatcher = new OutcomeBasedViewMatcher("dashboard");
 *
 * // Check if current view is the dashboard
 * ViewDescriptor currentView = NavigationUtils.getCurrentView(facesContext);
 * boolean isDashboard = dashboardMatcher.match(currentView);
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.application.view.matcher;
