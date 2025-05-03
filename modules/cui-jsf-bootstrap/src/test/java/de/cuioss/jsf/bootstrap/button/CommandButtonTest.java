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
package de.cuioss.jsf.bootstrap.button;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"icon", "size", "state", "titleKey", "titleValue", "iconAlign", "keyBinding"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class CommandButtonTest extends AbstractComponentTest<CommandButton> {

    private static final String KEY_BINDING = AttributeValue.CUI_CLICK_BINDING.getContent();

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.COMMAND_BUTTON_RENDERER, anyComponent().getRendererType());
        assertEquals(BootstrapFamily.COMPONENT_FAMILY, anyComponent().getFamily());
    }

    @Test
    void shouldFailToSetValue() {
        var test = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> test.setValue(""));
    }

    @Test
    void shouldResolveGetValueFromLabel() {
        var component = anyComponent();
        assertNull(component.getValue());
        var label = Generators.letterStrings().next();
        component.setLabelValue(label);
        assertEquals(label, component.getValue());
    }

    @Test
    void shouldResolveGetTitleFromTitleProvider() {
        var component = anyComponent();
        assertNull(component.getTitle());
        var label = Generators.letterStrings().next();
        component.setTitleValue(label);
        assertEquals(label, component.getTitle());
    }

    @Test
    void shouldDetermineRightIcon() {
        var component = anyComponent();
        assertFalse(component.isDisplayIconRight());
        component.setIcon("icon");
        assertFalse(component.isDisplayIconRight());
        component.setIconAlign(AlignHolder.RIGHT.name());
        assertTrue(component.isDisplayIconRight());
    }

    @Test
    void shouldDetermineLeftIcon() {
        var component = anyComponent();
        assertFalse(component.isDisplayIconLeft());
        component.setIcon("icon");
        assertTrue(component.isDisplayIconLeft());
        component.setIconAlign(AlignHolder.RIGHT.name());
        assertFalse(component.isDisplayIconLeft());
    }

    @Test
    void shouldAddKeyBindingOnPreRender() {
        var component = anyComponent();
        assertFalse(component.getPassThroughAttributes().containsKey(KEY_BINDING));
        component.processEvent(new PreRenderComponentEvent(component));
        assertFalse(component.getPassThroughAttributes().containsKey(KEY_BINDING));
        component.setKeyBinding("s");
        component.processEvent(new PreRenderComponentEvent(component));
        assertTrue(component.getPassThroughAttributes().containsKey(KEY_BINDING));
        assertEquals("s", component.getPassThroughAttributes().get(KEY_BINDING));
    }
}
