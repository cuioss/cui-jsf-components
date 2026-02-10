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
package de.cuioss.jsf.components.html.fieldset;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.components.CuiFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.ReverseConverter;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"disabled", "legendKey", "legendValue", "legendConverter", "legendEscape"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
class FieldsetComponentTest extends AbstractComponentTest<FieldsetComponent> {

    @BeforeEach
    void configureCuiComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    protected static final String MESSAGE_KEY = "some.key";

    protected static final String MESSAGE_VALUE = "abc";

    @Test
    @DisplayName("Should resolve legend based on configuration")
    void shouldResolveLegend() {
        // Arrange - Test with no legend configuration
        var component = anyComponent();

        // Act & Assert - No legend configured
        assertNull(component.resolveLegend(), "Legend should be null when no legend is configured");

        // Arrange - Test with legendKey
        component.setLegendKey(MESSAGE_KEY);

        // Act & Assert - With legendKey
        assertEquals(MESSAGE_KEY, component.resolveLegend(),
                "Legend should be the message key when legendKey is set");

        // Arrange - Test with legendValue
        component = anyComponent();
        component.setLegendValue(MESSAGE_VALUE);

        // Act & Assert - With legendValue
        assertEquals(MESSAGE_VALUE, component.resolveLegend(),
                "Legend should be the message value when legendValue is set");

        // Arrange - Test with legendValue and converter
        component = anyComponent();
        component.setLegendValue(MESSAGE_VALUE);
        component.setLegendConverter(new ReverseConverter());

        // Act & Assert - With legendValue and converter
        assertEquals("cba", component.resolveLegend(),
                "Legend should be converted when legendConverter is set");
    }

    @Test
    @DisplayName("Should handle disabled attribute correctly")
    void shouldHandleDisableAttribute(FacesContext facesContext) {
        // Arrange - Initial state (not disabled)
        var component = anyComponent();

        // Act - Process PreRenderComponentEvent with disabled=false (default)
        component.processEvent(new PreRenderComponentEvent(facesContext, component));

        // Assert - No disabled attribute should be present
        assertFalse(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME),
                "Disabled attribute should not be present when disabled=false");

        // Arrange - Set disabled to true
        component.setDisabled(true);

        // Act - Process PreRenderComponentEvent with disabled=true
        component.processEvent(new PreRenderComponentEvent(facesContext, component));

        // Assert - Disabled attribute should be present
        assertTrue(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME),
                "Disabled attribute should be present when disabled=true");

        // Arrange - Set disabled back to false
        component.setDisabled(false);

        // Act - Process PreRenderComponentEvent with disabled=false
        component.processEvent(new PreRenderComponentEvent(facesContext, component));

        // Assert - Disabled attribute should be removed
        assertFalse(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME),
                "Disabled attribute should be removed when disabled=false");

        // Arrange - Set disabled to true but use a different event
        component.setDisabled(true);

        // Act - Process PostAddToViewEvent (not PreRenderComponentEvent)
        component.processEvent(new PostAddToViewEvent(facesContext, component));

        // Assert - Should ignore events other than PreRenderComponentEvent
        assertFalse(component.getPassThroughAttributes(true).containsKey(FieldsetComponent.DISABLED_ATTRIBUTE_NAME),
                "Component should ignore events other than PreRenderComponentEvent");
    }

    @Test
    @DisplayName("Should provide correct component metadata")
    void shouldProvideCorrectMetadata() {
        // Arrange
        var component = anyComponent();

        // Act & Assert - Check renderer type
        assertEquals(CuiFamily.FIELDSET_RENDERER, component.getRendererType(),
                "Component should have the correct renderer type");

        // Act & Assert - Check component family
        assertEquals(CuiFamily.COMPONENT_FAMILY, component.getFamily(),
                "Component should have the correct component family");
    }
}
