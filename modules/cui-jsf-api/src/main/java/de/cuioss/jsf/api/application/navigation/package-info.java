/**
 * <h2>JSF Navigation API</h2>
 * <p>
 * This package provides utilities and components for handling navigation in JSF applications,
 * with a focus on programmatic navigation, view identification, and redirection.
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.application.navigation.NavigationUtils}: Utility class
 *       with static methods for JSF navigation operations such as view ID lookups,
 *       redirects, and parameter handling.</li>
 *   <li>{@link de.cuioss.jsf.api.application.navigation.ViewIdentifier}: Represents
 *       a view in a JSF application, combining a view ID with URL parameters and
 *       providing navigation capabilities.</li>
 *   <li>{@link de.cuioss.jsf.api.application.navigation.Redirector}: Interface for
 *       objects that can perform redirects, typically implemented by constants
 *       representing navigation targets.</li>
 * </ul>
 * 
 * <h3>Usage Scenarios</h3>
 * <p>
 * This package is particularly useful in the following scenarios:
 * <ul>
 *   <li>Programmatic navigation outside of standard navigation rules</li>
 *   <li>Navigation with complex parameter handling</li>
 *   <li>Redirects to different web application resources</li>
 *   <li>Extracting and storing view information for later navigation</li>
 *   <li>Navigation based on runtime conditions</li>
 * </ul>
 * 
 * <h3>Best Practices</h3>
 * <p>
 * While these utilities provide powerful navigation capabilities, they should be used 
 * judiciously:
 * <ul>
 *   <li>Prefer standard JSF navigation rules for simple navigation within the application</li>
 *   <li>Use {@link de.cuioss.jsf.api.application.navigation.NavigationUtils} primarily for redirects 
 *       to different web application resources</li>
 *   <li>Consider implementing {@link de.cuioss.jsf.api.application.navigation.Redirector} for 
 *       objects that represent navigation targets</li>
 * </ul>
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.application.navigation;
