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
package de.cuioss.jsf.components.inlineconfirm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.jsf.components.CuiFamily;
import de.cuioss.test.jsf.component.AbstractComponentTest;

class InlineConfirmComponentTest extends AbstractComponentTest<InlineConfirmComponent> {

    @Test
    void shouldProvideMetadata() {
        assertEquals(CuiFamily.COMPONENT_FAMILY, anyComponent().getFamily());
        assertEquals(CuiFamily.INLINE_CONFIRM_RENDERER, anyComponent().getRendererType());
    }

    @Test
    void shouldFailWithMissingFacet() {
        var component = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> component.getInitialFacet());
    }

    @Test
    void shouldFailWithMissingChild() {
        var component = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> component.getChildAsModifier());
    }

    @Test
    void shouldHandleChildAndFacet() {
        var underTest = anyComponent();
        underTest.getFacets().put(InlineConfirmComponent.INITIAL_FACET_NAME, new DummyComponent());
        underTest.getChildren().add(new DummyComponent());
        assertNotNull(underTest.getInitialFacet());
        assertNotNull(underTest.getChildAsModifier());
    }
}
