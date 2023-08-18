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
package de.cuioss.jsf.bootstrap.modal.support;

import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ModalDialogSizeTest {

    @Test
    void shouldMapSizes() {
        assertEquals(ModalDialogSize.DEFAULT, ModalDialogSize.getFromString(""));
        assertEquals(ModalDialogSize.LG, ModalDialogSize.getFromString(ModalDialogSize.LG.name()));
        assertEquals(ModalDialogSize.FLUID, ModalDialogSize.getFromString(ModalDialogSize.FLUID.name()));
        assertEquals(ModalDialogSize.SM, ModalDialogSize.getFromString(ModalDialogSize.SM.name()));
    }

    @Test
    void shouldMapStyleClasses() {
        assertTrue(isEmpty(ModalDialogSize.DEFAULT.getStyleClass()));
        assertEquals(ModalDialogSize.PREFIX + ModalDialogSize.FLUID.name().toLowerCase(),
                ModalDialogSize.FLUID.getStyleClass());
        assertEquals(ModalDialogSize.PREFIX + ModalDialogSize.SM.name().toLowerCase(),
                ModalDialogSize.SM.getStyleClass());
        assertEquals(ModalDialogSize.PREFIX + ModalDialogSize.LG.name().toLowerCase(),
                ModalDialogSize.LG.getStyleClass());
    }

    @Test
    void shouldFailToMapInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> ModalDialogSize.getFromString("not-there"));
    }
}
