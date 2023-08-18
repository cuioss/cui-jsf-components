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
package de.cuioss.jsf.components.selection;

import de.cuioss.uimodel.nameprovider.LabelKeyProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Oliver Wolff
 */
@RequiredArgsConstructor
public enum TestEnum implements LabelKeyProvider {

    /** */
    DAY("common.abb.day"),
    /** */
    MONTH("common.abb.month"),
    /** */
    YEAR("common.abb.year");

    @Getter
    private final String labelKey;
}
