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
package de.cuioss.jsf.api.components.model.widget;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.faces.event.ActionEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Synchronized;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract implementation of the {@link WidgetModel} supporting deferred
 * loading of the content and/or error message.
 *
 * @param <T> type of the content.
 * @author Matthias Walliczek
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDeferredLoadingWidget<T extends Serializable> extends BaseWidget
        implements DashboardWidgetModel {

    @Serial
    private static final long serialVersionUID = -8914809364978152106L;

    @Getter
    protected T content;

    @Getter
    private boolean initialized;

    protected IDisplayNameProvider<?> errorString;

    @Override
    public IDisplayNameProvider<?> getNotificationBoxValue() {
        return errorString;
    }

    /**
     * initialize the content or set the errorString.
     */
    protected abstract void doInit();

    @Override
    @Synchronized
    public void processAction(ActionEvent actionEvent) {
        if (!isInitialized()) {
            doInit();
            initialized = true;
        }
    }
}
