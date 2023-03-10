package de.cuioss.jsf.api.components.model.widget;

import java.util.List;

import de.cuioss.uimodel.nameprovider.LabeledKey;

/**
 * Display a list of entries as except.
 *
 * @author Matthias Walliczek
 */
public interface ListItemWidgetModel extends WidgetModel {

    /**
     * @return the list of items to display.
     */
    List<? extends ListItem> getItems();

    /**
     * @return the message string to display in case item list is empty.
     */
    LabeledKey getNoItemsMessage();

    /**
     * @return true if a show more button should be rendered.
     */
    boolean isRenderShowMoreButton();
}
