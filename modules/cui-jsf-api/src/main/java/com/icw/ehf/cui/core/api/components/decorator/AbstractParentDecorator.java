package com.icw.ehf.cui.core.api.components.decorator;

import static java.util.Objects.requireNonNull;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import com.icw.ehf.cui.core.api.components.partial.ComponentBridge;
import com.icw.ehf.cui.core.api.components.util.ComponentModifier;
import com.icw.ehf.cui.core.api.components.util.modifier.ComponentModifierFactory;

/**
 * Base class for decorating the parent of the given component. This component ensures that
 * {@link #decorate(ComponentModifier)} method is always called before a component is to be
 * rendered, In addition it implements the contract of {@link ComponentBridge}
 *
 * @author Oliver Wolff
 */
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public abstract class AbstractParentDecorator extends UIComponentBase implements ComponentBridge {

    @Override
    public void processEvent(final ComponentSystemEvent event) {
        if (event instanceof PostAddToViewEvent) {
            var parent = requireNonNull(getParent(), "There must be a parent present");
            if (isRendered()) {
                decorate(ComponentModifierFactory.findFittingWrapper(parent));
            }
        }
        super.processEvent(event);
    }

    /**
     * Actual decorates the given parent. The component ensures that this method will be called at
     * {@link PostAddToViewEvent} and only if the the component is set to {@code rendered=true}
     *
     * @param parent to be decorated, wrapped into an corresponding {@link ComponentModifier}, is
     *            never null
     */
    public abstract void decorate(final ComponentModifier parent);

    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
