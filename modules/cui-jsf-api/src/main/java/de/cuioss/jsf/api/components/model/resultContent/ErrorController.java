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
package de.cuioss.jsf.api.components.model.resultContent;

import javax.faces.application.FacesMessage;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.result.ResultObject;

/**
 * Handle errors and warning of a {@link ResultObject}. Allow displaying of a
 * notification box or a GlobalFacesMessage and set if the content should be
 * rendered depending on the result state and error.
 */
public interface ErrorController {

    /**
     * Set the value and state of a notification box.
     *
     * @param value if not null, a notification box will be rendered and this value
     *              will be displayed. Otherwise no notification box will be
     *              rendered.
     * @param state the state of the notification box.
     */
    void addNotificationBox(IDisplayNameProvider<?> value, ContextState state);

    /**
     * Add a GlobalFacesMessage.
     *
     * @param value    the text of the faces message.
     * @param severity the severity.
     */
    void addGlobalFacesMessage(IDisplayNameProvider<?> value, FacesMessage.Severity severity);

    /**
     * Add a sticky message.
     *
     * @param value the text of the sticky message.
     * @param state the state of the sticky box.
     */
    void addStickyMessage(IDisplayNameProvider<?> value, ContextState state);

    /**
     * Set if the content should be rendered.
     *
     * @param renderContent true if a should be rendered, otherwise false.
     */
    void setRenderContent(boolean renderContent);

}
