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

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.StyleAttributeProviderImpl;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.config.renderer.VetoRenderAttributeAssert;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.test.jsf.renderer.CommonRendererAsserts;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for FieldsetRenderer")
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VetoRenderAttributeAssert({CommonRendererAsserts.STYLE, CommonRendererAsserts.STYLE_CLASS})
class FieldsetRendererTest extends AbstractComponentRendererTest<FieldsetRenderer> {

    @BeforeEach
    void configureCuiComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    private static final String LEGEND_TEXT = "Some Legend";

    @Test
    @DisplayName("Should render minimal fieldset without any attributes")
    void shouldRenderMinimal(FacesContext facesContext) throws Exception {
        // Arrange
        var component = getComponent();
        var expected = new HtmlTreeBuilder().withNode(Node.FIELDSET);

        // Act & Assert - Minimal fieldset should render as a simple fieldset element
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    @DisplayName("Should render fieldset with legend")
    void shouldRenderLegend(FacesContext facesContext) throws Exception {
        // Arrange
        var component = new FieldsetComponent();
        component.setLegendValue(LEGEND_TEXT);

        var expected = new HtmlTreeBuilder()
                .withNode(Node.FIELDSET)
                .withNode(Node.LEGEND)
                .withTextContent(LEGEND_TEXT);

        // Act & Assert - Fieldset with legend should render with a legend element containing the text
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    @DisplayName("Should render fully configured fieldset with all attributes")
    void shouldRenderFull(FacesContext facesContext) throws Exception {
        // Arrange
        var component = new FieldsetComponent();
        component.setLegendValue(LEGEND_TEXT);
        component.setDisabled(true);
        component.processEvent(new PreRenderComponentEvent(facesContext, component));
        component.setStyle("style");
        component.setStyleClass("styleClass");

        var expected = new HtmlTreeBuilder()
                .withNode(Node.FIELDSET)
                .withAttribute(FieldsetComponent.DISABLED_ATTRIBUTE_NAME, FieldsetComponent.DISABLED_ATTRIBUTE_NAME)
                .withAttribute(AttributeName.CLASS, "styleClass")
                .withAttribute(StyleAttributeProviderImpl.KEY, "style")
                .withNode(Node.LEGEND)
                .withTextContent(LEGEND_TEXT);

        // Act & Assert - Fully configured fieldset should render with all attributes and legend
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Override
    protected UIComponent getComponent() {
        // Create a new instance of the component for each test
        return new FieldsetComponent();
    }
}
