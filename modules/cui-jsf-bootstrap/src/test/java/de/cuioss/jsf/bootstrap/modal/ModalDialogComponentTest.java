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
package de.cuioss.jsf.bootstrap.modal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "closable", "footerKey", "footerValue", "headerKey", "headerValue", "size", "state",
        "style", "animation" })
class ModalDialogComponentTest extends AbstractComponentTest<ModalDialogComponent> {

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.MODAL_DIALOG_COMPONENT_RENDERER, anyComponent().getRendererType());
        assertEquals(BootstrapFamily.COMPONENT_FAMILY, anyComponent().getFamily());
    }

    @Test
    void shouldHandleStyleClass() {
        var component = anyComponent();
        assertEquals("modal modal-default", component.getStyleClass());
        component.setSize("fluid");
        assertEquals("modal modal-default", component.getStyleClass());
        component.setStyleClass("someStyleClass");
        assertEquals("modal modal-default someStyleClass", component.getStyleClass());
        component.setAnimation("fade");
        assertEquals("modal modal-default fade someStyleClass", component.getStyleClass());
    }

    @Test
    void shouldFailOnUnsetId() {
        var component = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> component.resolveDialogId());
    }

    @Test
    void shouldResolveToId() {
        var component = anyComponent();
        component.setId("dialogid");
        assertEquals("dialogid", component.resolveDialogId());
    }
}
