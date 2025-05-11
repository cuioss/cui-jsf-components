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
package de.cuioss.jsf.bootstrap.icon;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.IconProvider;
import de.cuioss.jsf.bootstrap.icon.support.IconSize;
import de.cuioss.jsf.bootstrap.icon.support.IconState;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
@DisplayName("Tests for IconRenderer")
class IconRendererTest extends AbstractComponentRendererTest<IconRenderer> {

    private static final String ICON = "cui-icon-alarm";
    private static final String ICON_PREFIX = "cui-icon";
    private static final String ICON_RESULT_CSS = ICON_PREFIX + " " + ICON;
    private static final String SOME_KEY = "some.key";

    @Nested
    @DisplayName("Basic rendering tests")
    class BasicRenderingTests {

        @Test
        @DisplayName("Should render icon with specified icon class")
        void shouldRenderIconWithSpecifiedClass(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new IconComponent();
            component.setIcon(ICON);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(ICON_RESULT_CSS);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render minimal icon with default settings")
        void shouldRenderMinimalIcon(FacesContext facesContext) throws IOException {
            // Arrange
            var component = getComponent();

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(IconProvider.FALLBACK_ICON_STRING);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render fallback icon when library is invalid")
        void shouldRenderFallbackIconForInvalidLibrary(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new IconComponent();
            component.setIcon(SOME_KEY);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(IconProvider.FALLBACK_ICON_STRING);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Title rendering tests")
    class TitleRenderingTests {

        @Test
        @DisplayName("Should render title from bundle key")
        void shouldRenderTitleFromBundleKey(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new IconComponent();
            component.setIcon(ICON);
            component.setTitleKey(SOME_KEY);

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(ICON_RESULT_CSS)
                    .withAttribute(AttributeName.TITLE, SOME_KEY);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Nested
    @DisplayName("Style rendering tests")
    class StyleRenderingTests {

        @Test
        @DisplayName("Should render icon with specified state")
        void shouldRenderIconWithState(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new IconComponent();
            component.setIcon(ICON);
            component.setState(ContextState.DANGER.name());

            // Act & Assert
            StyleClassBuilder classBuilder = new StyleClassBuilderImpl(ICON_RESULT_CSS);
            classBuilder.append(IconState.DANGER.getStyleClass());
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(classBuilder);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render icon with specified size")
        void shouldRenderIconWithSize(FacesContext facesContext) throws IOException {
            // Arrange
            var component = new IconComponent();
            component.setIcon(ICON);
            component.setSize(ContextSize.LG.name());

            // Act & Assert
            StyleClassBuilder classBuilder = new StyleClassBuilderImpl(ICON_RESULT_CSS);
            classBuilder.append(IconSize.LG.getStyleClass());
            var expected = new HtmlTreeBuilder().withNode(Node.SPAN)
                    .withStyleClass(classBuilder);
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Override
    protected UIComponent getComponent() {
        return new IconComponent();
    }
}
