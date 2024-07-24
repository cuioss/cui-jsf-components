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
package de.cuioss.jsf.api.ui.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.ComponentSystemEvent;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ToggleSwitch as holder of toggled state
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
public class ToggleSwitch implements Serializable {

    @Serial
    private static final long serialVersionUID = -2220680284073352864L;

    @Setter
    @Getter
    private boolean toggled;

    /**
     * Constructor. Sets {@link #isToggled()} to false.
     */
    public ToggleSwitch() {
        toggled = false;
    }

    /**
     * Constructor.
     *
     * @param toggled
     */
    public ToggleSwitch(final Boolean toggled) {
        this.toggled = Objects.requireNonNullElse(toggled, false);
    }

    /**
     * Toggles, whether the state
     *
     * @param actionEvent ActionEvent
     */
    public void toggle(final ActionEvent actionEvent) {
        toggle();
    }

    /**
     * toggle the state
     */
    public void toggle() {
        toggled = !toggled;
    }

    /**
     * Listener for resetting the toggle
     *
     * @param event
     */
    @SuppressWarnings("squid:S1172") // defined by API (Unused parameter)
    public void resetToggle(final ComponentSystemEvent event) {
        toggled = false;
    }
}
