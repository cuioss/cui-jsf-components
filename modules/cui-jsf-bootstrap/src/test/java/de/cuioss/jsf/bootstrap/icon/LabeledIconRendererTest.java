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

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.IconProvider;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for LabeledIconRenderer")
class LabeledIconRendererTest extends AbstractComponentRendererTest<LabeledIconRenderer> {

    @BeforeEach
    void configureCuiComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    private static final String ICON = "cui-icon-alarm";
    private static final String ICON_PREFIX = "cui-icon";
    private static final String ICON_RESULT_CSS = ICON_PREFIX + " " + ICON;
    private static final String SOME_TITLE = "hello";

    @Nested
    @DisplayName("Basic rendering tests")
    class BasicRenderingTests {

        @Test
        @DisplayName("Should render minimal component with default settings")
        void shouldRenderMinimalComponent(FacesContext facesContext) throws Exception {
            // Arrange
            var component = getComponent();

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER);
            // Icon
            expected.withNode(Node.SPAN)
                    .withStyleClass(IconProvider.FALLBACK_ICON_STRING)
                    .currentHierarchyUp();
            // Text
            expected.withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT)
                    .currentHierarchyUp();

            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with label content")
        void shouldRenderWithLabelContent(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new LabeledIconComponent();
            component.setLabelValue(SOME_TITLE);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER);
            // Icon
            expected.withNode(Node.SPAN)
                    .withStyleClass(IconProvider.FALLBACK_ICON_STRING)
                    .currentHierarchyUp();
            // Text
            expected.withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT)
                    .withTextContent(SOME_TITLE)
                    .currentHierarchyUp();

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Icon rendering tests")
    class IconRenderingTests {

        @Test
        @DisplayName("Should render component with custom icon and label")
        void shouldRenderWithCustomIconAndLabel(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new LabeledIconComponent();
            component.setLabelValue(SOME_TITLE);
            component.setIcon(ICON);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER);
            // Icon
            expected.withNode(Node.SPAN)
                    .withStyleClass(ICON_RESULT_CSS)
                    .currentHierarchyUp();
            // Text
            expected.withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT)
                    .withTextContent(SOME_TITLE)
                    .currentHierarchyUp();

            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with right-aligned icon")
        void shouldRenderWithRightAlignedIcon(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new LabeledIconComponent();
            component.setIconAlign(AlignHolder.RIGHT.name());
            component.setLabelValue(SOME_TITLE);
            component.setIcon(ICON);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER);
            // Text (comes first with right-aligned icon)
            expected.withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT)
                    .withTextContent(SOME_TITLE)
                    .currentHierarchyUp();
            // Icon
            expected.withNode(Node.SPAN)
                    .withStyleClass(ICON_RESULT_CSS)
                    .currentHierarchyUp();

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Title attribute tests")
    class TitleAttributeTests {

        @Test
        @DisplayName("Should render component with title attribute")
        void shouldRenderWithTitleAttribute(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new LabeledIconComponent();
            component.setLabelValue(SOME_TITLE);
            component.setTitleValue(SOME_TITLE);
            component.setIcon(ICON);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER)
                    .withAttribute(AttributeName.TITLE, SOME_TITLE);
            // Icon
            expected.withNode(Node.SPAN)
                    .withStyleClass(ICON_RESULT_CSS)
                    .currentHierarchyUp();
            // Text
            expected.withNode(Node.SPAN)
                    .withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT)
                    .withTextContent(SOME_TITLE)
                    .currentHierarchyUp();

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Override
    protected UIComponent getComponent() {
        return new LabeledIconComponent();
    }
}
