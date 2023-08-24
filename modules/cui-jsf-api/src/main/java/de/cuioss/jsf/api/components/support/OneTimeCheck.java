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
package de.cuioss.jsf.api.components.support;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.partial.ComponentBridge;
import de.cuioss.jsf.api.components.util.KeyMappingUtility;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Some attribute like disabled and the styleclass needs to be changed once.
 * PostAddToView does not fit, its a called on ajax requests also. This class
 * helps keeping track of said changes.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString(exclude = "componentBridge")
public class OneTimeCheck {

    private final ComponentBridge componentBridge;

    private static final String KEY = "oneTimeChange";

    private final String compoundKey;

    /**
     * @param componentBridge must not be null
     */
    public OneTimeCheck(final ComponentBridge componentBridge) {
        this.componentBridge = requireNonNull(componentBridge);
        compoundKey = KEY;
    }

    /**
     * Constructor. Needed only for cases, if a component uses more than one
     * {@link OneTimeCheck}
     *
     * @param componentBridge must not be null
     * @param extension       to be appended to the key.
     */
    public OneTimeCheck(final ComponentBridge componentBridge, final String extension) {
        this.componentBridge = requireNonNull(componentBridge);
        compoundKey = KeyMappingUtility.mapKeyWithExtension(KEY, extension);
    }

    /**
     * @return the actual flag whether the check was done. Defaults to
     *         <code>false</code>
     */
    public boolean isChecked() {
        return (Boolean) componentBridge.stateHelper().eval(compoundKey, Boolean.FALSE);
    }

    /**
     * Sets the checked value
     *
     * @param checked to be set
     */
    public void setChecked(final boolean checked) {
        componentBridge.stateHelper().put(compoundKey, checked);
    }

    /**
     * Convenient method that reads the actual state and returns it. If it is false
     * it additionally sets the value to true
     *
     * @return the initial set value
     */
    public boolean readAndSetChecked() {
        final var current = isChecked();
        if (!current) {
            setChecked(true);
        }
        return current;
    }
}
