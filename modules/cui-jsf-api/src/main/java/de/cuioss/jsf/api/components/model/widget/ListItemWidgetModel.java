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
package de.cuioss.jsf.api.components.model.widget;

import de.cuioss.uimodel.nameprovider.LabeledKey;

import java.util.List;

/**
 * Specialized widget model for displaying collections of items in a list format.
 * <p>
 * This interface extends the base {@link WidgetModel} to provide support for
 * list-based widgets like activity feeds, notification lists, news items, or
 * any other collection of structured content that needs to be displayed in a
 * consistent manner.
 * </p>
 * <p>
 * The ListItemWidgetModel manages:
 * </p>
 * <ul>
 *   <li>A collection of items to be displayed in the widget</li>
 *   <li>Empty state messaging when no items are available</li>
 *   <li>Pagination or "show more" functionality for larger collections</li>
 * </ul>
 * <p>
 * List item widgets typically render each item with a consistent structure,
 * including elements like:
 * </p>
 * <ul>
 *   <li>Title and descriptive text</li>
 *   <li>Visual indicator (icon or preview image)</li>
 *   <li>Timestamp or date information</li>
 *   <li>Optional navigation functionality</li>
 * </ul>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Create a model with a collection of news items
 * var newsItems = getLatestNewsItems();
 * ListItemWidgetModel newsWidget = new DefaultListItemWidgetModel(
 *     "Latest News",                   // widget title
 *     newsItems,                       // list of items
 *     LabeledKey.of("No news items"),  // empty message
 *     newsItems.size() > 5             // show more button condition
 * );
 * 
 * // Provide it to a component
 * newsComponent.setModel(newsWidget);
 * </pre>
 * <p>
 * Implementations of this interface should consider thread safety if the model
 * will be accessed concurrently.
 * </p>
 * 
 * @author Matthias Walliczek
 * @since 1.0
 * @see ListItem
 * @see WidgetModel
 */
public interface ListItemWidgetModel extends WidgetModel {

    /**
     * Returns the collection of items to be displayed in the widget.
     * <p>
     * The items are typically displayed in the order provided by the list,
     * though UI components may apply additional sorting or filtering based on
     * user preferences or display constraints.
     * </p>
     * <p>
     * If no items are available, an empty list should be returned rather than null.
     * </p>
     *
     * @return a non-null list of items to display
     */
    List<? extends ListItem> getItems();

    /**
     * Returns the message to display when no items are available.
     * <p>
     * This message is shown in place of the list when {@link #getItems()} returns
     * an empty list, providing feedback to the user about why no content is visible.
     * </p>
     * <p>
     * Using a {@link LabeledKey} allows for localization of the empty state message.
     * </p>
     *
     * @return the message to display in the empty state, or null if no message
     *         should be displayed
     */
    LabeledKey getNoItemsMessage();

    /**
     * Determines whether a "show more" button should be rendered.
     * <p>
     * When true, the widget will include a button or link that allows users to
     * view additional items beyond the initial set displayed in the widget.
     * This is typically used when the list contains more items than can be
     * comfortably displayed in the widget's default state.
     * </p>
     * <p>
     * The implementation of what happens when the button is clicked (e.g., pagination,
     * expanding the widget, navigation to a full list view) is determined by the
     * component using this model.
     * </p>
     *
     * @return true if a "show more" button should be rendered, false otherwise
     */
    boolean isRenderShowMoreButton();
}
