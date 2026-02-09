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
package de.cuioss.jsf.bootstrap.tag;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.events.ModelPayloadEvent;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.button.CloseCommandButton;
import de.cuioss.jsf.bootstrap.layout.input.support.MockUIInput;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.CuiMockMethodExpression;
import jakarta.faces.component.UIInput;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.ValueChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"model", "disposable", "contentKey", "contentValue", "size", "state", "titleKey",
        "titleValue", "contentEscape"})
@DisplayName("Tests for TagComponent")
class TagComponentTest extends AbstractUiComponentTest<TagComponent> {

    private static final String PAYLOAD = "data";

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(CloseCommandButton.class)
                .registerUIComponent(JsfHtmlComponent.HIDDEN.getComponentType(), JsfHtmlComponent.HIDDEN.getComponentClass());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.TAG_COMPONENT_RENDERER, anyComponent().getRendererType());
    }

    @Test
    void shouldProvideCorrectEventInfo() {
        assertEquals("click", anyComponent().getDefaultEventName());
        assertEquals(immutableList("click"), anyComponent().getEventNames());
    }

    @Test
    void shouldConsiderIsDisposedOnRenderedAttribute() {
        final var component = createComponent();
        assertTrue(component.isRendered());
        simulateDisposeItem(component);
        assertFalse(component.isRendered());
        component.setRendered(false);
        assertFalse(component.isRendered());
        component.setDisposable(false);
        assertFalse(component.isRendered());
    }

    private static TagComponent createComponent() {
        final var tagComponent = new TagComponent();
        tagComponent.setDisposable(true);
        tagComponent.processEvent(new PostAddToViewEvent(new MockUIInput()));
        return tagComponent;
    }

    private static void simulateDisposeItem(final TagComponent component) {
        final var hiddenInput = (UIInput) component.getChildren().stream().filter(UIInput.class::isInstance)
                .findFirst().orElseThrow(
                () -> new IllegalStateException("Hidden input is missing but should be available as child"));
        hiddenInput.setValue("true");
    }

    @Test
    void shouldBroadcastDisposeEvent() {
        final var component = new TagComponent();
        final var expression = new CuiMockMethodExpression();
        component.setDisposeListener(expression);
        component.broadcast(new ModelPayloadEvent(component, PAYLOAD));
        assertTrue(expression.isInvoked());
    }

    @Test
    void shouldNotBroadcastOnInvalidEvent() {
        final var component = new TagComponent();
        final var expression = new CuiMockMethodExpression();
        component.setDisposeListener(expression);
        component.broadcast(new ValueChangeEvent(component, PAYLOAD, PAYLOAD));
        assertFalse(expression.isInvoked());
    }

    @Test
    void shouldNotBroadcastIfNoListenerIsSet() {
        final var component = new TagComponent();
        final var expression = new CuiMockMethodExpression();
        component.broadcast(new ModelPayloadEvent(component, PAYLOAD));
        assertFalse(expression.isInvoked());
    }

}
