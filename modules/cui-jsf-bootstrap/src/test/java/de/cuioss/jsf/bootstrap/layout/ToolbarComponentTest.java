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
package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.event.PreRenderComponentEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "pinBottomOffset", "pinTopOffset", "pinToBottom", "pinToTop" }, defaultValued = {
        "pinBottomOffset", "pinTopOffset" })
class ToolbarComponentTest extends AbstractUiComponentTest<ToolbarComponent> {

    @Test
    void shouldProvideCorrectStyleClass() {
        final var component = anyComponent();
        assertEquals("toolbar toolbar-default", component.resolveStyleClass().getStyleClass());
    }

    @Test
    void shouldProvideComplexStyleClass() {
        final var component = anyComponent();
        component.setPinToTop(true);
        component.setSize("xl");
        var expected = "toolbar pinned toolbar-xl";
        assertEquals(expected, component.resolveStyleClass().getStyleClass());
        component.setPinToBottom(true);
        assertEquals(expected, component.resolveStyleClass().getStyleClass());
        component.setPinToTop(false);
        assertEquals(expected, component.resolveStyleClass().getStyleClass());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.LAYOUT_RENDERER, anyComponent().getRendererType());
    }

    @Test
    void shouldNotSetPTAttributes() {
        var component = anyComponent();
        component.processEvent(new PreRenderComponentEvent(component));
        assertTrue(component.getPassThroughAttributes().isEmpty());
    }

    @Test
    void shouldHandlePTAttributesForTop() {
        var component = anyComponent();
        component.setPinToTop(true);
        component.setPinTopOffset(10);
        component.processEvent(new PreRenderComponentEvent(component));
        var map = component.getPassThroughAttributes();
        assertEquals(2, map.size());
        assertEquals(ToolbarComponent.DATA_SPY_VALUE, map.get(ToolbarComponent.DATA_SPY_ATTRIBUTE));
        assertEquals(10, map.get(ToolbarComponent.DATA_OFFSET_TOP));
    }

    @Test
    void shouldHandlePTAttributesForBottom() {
        var component = anyComponent();
        component.setPinToBottom(true);
        component.setPinBottomOffset(10);
        component.processEvent(new PreRenderComponentEvent(component));
        var map = component.getPassThroughAttributes();
        assertEquals(2, map.size());
        assertEquals(ToolbarComponent.DATA_SPY_VALUE, map.get(ToolbarComponent.DATA_SPY_ATTRIBUTE));
        assertEquals(10, map.get(ToolbarComponent.DATA_OFFSET_BOTTOM));
    }
}
