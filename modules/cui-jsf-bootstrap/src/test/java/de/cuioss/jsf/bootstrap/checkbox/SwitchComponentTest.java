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
package de.cuioss.jsf.bootstrap.checkbox;

import static de.cuioss.tools.collect.CollectionLiterals.immutableMap;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "offTextValue", "offTextKey", "onTextValue", "onTextKey", "titleValue", "titleKey",
        "rendered", "disabled" })
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class SwitchComponentTest extends AbstractComponentTest<SwitchComponent> {

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.SWITCH_RENDERER, anyComponent().getRendererType());
        assertEquals(BootstrapFamily.COMPONENT_FAMILY, anyComponent().getFamily());
    }

    @Test
    void shouldResolveText() {
        var onText = Generators.nonEmptyStrings().next();
        var offText = Generators.nonEmptyStrings().next();
        var underTest = anyComponent();
        underTest.setOnTextValue(onText);
        underTest.setOffTextValue(offText);
        assertEquals(offText, underTest.resolveOffText());
        assertEquals(onText, underTest.resolveOnText());
    }

    @Test
    void shouldResolveTitleText() {
        var sample = Generators.nonEmptyStrings().next();
        var underTest = anyComponent();
        underTest.setTitleValue(sample);
        assertEquals(sample, underTest.resolveTitle());
    }

    @Test
    void shouldResolveStyle() {
        var sample = Generators.nonEmptyStrings().next();
        var underTest = anyComponent();
        underTest.setStyle(sample);
        assertNull(underTest.getStyle());
        assertEquals(sample, underTest.resolveStyle());
    }

    @Test
    void shouldResolveStyleClass() {
        var sample = Generators.nonEmptyStrings().next().trim();
        var underTest = anyComponent();
        underTest.setStyleClass(sample);
        assertNull(underTest.getStyleClass());
        assertEquals(sample, underTest.getStyleClassBuilder().getStyleClass());
    }

    @Test
    void shouldResolvePassThroughAttributes() {
        var underTest = anyComponent();
        underTest.setDisabled(false);
        assertEquals(immutableMap("data-switch-disabled", "false"), underTest.resolvePassThroughAttributes());
        underTest.setDisabled(true);
        assertEquals(immutableMap("data-switch-disabled", "true"), underTest.resolvePassThroughAttributes());
    }

    @Test
    void shouldResolveSelectedWithEmpty() {
        var component = anyComponent();
        component.setSubmittedValue(null);
        assertFalse(component.isSelected());
    }

    @Test
    void shouldResolveSelectedWithBoolean() {
        var component = anyComponent();
        component.setSubmittedValue(Boolean.TRUE);
        assertTrue(component.isSelected());
        component.setSubmittedValue(Boolean.FALSE);
        assertFalse(component.isSelected());
    }

    @Test
    void shouldResolveSelectedWithString() {
        var component = anyComponent();
        component.setSubmittedValue(Boolean.TRUE.toString());
        assertTrue(component.isSelected());
        component.setSubmittedValue(Boolean.FALSE.toString());
        assertFalse(component.isSelected());
    }

    @Test
    void shouldResolveSelectedWithOther() {
        var component = anyComponent();
        component.setSubmittedValue(Generators.runtimeExceptions().next());
        assertFalse(component.isSelected());
    }
}
