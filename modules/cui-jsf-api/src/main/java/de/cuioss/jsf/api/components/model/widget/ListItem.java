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

import java.io.Serializable;
import java.time.LocalDate;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;

/**
 * To be displayed inside the {@link ListItemWidgetModel}.
 *
 * @author Matthias Walliczek
 */
public interface ListItem extends Serializable {

    /**
     * @return the title key of the widget.
     */
    IDisplayNameProvider<?> getTitle();

    /**
     * @return the microdata itemtype property for the title element.
     */
    String getTitleType();

    /**
     * @return the text to display between title and timestamp.
     */
    String getText();

    /**
     * @return the microdata itemtype property for the text element.
     */
    String getTextType();

    /**
     * @return cui icon class to be displayed if no preview image is set.
     */
    String getIconClass();

    /**
     * Allow rendering a preview image if set.
     *
     * @return url for the preview image
     */
    String getPreviewImage();

    /**
     * @return resource library to retrieve the image from.
     */
    String getPreviewImageLibrary();

    /**
     * If specified, a link to the specified url will be rendered around the item.
     *
     * @return target link url.
     */
    String getClickLink();

    /**
     * Should the link be opened in a new window?
     *
     * @return target attribute for the "a href" element.
     */
    String getClickLinkTarget();

    /**
     * @return the timestamp for this item to allow sorting.
     */
    LocalDate getTimestamp();

    /**
     * @return the optional string representation of the timestamp.
     */
    String getTimestampValue();
}
