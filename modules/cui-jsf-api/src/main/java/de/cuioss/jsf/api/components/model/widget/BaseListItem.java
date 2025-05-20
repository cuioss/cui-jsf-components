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
package de.cuioss.jsf.api.components.model.widget;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDate;

/**
 * <h2>Standard implementation of the ListItem interface</h2>
 * <p>
 * This class provides a concrete implementation of {@link ListItem} for use in list widgets
 * and similar UI components. It represents a single item within a list with rich display
 * options including titles, descriptive text, icons, images, and timestamp information.
 * </p>
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Supports internationalized titles through IDisplayNameProvider</li>
 * <li>Configurable icon and image support</li>
 * <li>Click handling through links with optional target specification</li>
 * <li>Timestamp support with both date object and string representation</li>
 * <li>Content type specifications for proper rendering</li>
 * </ul>
 * <p>
 * This implementation uses Lombok's {@code @Data} annotation to automatically generate
 * getters, setters, equals, hashCode, and toString methods.
 * </p>
 * <p>
 * Typical usage:
 * </p>
 * <pre>
 * BaseListItem item = new BaseListItem();
 * item.setTitle(new DisplayNameProvider("Item Title"));
 * item.setText("Item description text");
 * item.setIconClass("cui-icon-info");
 * // Additional configuration...
 * listItemWidgetModel.addItem(item);
 * </pre>
 * 
 * @author Oliver Wolff
 */
@Data
public class BaseListItem implements ListItem {

    @Serial
    private static final long serialVersionUID = -3847701857160268822L;

    /**
     * The title of the list item, using a display name provider for internationalization support.
     */
    private IDisplayNameProvider<?> title;

    /**
     * The content type of the title, which can be used to determine how the title should be rendered.
     * For example, "plain", "html", etc.
     */
    private String titleType;

    /**
     * The descriptive text content for this list item.
     */
    private String text;

    /**
     * The content type of the text, which can be used to determine how the text should be rendered.
     * For example, "plain", "html", "markdown", etc.
     */
    private String textType;

    /**
     * The CSS class name for an icon to be displayed with this list item.
     * This typically references an icon from an icon font library like Font Awesome.
     */
    private String iconClass;

    /**
     * The path or URL to an image to be displayed as a preview for this item.
     */
    private String previewImage;

    /**
     * The name of the image library where the preview image is located.
     * This is used in conjunction with previewImage when resolving resource paths.
     */
    private String previewImageLibrary;

    /**
     * The URL or navigation outcome that should be triggered when the list item is clicked.
     */
    private String clickLink;

    /**
     * The target frame or window where the clickLink should open.
     * Follows HTML target attribute conventions (_self, _blank, etc.).
     */
    private String clickLinkTarget;

    /**
     * A timestamp associated with this list item, represented as a LocalDate.
     * This can be used for sorting, filtering, or displaying creation/modification dates.
     */
    private LocalDate timestamp;

    /**
     * A string representation of the timestamp, which can be used when a custom
     * formatted timestamp display is needed.
     */
    private String timestampValue;
}
