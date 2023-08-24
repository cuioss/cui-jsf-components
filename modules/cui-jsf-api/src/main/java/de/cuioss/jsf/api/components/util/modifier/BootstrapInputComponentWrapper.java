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
package de.cuioss.jsf.api.components.util.modifier;

import de.cuioss.jsf.api.components.base.BaseCuiInputComponent;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Oliver Wolff
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class BootstrapInputComponentWrapper extends ReflectionBasedModifier {

    private final BaseCuiInputComponent component;

    /**
     * @param component
     */
    public BootstrapInputComponentWrapper(final BaseCuiInputComponent component) {
        super(component);
        this.component = component;
    }

    @Override
    public boolean isSupportsStyleClass() {
        return true;
    }

    @Override
    public void setStyleClass(final String styleClass) {
        component.setStyleClass(styleClass);

    }

    @Override
    public String getStyleClass() {
        return component.getStyleClass();
    }

    @Override
    public boolean isSupportsStyle() {
        return true;
    }

    @Override
    public String getStyle() {
        return component.getStyle();
    }

    @Override
    public void setStyle(final String style) {
        component.setStyle(style);
    }

    @Override
    public boolean isEditableValueHolder() {
        return true;
    }

    @Override
    public boolean isValid() {
        return component.isValid();
    }

    @Override
    public boolean isRequired() {
        return component.isRequired();
    }

}
