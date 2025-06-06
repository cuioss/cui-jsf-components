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
package de.cuioss.jsf.api.components.model.datalist.impl.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@RequiredArgsConstructor
public class ExplodingModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final String name;

    /**
     * Throws a {@link RuntimeException}
     */
    @SuppressWarnings("CopyConstructorMissesField")
    public ExplodingModel(final ExplodingModel other) {
        throw new RuntimeException();
    }

    /**
     * Throws a {@link RuntimeException}
     */
    public ExplodingModel() {
        throw new RuntimeException();
    }
}
