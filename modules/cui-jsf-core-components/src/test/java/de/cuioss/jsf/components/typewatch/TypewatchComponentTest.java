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
package de.cuioss.jsf.components.typewatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostAddToViewEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;

@VerifyComponentProperties(of = { "process", "update", "allowSubmit", "wait", "highlight", "captureLength" })
class TypewatchComponentTest extends AbstractComponentTest<TypewatchComponent> implements ComponentConfigurator {

    private HtmlInputText parent;

    private ComponentSystemEvent expectedEvent;

    @Test
    void shouldDecorateMinimal() {
        var underTest = anyComponent();
        assertEquals(1, parent.getParent().getChildren().size());
        assertEquals(0, parent.getParent().getChildren().get(0).getPassThroughAttributes().size());
        underTest.processEvent(expectedEvent);
        assertEquals(1, parent.getParent().getChildren().size());
        final var pt = parent.getParent().getChildren().get(0).getPassThroughAttributes();
        assertEquals("data-typewatch", pt.get("data-typewatch"));
        assertEquals(false, pt.get("data-typewatch-allowsubmit"));
        assertEquals(false, pt.get("data-typewatch-highlight"));
    }

    @Test
    void shouldDecorateMaximal() {
        var underTest = anyComponent();
        underTest.setAllowSubmit(true);
        underTest.setWait(666);
        underTest.setHighlight(true);
        underTest.setCaptureLength(42);
        underTest.processEvent(expectedEvent);
        final var pt = parent.getParent().getChildren().get(0).getPassThroughAttributes();
        assertEquals("data-typewatch", pt.get("data-typewatch"));
        assertEquals(true, pt.get("data-typewatch-allowsubmit"));
        assertEquals(666, pt.get("data-typewatch-wait"));
        assertEquals(true, pt.get("data-typewatch-highlight"));
        assertEquals(42, pt.get("data-typewatch-capturelength"));
    }

    @Override
    public void configure(final TypewatchComponent toBeConfigured) {
        super.configure(toBeConfigured);
        var panel = new HtmlPanelGroup();
        parent = new HtmlInputText();
        panel.getChildren().add(parent);
        toBeConfigured.setParent(parent);
        expectedEvent = new PostAddToViewEvent(toBeConfigured);
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlInputText();
    }
}
