/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
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
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Standard implementation of the NavigationMenuItemContainer interface</h2>
 * <p>
 * This class extends {@link NavigationMenuItemImpl} and implements {@link NavigationMenuItemContainer}
 * to provide a concrete implementation of a container-type menu item. It represents menu items
 * that can contain other menu items, such as dropdown menus or submenus.
 * </p>
 * <p>
 * Key features of this implementation:
 * </p>
 * <ul>
 * <li>Manages a collection of child menu items</li>
 * <li>Supports internationalized container labels</li>
 * <li>Inherits all properties and behaviors from NavigationMenuItemImpl</li>
 * </ul>
 * <p>
 * This class is typically used for dropdown menus, collapsible sections, and other
 * menu constructs that group related navigation options.
 * </p>
 *
 * @author Sven Haag
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class NavigationMenuItemContainerImpl extends NavigationMenuItemImpl implements NavigationMenuItemContainer {

    @Serial
    private static final long serialVersionUID = 5453628443565248832L;

    /**
     * <p>The list of child menu items contained within this container.</p>
     * <p>Initialized as an empty ArrayList to avoid null checks when adding children.</p>
     */
    @Getter
    @Setter
    private List<NavigationMenuItem> children = new ArrayList<>();

    /**
     * <p>The resource bundle key for the container's label.</p>
     * <p>This is used to look up internationalized label text in a resource bundle.</p>
     */
    @Getter
    @Setter
    private String labelKey;

    /**
     * <p>The explicit label value for this container.</p>
     * <p>This is a direct label value that can be used instead of a resource bundle key.</p>
     */
    @Getter
    @Setter
    private String labelValue;

    /**
     * <p>Creates a new container menu item with the specified order value.</p>
     * <p>The order value determines the position of this container relative to
     * other menu items at the same level in the menu hierarchy.</p>
     *
     * @param order The order value for positioning this container. Lower values
     *              position the container earlier/higher in the menu structure.
     */
    public NavigationMenuItemContainerImpl(Integer order) {
        super(order);
    }

    /**
     * <p>Retrieves the resolved label text for this container.</p>
     * <p>This implementation uses the {@link LabelResolver} to resolve the label
     * from either the labelKey (using a resource bundle) or the labelValue.</p>
     * <p>This label is typically used as the heading or title for the container,
     * such as the text on a dropdown toggle button.</p>
     *
     * @return The resolved label text, or {@code null} if no label is available.
     */
    @Override
    public String getResolvedLabel() {
        return LabelResolver.builder().withLabelKey(labelKey).withLabelValue(labelValue).build()
                .resolve(FacesContext.getCurrentInstance());
    }
}
