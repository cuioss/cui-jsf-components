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
package de.cuioss.jsf.api.components.partial;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the
 * iconAlign-attribute. The implementation relies on the correct use of
 * attribute names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>iconAlign</h2>
 * <p>
 * Defines The alignment of the icon within a given component. In case it is not
 * set or does not match one of "left" or "right" the
 * {@link #resolveIconAlign()} will return {@link AlignHolder#DEFAULT}, other
 * wise it will return the according value. The default icon alignment is
 * usually 'left'
 * </p>
 *
 * @author Oliver Wolff
 */
public class IconAlignProvider {

    /** The key for the {@link StateHelper} */
    private static final String KEY = "iconAlign";

    private final CuiState state;

    /**
     * @param bridge
     */
    public IconAlignProvider(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    /**
     * @return the align
     */
    public String getIconAlign() {
        return state.get(KEY);
    }

    /**
     * @param iconAlign the size to set
     */
    public void setIconAlign(String iconAlign) {
        state.put(KEY, iconAlign);
    }

    /**
     * @return the resolved {@link AlignHolder} if available, otherwise it will
     *         return {@link AlignHolder#DEFAULT}.
     */
    public AlignHolder resolveIconAlign() {
        return AlignHolder.getFromString(getIconAlign());
    }

}
