package de.cuioss.jsf.api.components.model.widget;

import java.util.ArrayList;

import de.cuioss.tools.collect.CollectionLiterals;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.nameprovider.LabeledKey;

public class BaseDeferredLoadingListItemWidgetMock extends BaseDeferredLoadingListItemWidget {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doInit() {
        content = new ArrayList<>(CollectionLiterals.immutableList(new BaseListItem()));
    }

    @Override
    public LabeledKey getNoItemsMessage() {
        return null;
    }

    @Override
    public IDisplayNameProvider<?> getTitle() {
        return null;
    }

    @Override
    public String getId() {
        return "123";
    }

    @Override
    public String getCoreAction() {
        return "action";
    }
}
