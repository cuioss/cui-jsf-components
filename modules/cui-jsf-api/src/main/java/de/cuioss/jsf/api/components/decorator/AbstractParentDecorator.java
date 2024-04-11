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
package de.cuioss.jsf.api.components.decorator;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory;

import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import static java.util.Objects.requireNonNull;

/**
 * Base class for decorating the parent of the given component. This component
 * ensures that {@link #decorate(ComponentModifier)} method is always called
 * before a component is to be rendered, In addition, it implements the contract
 * of {@link ComponentBridge}
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
     * Actual decorates the given parent. The component ensures that this method
     * will be called at {@link PostAddToViewEvent} and only if the component is
     * set to {@code rendered=true}
     *
     * @param parent to be decorated, wrapped into an corresponding
     *               {@link ComponentModifier}, is never null
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
