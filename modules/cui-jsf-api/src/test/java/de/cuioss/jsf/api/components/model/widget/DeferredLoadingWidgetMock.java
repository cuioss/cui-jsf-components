package de.cuioss.jsf.api.components.model.widget;

import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;

@SuppressWarnings({ "javadoc", "serial" })
public class DeferredLoadingWidgetMock extends BaseDeferredLoadingWidget<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doInit() {

    }

    @Override
    public String getCompositeComponentId() {
        return "123";
    }

    @Override
    public IDisplayNameProvider<?> getTitle() {
        return new DisplayName("title");
    }

    @Override
    public String getId() {
        return "123";
    }
}
