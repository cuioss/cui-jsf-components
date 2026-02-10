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

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.converter.StringIdentConverter;
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
@DisplayName("Tests for MimeTypeIconRenderer")
class MimeTypeIconRendererTest extends AbstractComponentRendererTest<MimeTypeIconRenderer> {

    @BeforeEach
    void configureCuiComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    private static final String TITLE = "someTitle";

    @Nested
    @DisplayName("Basic rendering tests")
    class BasicRenderingTests {

        @Test
        @DisplayName("Should render minimal component with default settings")
        void shouldRenderMinimalComponent(FacesContext facesContext) throws Exception {
            // Arrange
            var component = getComponent();

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack");
            // Layer 1
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                    .currentHierarchyUp();
            // Layer 2
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                    .currentHierarchyUp();
            // Layer 3
            expected.withNode(Node.ITALIC)
                    .withStyleClass(
                            "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                    .currentHierarchyUp();
            // Layer 4
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Decorator tests")
    class DecoratorTests {

        @Test
        @DisplayName("Should render component with custom decorator class")
        void shouldRenderWithCustomDecorator(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new MimeTypeIconComponent();
            component.setDecoratorClass(TITLE);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack");
            // Layer 1
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                    .currentHierarchyUp();
            // Layer 2
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x " + TITLE).currentHierarchyUp();
            // Layer 3
            expected.withNode(Node.ITALIC)
                    .withStyleClass(
                            "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                    .currentHierarchyUp();
            // Layer 4
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("MimeType tests")
    class MimeTypeTests {

        @Test
        @DisplayName("Should render component with specific mime type")
        void shouldRenderWithSpecificMimeType(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new MimeTypeIconComponent();
            component.setMimeTypeIcon(MimeTypeIcon.AUDIO_BASIC);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack");
            // Layer 1
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                    .currentHierarchyUp();
            // Layer 2
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                    .currentHierarchyUp();
            // Layer 3
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder "
                    + MimeTypeIcon.AUDIO_BASIC.getPlaceholder()).currentHierarchyUp();
            // Layer 4
            expected.withNode(Node.ITALIC)
                    .withStyleClass("cui-mime-type cui-icon-stack-1x " + MimeTypeIcon.AUDIO_BASIC.getIconClass());

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Size tests")
    class SizeTests {

        @Test
        @DisplayName("Should render component with custom size")
        void shouldRenderWithCustomSize(FacesContext facesContext) throws Exception {
            // Arrange
            var component = new MimeTypeIconComponent();
            component.setSize(ContextSize.LG.name());

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack cui-icon-lg");
            // Layer 1
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                    .currentHierarchyUp();
            // Layer 2
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                    .currentHierarchyUp();
            // Layer 3
            expected.withNode(Node.ITALIC)
                    .withStyleClass(
                            "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                    .currentHierarchyUp();
            // Layer 4
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");

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
            var component = new MimeTypeIconComponent();
            component.setTitleValue(TITLE);
            component.setTitleConverter(new StringIdentConverter());

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack")
                    .withAttribute(AttributeName.TITLE, TITLE);
            // Layer 1
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                    .currentHierarchyUp();
            // Layer 2
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                    .currentHierarchyUp();
            // Layer 3
            expected.withNode(Node.ITALIC)
                    .withStyleClass(
                            "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                    .currentHierarchyUp();
            // Layer 4
            expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");

            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Override
    protected UIComponent getComponent() {
        return new MimeTypeIconComponent();
    }
}
