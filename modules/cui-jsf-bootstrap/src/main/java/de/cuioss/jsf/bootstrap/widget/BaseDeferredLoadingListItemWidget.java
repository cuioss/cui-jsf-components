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
package de.cuioss.jsf.bootstrap.widget;

import de.cuioss.jsf.api.components.model.widget.BaseDeferredLoadingWidget;
import de.cuioss.jsf.api.components.model.widget.ListItem;
import de.cuioss.jsf.api.components.model.widget.ListItemWidgetModel;
import de.cuioss.tools.string.MoreStrings;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for widgets that display lists of items with deferred loading.
 * Extends {@link BaseDeferredLoadingWidget} to handle collections of {@link ListItem} objects.
 * Works with the "listItemWidget" composite component for standardized rendering.
 *
 * <h2>Example Implementation</h2>
 * <pre>
 * public class UserListWidget extends BaseDeferredLoadingListItemWidget {
 *
 *     private UserService userService;
 *
 *     protected ArrayList&lt;ListItem&gt; loadContent() {
 *         List&lt;User&gt; users = userService.getUsers();
 *         ArrayList&lt;ListItem&gt; items = new ArrayList&lt;&gt;();
 *
 *         for (User user : users) {
 *             items.add(new ListItem(user.getId(), user.getName(), user.getDescription()));
 *         }
 *
 *         return items;
 *     }
 * }
 * </pre>
 *
 * @author Matthias Walliczek
 * @since 1.0
 *
 * @see BaseDeferredLoadingWidget
 * @see ListItemWidgetModel
 * @see ListItem
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BaseDeferredLoadingListItemWidget extends BaseDeferredLoadingWidget<ArrayList<ListItem>>
        implements ListItemWidgetModel {

    @Serial
    private static final long serialVersionUID = 8583462862065649466L;

    /**
     * {@inheritDoc}
     *
     * <p>Provides access to the list items that have been loaded by the deferred loading
     * mechanism. This method is used by the rendering component to display the items.</p>
     *
     * @return the list of {@link ListItem} objects to be displayed, or an empty list if
     *         none are available
     */
    @Override
    public List<ListItem> getItems() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Specifies the default composite component ID to be used for rendering this widget.
     * This method returns the ID for the standard list item widget composite component
     * defined in the CUI JSF Bootstrap module.</p>
     *
     * @return the ID of the composite component to be used for rendering, by default
     *         "cui-composite:listItemWidget"
     */
    @Override
    public String getCompositeComponentId() {
        return "cui-composite:listItemWidget";
    }

    /**
     * {@inheritDoc}
     *
     * <p>Determines whether a "Show More" button should be rendered at the bottom of the list.
     * The button will be displayed only if:</p>
     * <ul>
     *   <li>A core action is defined (not empty)</li>
     *   <li>The list of items is not null</li>
     *   <li>The list of items is not empty</li>
     * </ul>
     *
     * @return {@code true} if the "Show More" button should be displayed, {@code false} otherwise
     */
    @Override
    public boolean isRenderShowMoreButton() {
        return !MoreStrings.isEmpty(getCoreAction()) && null != getItems() && !getItems().isEmpty();
    }
}
