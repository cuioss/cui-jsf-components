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

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.util.ComponentModifier;
import de.cuioss.jsf.api.components.util.modifier.ComponentModifierFactory;
import jakarta.faces.component.StateHelper;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PostAddToViewEvent;

/**
 * Base class for components that need to decorate their parent component.
 * <p>
 * This abstract class provides the foundation for creating JSF components that modify
 * or enhance their parent component in the component tree. It ensures that the
 * {@link #decorate(ComponentModifier)} method is called at the appropriate time
 * in the JSF lifecycle (specifically after the component is added to the view).
 * </p>
 * <p>
 * The decorator pattern is implemented through the following workflow:
 * </p>
 * <ol>
 *   <li>Component is added to the view and receives a {@link PostAddToViewEvent}</li>
 *   <li>If the component is rendered (rendered="true"), the parent component is decorated</li>
 *   <li>The actual decoration logic is implemented by subclasses in the {@link #decorate(ComponentModifier)} method</li>
 * </ol>
 * <p>
 * Usage example for creating a component subclass:
 * </p>
 * <pre>
 * public class StyleClassDecorator extends AbstractParentDecorator {
 *     
 *     private String styleClass;
 *     
 *     &#64;Override
 *     public String getFamily() {
 *         return "decorator.family";
 *     }
 *     
 *     &#64;Override
 *     public void decorate(ComponentModifier parent) {
 *         parent.addStyleClass(styleClass);
 *     }
 *     
 *     // Getter and setter for styleClass attribute
 *     public void setStyleClass(String styleClass) {
 *         this.styleClass = styleClass;
 *     }
 *     
 *     public String getStyleClass() {
 *         return styleClass;
 *     }
 * }
 * </pre>
 * <p>
 * This class implements {@link ComponentBridge} to provide simplified access to component
 * state and context.
 * </p>
 * <p>
 * Like other JSF components, this class is not thread-safe and instances
 * should not be shared between requests.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ComponentBridge
 * @see ComponentModifier
 * @see PostAddToViewEvent
 */
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public abstract class AbstractParentDecorator extends UIComponentBase implements ComponentBridge {

    /**
     * {@inheritDoc}
     * <p>
     * Processes component system events, specifically handling the {@link PostAddToViewEvent}
     * to trigger parent component decoration if this component is rendered.
     * </p>
     * 
     * @param event the component system event to be processed
     */
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
     * Abstract method that must be implemented by subclasses to provide the actual
     * decoration logic for the parent component.
     * <p>
     * This method is called automatically when the component is added to the view,
     * but only if the component is rendered (rendered="true"). The parent component
     * is wrapped in a {@link ComponentModifier} to provide a consistent API for
     * modifying different types of components.
     * </p>
     * <p>
     * Implementations should use the provided modifier to apply decorations such as
     * adding style classes, attributes, or other modifications to the parent component.
     * </p>
     *
     * @param parent the parent component to be decorated, wrapped in a {@link ComponentModifier},
     *               never null
     */
    public abstract void decorate(final ComponentModifier parent);

    /**
     * {@inheritDoc}
     * <p>
     * Provides access to the component's StateHelper for storing component state.
     * </p>
     * 
     * @return the StateHelper for this component
     */
    @Override
    public StateHelper stateHelper() {
        return getStateHelper();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Provides access to the current FacesContext.
     * </p>
     * 
     * @return the current FacesContext
     */
    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Provides access to the named facet of this component.
     * </p>
     * 
     * @param facetName the name of the facet to retrieve
     * @return the UIComponent that corresponds to the requested facet, or null if no such facet exists
     */
    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
