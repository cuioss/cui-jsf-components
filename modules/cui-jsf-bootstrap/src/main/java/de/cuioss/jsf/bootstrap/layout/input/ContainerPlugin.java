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
