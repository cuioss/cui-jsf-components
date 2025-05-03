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
package de.cuioss.jsf.api.components.model.lazyloading;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ActionListener;

import java.io.Serializable;

/**
 * A model for the LazyLoadingComponent
 */
public interface LazyLoadingModel extends ActionListener, Serializable {

    /**
     * @return a {@link IDisplayNameProvider} if a notification box should be
     *         rendered, otherwise null.
     */
    IDisplayNameProvider<?> getNotificationBoxValue();

    /**
     * @return the state of the notification box, see also
     *         {@link #getNotificationBoxValue()}
     */
    ContextState getNotificationBoxState();

    /**
     * @return true, if the content (the children) should be rendered, otherwise
     *         false.
     */
    boolean isRenderContent();

    /**
     * @return true, if the content to be rendered is already retrieved, otherwise
     *         false. False will trigger a lazy loading round trip which will call
     *         {@link #processAction(ActionEvent)} when finished.
     */
    boolean isInitialized();
}
