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
package de.cuioss.jsf.api.components.events;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

import lombok.Getter;

/**
 * Simple payload wrapper for events like the disposeEvents on
 * de.cuioss.jsf.bootstrap.tag.TagComponent
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
     * @param payload   defining the payload
     */
    public ModelPayloadEvent(UIComponent component, Serializable payload) {
        super(component);
        model = payload;
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
