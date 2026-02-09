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
package de.cuioss.jsf.api.components.decorator;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PostValidateEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@EnableJsfEnvironment
class AbstractParentDecoratorTest extends AbstractComponentTest<ParentDecoratorDummy> {

    private ComponentSystemEvent expectedEvent;

    private ComponentSystemEvent unexpectedEvent;

    @BeforeEach
    void init() {
        expectedEvent = new PostAddToViewEvent(new HtmlInputText());
        unexpectedEvent = new PostValidateEvent(new HtmlInputText());
    }

    @Override
    public void configure(final ParentDecoratorDummy toBeConfigured) {
        toBeConfigured.setParent(new HtmlInputText());
    }

    @Test
    void shouldReactOnExpectedEvent() {
        var underTest = anyComponent();
        assertFalse(underTest.isDecorateCalled());
        underTest.processEvent(expectedEvent);
        assertTrue(underTest.isDecorateCalled());
    }

    @Test
    void shouldIgnoreInvalidEvent() {
        var underTest = anyComponent();
        assertFalse(underTest.isDecorateCalled());
        underTest.processEvent(unexpectedEvent);
        assertFalse(underTest.isDecorateCalled());
    }

    @Test
    void shouldFailOnMissingParent() {
        var underTest = anyComponent();
        underTest.setParent(null);
        assertThrows(NullPointerException.class, () -> underTest.processEvent(expectedEvent));
    }

    @Test
    void shouldActAsComponentBridge() {
        assertNotNull(anyComponent().facesContext());
        assertNotNull(anyComponent().stateHelper());
    }
}
