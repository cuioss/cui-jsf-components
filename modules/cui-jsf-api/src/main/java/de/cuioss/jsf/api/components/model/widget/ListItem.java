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

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Interface defining the model for individual items within a list-based widget.
 * <p>
 * This interface represents structured content items displayed in components that
 * implement the {@link ListItemWidgetModel}. Each item provides a rich set of
 * display properties including title, text, timestamp, and visual elements like
 * icons or preview images.
 * </p>
 * <p>
 * List items support several key features:
 * </p>
 * <ul>
 *   <li>Semantic HTML through microdata attributes for SEO and accessibility</li>
 *   <li>Visual representation through icons or preview images</li>
 *   <li>Chronological organization through timestamps</li>
 *   <li>Navigation through clickable links</li>
 *   <li>Localized titles through display name providers</li>
 * </ul>
 * <p>
 * The ListItem model is designed for rendering consistent, information-rich list
 * entries in news feeds, activity streams, notification lists, and other list-based
 * UI components.
 * </p>
 * <p>
 * Implementation classes should consider making instances immutable where possible
 * to ensure thread safety.
 * </p>
 * 
 * @author Matthias Walliczek
 * @since 1.0
 * @see ListItemWidgetModel
 */
public interface ListItem extends Serializable {

    /**
     * Returns the display title for this list item.
     * <p>
     * The title is typically displayed as the primary heading for the list item.
     * Using a display name provider allows for localized or dynamically generated titles.
     * </p>
     *
     * @return the display name provider for this item's title (should not be null)
     */
    @SuppressWarnings("java:S1452") // Wildcard return type required by public API
    IDisplayNameProvider<?> getTitle();

    /**
     * Returns the microdata itemtype property for the title element.
     * <p>
     * This value is used to provide semantic meaning to the title in HTML microdata
     * format, which enhances SEO and accessibility. If provided, it will be added as
     * an itemtype attribute to the title element.
     * </p>
     * <p>
     * Common values might include schema.org types like "http://schema.org/name".
     * </p>
     *
     * @return the microdata itemtype for the title, or null if none should be applied
     */
    String getTitleType();

    /**
     * Returns the descriptive text content for this list item.
     * <p>
     * This text is typically displayed between the title and timestamp, providing
     * additional information or context about the item.
     * </p>
     *
     * @return the descriptive text for this item, or null if no text should be displayed
     */
    String getText();

    /**
     * Returns the microdata itemtype property for the descriptive text element.
     * <p>
     * This value is used to provide semantic meaning to the text content in HTML microdata
     * format. If provided, it will be added as an itemtype attribute to the text element.
     * </p>
     * <p>
     * Common values might include schema.org types like "http://schema.org/description".
     * </p>
     *
     * @return the microdata itemtype for the text content, or null if none should be applied
     */
    String getTextType();

    /**
     * Returns the CSS icon class to be displayed if no preview image is set.
     * <p>
     * This icon serves as a visual indicator of the item type or content when no
     * preview image is available. The value should be a CSS class that defines the icon,
     * typically from an icon font like Font Awesome or CUI icons.
     * </p>
     * <p>
     * Example values: "cui-icon-document", "cui-icon-calendar", "fa fa-user"
     * </p>
     *
     * @return the CSS class for the icon, or null if no icon should be displayed
     */
    String getIconClass();

    /**
     * Returns the URL for an optional preview image to be displayed with the item.
     * <p>
     * When provided, this image will be displayed instead of the icon specified by
     * {@link #getIconClass()}. The image typically represents a thumbnail or preview
     * of the content referenced by the list item.
     * </p>
     *
     * @return the URL for the preview image, or null if no image should be displayed
     * @see #getPreviewImageLibrary()
     */
    String getPreviewImage();

    /**
     * Returns the resource library name from which to retrieve the preview image.
     * <p>
     * This method works in conjunction with {@link #getPreviewImage()} to specify
     * where the image resource is located in the JSF resource system.
     * </p>
     *
     * @return the name of the JSF resource library containing the preview image,
     *         or null to use the default library
     */
    String getPreviewImageLibrary();

    /**
     * Returns the URL to which the item should link when clicked.
     * <p>
     * If provided, the entire list item will be wrapped in an anchor tag pointing
     * to this URL, making the item clickable. This supports navigation to detail
     * views or related content.
     * </p>
     *
     * @return the target URL for when the item is clicked, or null if the item
     *         should not be clickable
     * @see #getClickLinkTarget()
     */
    String getClickLink();

    /**
     * Returns the target attribute for the item's click link.
     * <p>
     * This value specifies how the linked resource should be displayed when the
     * item is clicked. Common values include "_blank" (new window/tab), "_self" (same frame),
     * or the name of a specific frame.
     * </p>
     * <p>
     * Only relevant when {@link #getClickLink()} returns a non-null value.
     * </p>
     *
     * @return the target attribute for the anchor tag, or null to use the default target
     */
    String getClickLinkTarget();

    /**
     * Returns the timestamp associated with this list item.
     * <p>
     * The timestamp typically represents when the item was created, modified, or
     * when an event occurred. It is used for sorting items chronologically and
     * for displaying date information.
     * </p>
     *
     * @return the date timestamp for this item, or null if the item has no
     *         associated timestamp
     */
    LocalDate getTimestamp();

    /**
     * Returns an optional string representation of the timestamp.
     * <p>
     * This method allows providing a pre-formatted version of the timestamp, which
     * can be useful for custom date formats or for including additional time information
     * beyond what is available in the {@link #getTimestamp()} date.
     * </p>
     * <p>
     * If this returns null while {@link #getTimestamp()} returns a non-null value,
     * the timestamp will be formatted using default formatting.
     * </p>
     *
     * @return the formatted timestamp string, or null to use default formatting
     */
    String getTimestampValue();
}
