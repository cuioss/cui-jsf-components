/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.model.datalist.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cuioss.jsf.api.components.model.datalist.impl.support.ExplodingModel;
import de.cuioss.jsf.api.components.model.datalist.impl.support.MissingCopyConstructor;
import de.cuioss.jsf.api.components.model.datalist.impl.support.MissingDefaultConstructor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ReflectionBasedEditableDataListModelTest {

    @Test
    void shouldHandleGoodCase() {
        var model = new ReflectionBasedEditableDataListModel<>(SomeModel.class, null);
        assertNotNull(model.createEmptyItem());
        assertNotNull(model.createCopy(new SomeModel()));
    }

    @Test
    void shouldHandleMissingDefaultConstructor() {
        var model = new ReflectionBasedEditableDataListModel<>(MissingDefaultConstructor.class, null);
        assertThrows(IllegalStateException.class, model::createEmptyItem);
    }

    @Test
    void shouldHandleMissingCopyConstructor() {
        var model = new ReflectionBasedEditableDataListModel<>(MissingCopyConstructor.class, null);
        var item = new MissingCopyConstructor("");
        assertThrows(IllegalStateException.class, () -> model.createCopy(item));
    }

    @Test
    void shouldHandleExplodingCopyConstructor() {
        var model = new ReflectionBasedEditableDataListModel<>(ExplodingModel.class, null);
        var item = new ExplodingModel("");
        assertThrows(IllegalStateException.class, () -> model.createCopy(item));
    }

    @Test
    void shouldHandleExplodingDefaultConstructor() {
        var model = new ReflectionBasedEditableDataListModel<>(ExplodingModel.class, new ArrayList<>());
        assertThrows(IllegalStateException.class, model::createEmptyItem);
    }
}
