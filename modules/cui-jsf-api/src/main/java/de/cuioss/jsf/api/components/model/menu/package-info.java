/**
 * <h2>Menu Model API for JSF Navigation Components</h2>
 * <p>
 * This package provides a comprehensive set of interfaces and implementations for creating
 * and managing navigation menus programmatically in JSF applications. The API enables
 * developers to:
 * </p>
 * <ul>
 * <li>Define hierarchical menu structures with parent-child relationships</li>
 * <li>Create various types of menu items (containers, separators, links)</li>
 * <li>Support both internal JSF navigation and external URL navigation</li>
 * <li>Control menu item rendering, ordering, and active state</li>
 * <li>Provide internationalized labels for menu items</li>
 * </ul>
 * <p>
 * The core interface is {@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItem} which
 * defines the common contract for all menu items. Specialized sub-interfaces include:
 * </p>
 * <ul>
 * <li>{@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItemContainer} - For dropdown/submenu containers</li>
 * <li>{@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingle} - For simple menu items that link to internal pages</li>
 * <li>{@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItemExternalSingle} - For menu items that link to external URLs</li>
 * <li>{@link de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSeparator} - For menu separators/dividers</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.components.model.menu;
