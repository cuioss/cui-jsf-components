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

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.faces.event.ActionEvent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * Abstract base implementation of {@link WidgetModel}.
 */
@ToString
@EqualsAndHashCode
public abstract class BaseWidget implements WidgetModel {

    @Serial
    private static final long serialVersionUID = -3597359970570562302L;

    @Override
    public boolean isDisableCoreAction() {
        return MoreStrings.isEmpty(getCoreAction()) || null != getNotificationBoxValue();
    }

    @Override
    public boolean isRenderPrimaryAction() {
        return !MoreStrings.isEmpty(getPrimaryAction());
    }

    @Override
    public String getTitleIcon() {
        return null;
    }

    @Override
    public boolean isDisablePrimaryAction() {
        return null != getNotificationBoxValue();
    }

    @Override
    public String getCoreAction() {
        return null;
    }

    @Override
    public String getPrimaryAction() {
        return null;
    }

    @Override
    public IDisplayNameProvider<?> getPrimaryActionTitle() {
        return null;
    }

    @Override
    public String getTitleValue() {
        return null;
    }

    @Override
    public boolean isRenderContent() {
        return null == getNotificationBoxValue();
    }

    @Override
    public ContextState getNotificationBoxState() {
        return ContextState.WARNING;
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    @Override
    public boolean isRendered() {
        return true;
    }

    @Override
    public void processAction(ActionEvent actionEvent) {
    }

    @Override
    public void startInitialize() {
    }
}
