package de.cuioss.jsf.api.components.base;

import javax.faces.event.PreRenderComponentEvent;

/**
 * Streamlines the usage of {@link PreRenderComponentEvent}s within cui-local types.
 * */
public interface PreRenderComponentListener {

    /**
     * Will be called by the parent class
     * @param event to be sent
     */
    void preRenderComponentEvent(PreRenderComponentEvent event);
}
