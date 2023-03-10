package de.cuioss.jsf.bootstrap.layout.input.support;

import de.cuioss.jsf.api.components.base.CuiComponentBase;
import de.cuioss.jsf.bootstrap.layout.input.ContainerPlugin;
import de.cuioss.jsf.bootstrap.layout.input.LabeledContainerComponent;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class MockComponentPlugin extends CuiComponentBase implements ContainerPlugin {

    @Getter
    @Setter
    private boolean prerenderCalled = false;

    @Getter
    @Setter
    private boolean postAddToViewCalled = false;

    @Override
    public void prerender(LabeledContainerComponent parent) {
        prerenderCalled = true;

    }

    @Override
    public void postAddToView(LabeledContainerComponent parent) {
        postAddToViewCalled = true;
    }
}
