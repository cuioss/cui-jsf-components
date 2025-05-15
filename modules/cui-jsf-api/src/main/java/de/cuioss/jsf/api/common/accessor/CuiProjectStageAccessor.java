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
package de.cuioss.jsf.api.common.accessor;

import de.cuioss.uimodel.application.CuiProjectStage;
import lombok.Getter;

import java.io.Serial;

/**
 * Accessor for resolving {@link CuiProjectStage} in a serialization-safe manner.
 * <p>
 * This accessor simplifies access to the project stage and enables serialization-safe
 * handling in JSF views. It retrieves the {@link CuiProjectStage} from
 * the {@code CuiProjectStageProducer}.
 * </p>
 * 
 * Usage example:
 * <pre>
 * private final CuiProjectStageAccessor projectStageAccessor = new CuiProjectStageAccessor();
 * 
 * public boolean isRenderDebugComponents() {
 *     return projectStageAccessor.getValue().isWithinDevelopment();
 * }
 * </pre>
 *
 * @author Oliver Wolff
 */
public class CuiProjectStageAccessor implements ManagedAccessor<CuiProjectStage> {

    @Serial
    private static final long serialVersionUID = 706263142443297439L;

    /**
     * The lazily initialized CuiProjectStage instance.
     * This field is initialized by the {@link #initProjectStage()} method
     * on the first call to {@link #getValue()}.
     */
    @Getter(lazy = true)
    private final CuiProjectStage value = initProjectStage();

    /**
     * Initializes the CuiProjectStage instance.
     * <p>
     * This method creates and initializes a new {@link CuiProjectStageImpl}
     * which determines the current project stage based on the application configuration.
     * </p>
     *
     * @return An initialized CuiProjectStage instance
     */
    private CuiProjectStage initProjectStage() {
        var impl = new CuiProjectStageImpl();
        impl.initBean();
        return impl;
    }
}
