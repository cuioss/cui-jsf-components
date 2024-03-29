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

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.util.CuiState;
import lombok.NonNull;

import javax.faces.component.StateHelper;

/**
 * Default implementation for {@link ComponentStyleClassProvider}
 * <p>
 *
 * @author Oliver Wolff
 */
public class ComponentStyleClassProviderImpl implements ComponentStyleClassProvider {

    /**
     * The key for the {@link StateHelper}
     */
    public static final String KEY = "styleClass";

    /**
     * The key for the {@link StateHelper}
     */
    public static final String LOCAL_STYLE_CLASS_KEY = "localStyleClass";

    private final CuiState state;

    /**
     * @param bridge to be used
     */
    public ComponentStyleClassProviderImpl(@NonNull ComponentBridge bridge) {
        state = new CuiState(bridge.stateHelper());
    }

    @Override
    public void setStyleClass(String styleClass) {
        state.put(KEY, styleClass);
        state.put(LOCAL_STYLE_CLASS_KEY, styleClass);
    }

    @Override
    public void computeAndStoreFinalStyleClass(StyleClassBuilder componentSpecificStyleClass) {
        state.put(KEY, componentSpecificStyleClass.append(getLocalStyleClassBuilder()).getStyleClass());
    }

    @Override
    public String getStyleClass() {
        return state.get(KEY);
    }

    private StyleClassBuilder getLocalStyleClassBuilder() {
        return new StyleClassBuilderImpl(state.get(LOCAL_STYLE_CLASS_KEY)) ;
    }

}
