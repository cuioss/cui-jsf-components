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
