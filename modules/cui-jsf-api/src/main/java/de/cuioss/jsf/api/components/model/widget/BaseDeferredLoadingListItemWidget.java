package de.cuioss.jsf.api.components.model.widget;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.tools.string.MoreStrings;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Abstract extension of the {@link BaseDeferredLoadingWidget} for list items, presetting the
 * listItemWidget composite
 * component to be rendered.
 *
 * @author Matthias Walliczek
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BaseDeferredLoadingListItemWidget
        extends BaseDeferredLoadingWidget<ArrayList<ListItem>> implements ListItemWidgetModel {

    private static final long serialVersionUID = 8583462862065649466L;

    @Override
    public List<ListItem> getItems() {
        return getContent();
    }

    /**
     * @return the id of the in this module defined composite component that should be used as
     *         default for this
     *         implementations of this abstract widget class. May be overridden by a different id of
     *         a more specific composite
     *         component.
     */
    @Override
    public String getCompositeComponentId() {
        return "cui-composite:listItemWidget";
    }

    @Override
    public boolean isRenderShowMoreButton() {
        return !MoreStrings.isEmpty(getCoreAction()) && null != getItems() && !getItems().isEmpty();
    }
}
