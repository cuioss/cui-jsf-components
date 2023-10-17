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

/**
 * Accesses instances of {@link CuiProjectStage}
 *
 * @author Oliver Wolff
 */
public class CuiProjectStageAccessor implements ManagedAccessor<CuiProjectStage> {

    private static final long serialVersionUID = 706263142443297439L;

    @Getter(lazy = true)
    private final CuiProjectStage value = initProjectStage();

    private CuiProjectStage initProjectStage() {
        var impl = new CuiProjectStageImpl();
        impl.initBean();
        return impl;
    }
}