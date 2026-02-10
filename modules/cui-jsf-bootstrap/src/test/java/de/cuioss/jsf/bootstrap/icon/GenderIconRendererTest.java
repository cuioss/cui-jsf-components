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
package de.cuioss.jsf.bootstrap.icon;

import static de.cuioss.jsf.bootstrap.icon.GenderIconComponentTest.GENDER_MALE_TITLE;
import static de.cuioss.jsf.bootstrap.icon.GenderIconComponentTest.GENDER_UNKNOWN_TITLE;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.uimodel.model.Gender;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
@DisplayName("Tests for GenderIconRenderer")
class GenderIconRendererTest extends AbstractComponentRendererTest<IconRenderer> {

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    private static final String ICON_PREFIX = "cui-icon ";
    private static final String GENDER_UNKNOWN_CSS = ICON_PREFIX + Gender.UNKNOWN.getCssClass();
    private static final String GENDER_MALE_CSS = ICON_PREFIX + Gender.MALE.getCssClass();

    @Nested
    @DisplayName("Rendering tests")
    class RenderingTests {

        @Test
        @DisplayName("Should render minimal gender icon with default settings")
        void shouldRenderMinimalGenderIcon(FacesContext facesContext) throws Exception {
            // Arrange
            var component = getComponent();

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(GENDER_UNKNOWN_CSS)
                    .withAttribute(AttributeName.TITLE, GENDER_UNKNOWN_TITLE);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render gender icon with specific gender")
        void shouldRenderGenderIconWithSpecificGender(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new GenderIconComponent();
            component.setGender(Gender.MALE);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(GENDER_MALE_CSS)
                    .withAttribute(AttributeName.TITLE, GENDER_MALE_TITLE);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Override
    protected UIComponent getComponent() {
        return new GenderIconComponent();
    }
}
