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

import de.cuioss.jsf.api.components.support.LabelResolver;
import jakarta.faces.context.FacesContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>Standard implementation of the NavigationMenuItemSingle interface</h2>
 * <p>
 * This class extends {@link NavigationMenuItemImpl} and implements {@link NavigationMenuItemSingle}
 * to provide a concrete implementation of a single-action menu item. It represents menu items
 * that navigate to a specific JSF outcome when activated.
 * </p>
 * <p>
 * Key features of this implementation:
 * </p>
 * <ul>
 * <li>Supports JSF navigation outcomes with parameters</li>
 * <li>Configurable target frame/window for navigation</li>
 * <li>Optional onClick JavaScript actions</li>
 * <li>Internationalized labels</li>
 * <li>Inherits all properties and behaviors from NavigationMenuItemImpl</li>
 * </ul>
 * <p>
 * This class is typically used for standard menu links that navigate within
 * a JSF application.
 * </p>
 *
 * @author Sven Haag
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class NavigationMenuItemSingleImpl extends NavigationMenuItemImpl implements NavigationMenuItemSingle {

    @Serial
    private static final long serialVersionUID = -4292374413272966456L;

    /**
     * <p>The JSF navigation outcome for this menu item.</p>
     * <p>This defines the target view or page that will be navigated to when
     * this menu item is activated.</p>
     */
    @Getter
    @Setter
    private String outcome;

    /**
     * <p>The target frame or window for navigation.</p>
     * <p>Follows HTML target attribute conventions (_self, _blank, etc.).</p>
     */
    @Getter
    @Setter
    private String target;

    /**
     * <p>JavaScript code to be executed when the menu item is clicked.</p>
     * <p>This can execute client-side logic in addition to or instead of navigation.</p>
     */
    @Getter
    @Setter
    private String onClickAction;

    /**
     * <p>Parameters to be included in the navigation.</p>
     * <p>These are typically appended as query parameters to the URL when
     * navigating to the outcome.</p>
     * <p>Initialized as an empty HashMap to avoid null checks when adding parameters.</p>
     */
    @Getter
    private final Map<String, String> outcomeParameter = new HashMap<>();

    /**
     * <p>The resource bundle key for the menu item's label.</p>
     * <p>This is used to look up internationalized label text in a resource bundle.</p>
     */
    @Getter
    @Setter
    private String labelKey;

    /**
     * <p>The explicit label value for this menu item.</p>
     * <p>This is a direct label value that can be used instead of a resource bundle key.</p>
     */
    @Getter
    @Setter
    private String labelValue;

    /**
     * <p>Creates a new single menu item with the specified order value.</p>
     * <p>The order value determines the position of this menu item relative to
     * other menu items within the same container.</p>
     *
     * @param order The order value for positioning this menu item. Lower values
     *              position the menu item earlier/higher in the menu structure.
     */
    public NavigationMenuItemSingleImpl(Integer order) {
        super(order);
    }

    /**
     * <p>Retrieves the resolved label text for this menu item.</p>
     * <p>This implementation uses the {@link LabelResolver} to resolve the label
     * from either the labelKey (using a resource bundle) or the labelValue.</p>
     * <p>This label is typically displayed as the text of the menu item link or button.</p>
     *
     * @return The resolved label text, or {@code null} if no label is available.
     */
    @Override
    public String getResolvedLabel() {
        return LabelResolver.builder().withLabelKey(labelKey).withLabelValue(labelValue).build()
                .resolve(FacesContext.getCurrentInstance());
    }
}
