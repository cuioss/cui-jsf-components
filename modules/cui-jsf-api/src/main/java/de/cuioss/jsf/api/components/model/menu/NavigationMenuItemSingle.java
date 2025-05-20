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

import java.util.Map;

/**
 * <h2>Interface for single action menu items</h2>
 * <p>
 * This interface extends {@link NavigationMenuItem} to represent menu items that
 * perform a single navigation action when activated. These are typically rendered
 * as links or buttons that navigate to another page or perform a specific function.
 * </p>
 * <p>
 * Key characteristics of single menu items:
 * </p>
 * <ul>
 * <li>Navigate to a specific outcome (JSF navigation target)</li>
 * <li>Can include parameters for the navigation</li>
 * <li>Support browser window/tab targeting</li>
 * <li>Support JavaScript actions on click</li>
 * </ul>
 * <p>
 * This interface also extends {@link NavigationMenuLabelProvider} to ensure
 * proper labeling support for menu items.
 * </p>
 * <p>
 * Note: For navigation to external URLs (outside the JSF application),
 * use {@link NavigationMenuItemExternalSingle} instead.
 * </p>
 *
 * @author Sven Haag
 */
public interface NavigationMenuItemSingle extends NavigationMenuItem, NavigationMenuLabelProvider {

    /**
     * <p>Retrieves the JSF navigation outcome for this menu item.</p>
     * <p>This outcome identifies the target view to which the user should be
     * navigated when this menu item is activated. It can be a direct view ID
     * or a logical outcome name defined in faces-config navigation rules.</p>
     *
     * @return The navigation outcome string. May be {@code null} if no navigation
     * is intended (e.g., for items that only execute JavaScript).
     */
    String getOutcome();

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
     *
     * @return The target specification for the navigation. May be {@code null}
     * for default targeting behavior.
     */
    String getTarget();

    /**
     * <p>Retrieves parameters to be included in the navigation.</p>
     * <p>These parameters are typically appended to the URL as query parameters
     * when navigating to the outcome specified by {@link #getOutcome()}.</p>
     *
     * @return A map of parameter names to values. May be empty but should not
     * be {@code null}.
     */
    Map<String, String> getOutcomeParameter();

    /**
     * <p>Retrieves JavaScript code to be executed when the menu item is clicked.</p>
     * <p>This can be used for client-side actions that need to be performed
     * before, after, or instead of the navigation to the outcome.</p>
     *
     * @return JavaScript code as a string. May be {@code null} if no JavaScript
     * action is required.
     */
    String getOnClickAction();
}
