package com.icw.ehf.cui.core.api.components.events;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import lombok.Getter;

/**
 * Simple payload wrapper for events like the disposeEvents on
 * com.icw.ehf.cui.components.bootstrap.tag.TagComponent
 *
 * @author Oliver Wolff
 *
 */
public class ModelPayloadEvent extends FacesEvent {

    private static final long serialVersionUID = 7452809204723547999L;

    @Getter
    private final Serializable model;

    /**
     * @param component
     * @param payload
     *            defining the payload
     */
    public ModelPayloadEvent(UIComponent component, Serializable payload) {
        super(component);
        this.model = payload;
    }

    @Override
    public boolean isAppropriateListener(FacesListener listener) {
        return false;
    }

    @Override
    public void processListener(FacesListener listener) {
        throw new UnsupportedOperationException();
    }

}
