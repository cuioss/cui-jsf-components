/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.model.menu;

/**
 * <h2>Interface for external URL menu items</h2>
 * <p>
 * This interface extends {@link NavigationMenuItem} to represent menu items that
 * link to external URLs (outside the JSF application). These are typically rendered
 * as standard HTML links that navigate to arbitrary web addresses.
 * </p>
 * <p>
 * Key characteristics of external single menu items:
 * </p>
 * <ul>
 * <li>Direct linking to absolute URLs rather than JSF outcomes</li>
 * <li>Support for targeting specific browser windows or frames</li>
 * <li>Support for JavaScript actions on click</li>
 * <li>Compatible with standard HTTP security features like rel="noopener"</li>
 * </ul>
 * <p>
 * This interface also extends {@link NavigationMenuLabelProvider} to ensure
 * proper labeling support for menu items.
 * </p>
 * <p>
 * Note: For navigation within a JSF application using navigation outcomes,
 * use {@link NavigationMenuItemSingle} instead.
 * </p>
 *
 * @author Matthias Walliczek
 */
public interface NavigationMenuItemExternalSingle extends NavigationMenuItem, NavigationMenuLabelProvider {

    /**
     * <p>Retrieves the external URL for this menu item.</p>
     * <p>This URL specifies the web address that the user should be navigated to
     * when this menu item is activated. It should be a complete URL, typically
     * beginning with "http://" or "https://".</p>
     *
     * @return The external URL as a string. Should not be {@code null} for
     * properly functioning external links.
     */
    String getHRef();

    /**
     * <p>Retrieves the target frame or window for the navigation.</p>
     * <p>This follows the HTML target attribute conventions:</p>
     * <ul>
     * <li>{@code _self}: Load in the same browsing context (default)</li>
     * <li>{@code _blank}: Load in a new browsing context (new window/tab)</li>
     * <li>{@code _parent}: Load in the parent browsing context</li>
     * <li>{@code _top}: Load in the top-level browsing context</li>
     * <li>Or a named frame/iframe</li>
     * </ul>
     * <p>For security reasons, external links are often opened in new tabs/windows
     * using {@code _blank} along with proper security attributes.</p>
     *
     * @return The target specification for the navigation. May be {@code null}
     * for default targeting behavior.
     */
    String getTarget();

    /**
     * <p>Retrieves JavaScript code to be executed when the menu item is clicked.</p>
     * <p>This can be used for client-side actions that need to be performed
     * before, after, or instead of the navigation to the external URL.</p>
     * <p>Security note: Care should be taken with JavaScript that processes external
     * URLs to avoid potential security vulnerabilities.</p>
     *
     * @return JavaScript code as a string. May be {@code null} if no JavaScript
     * action is required.
     */
    String getOnClickAction();
}
