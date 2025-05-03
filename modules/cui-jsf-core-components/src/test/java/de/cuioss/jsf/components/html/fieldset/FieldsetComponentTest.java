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
package de.cuioss.jsf.components.html.fieldset;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.components.CuiFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.mocks.ReverseConverter;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"disabled", "legendKey", "legendValue", "legendConverter", "legendEscape"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
class FieldsetComponentTest extends AbstractComponentTest<FieldsetComponent> {

    protected static final String MESSAGE_KEY = "some.key";

    protected static final String MESSAGE_VALUE = "abc";

    @Test
    void shouldResolveLegend() {
        var component = anyComponent();
        assertNull(component.resolveLegend());
        component.setLegendKey(MESSAGE_KEY);
        assertEquals(MESSAGE_KEY, component.resolveLegend());
        component = anyComponent();
        component.setLegendValue(MESSAGE_VALUE);
        assertEquals(MESSAGE_VALUE, component.resolveLegend());
        component = anyComponent();
        component.setLegendValue(MESSAGE_VALUE);
        component.setLegendConverter(new ReverseConverter());
        assertEquals("cba", component.resolveLegend());
    }

    @Test
    void shouldHandleDisableAttribute() {
        var component = anyComponent();
        component.processEvent(new PreRenderComponentEvent(getFacesContext(), component));
        assertFalse(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME));
        component.setDisabled(true);
        component.processEvent(new PreRenderComponentEvent(getFacesContext(), component));
        assertTrue(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME));
        component.setDisabled(false);
        component.processEvent(new PreRenderComponentEvent(getFacesContext(), component));
        assertFalse(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME));
        // Should ignore other events
        component.setDisabled(true);
        component.processEvent(new PostAddToViewEvent(getFacesContext(), component));
        assertFalse(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME));
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(CuiFamily.FIELDSET_RENDERER, anyComponent().getRendererType());
        assertEquals(CuiFamily.COMPONENT_FAMILY, anyComponent().getFamily());
    }
}
