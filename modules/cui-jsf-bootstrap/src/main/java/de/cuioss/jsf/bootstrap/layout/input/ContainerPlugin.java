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
package de.cuioss.jsf.bootstrap.layout.input;

import java.util.Optional;

import javax.faces.component.UIComponent;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PreRenderComponentEvent;

/**
 * A container plugin is used for extending the functionality of
 * {@link LabeledContainerComponent}. It defines an Event-based interface. In
 * essence it streamlines the communication of lifecycle-events. The plugins can
 * be placed as direct child an / or facet
 *
 * @author Oliver Wolff
 *
 */
public interface ContainerPlugin {

    /**
     * This method is called when the {@link LabeledContainerComponent} handles its
     * {@link PreRenderComponentEvent} all plugin-implementations must rely on this
     * method of delivery instead of listening to {@link PreRenderComponentEvent}
     * themselves.
     *
     * @param parent the {@link LabeledContainerComponent}
     */
    default void prerender(LabeledContainerComponent parent) {
        // noop by design
    }

    /**
     * This method is called when the {@link LabeledContainerComponent} handles its
     * {@link PostAddToViewEvent} all plugin-implementations must rely on this
     * method of delivery instead of listening to {@link PostAddToViewEvent}
     * themselves.
     *
     * @param parent the {@link LabeledContainerComponent}
     */
    default void postAddToView(LabeledContainerComponent parent) {
        // noop by design
    }

    /**
     * To be called during rendering. Used for components providing content for one
     * of the {@link ContainerFacets} to be rendered. The components will not be
     * added to the view by the {@link LabeledContainerComponent}, this should be
     * done in {@link #postAddToView(LabeledContainerComponent)} by the plugin if
     * needed.
     *
     * @param facet to be rendered in
     * @return an {@link Optional} for {@link UIComponent} to be rendered in context
     *         of a facet.
     */
    default Optional<UIComponent> provideFacetContent(ContainerFacets facet) {
        return Optional.empty();
    }

    /**
     * @return the {@link PluginStateInfo} is used for determining the actual state
     *         to the {@link LabeledContainerComponent}. The default implementation
     *         returns {@link PluginStateInfo#NO_STATE_INFO}. It is called during
     *         processing of {@link PreRenderComponentEvent}
     */
    default PluginStateInfo provideContainerStateInfo() {
        return PluginStateInfo.NO_STATE_INFO;
    }
}
