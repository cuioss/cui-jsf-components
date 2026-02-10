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
package de.cuioss.jsf.bootstrap.lazyloading;

import static de.cuioss.jsf.bootstrap.CssCuiBootstrap.CUI_LAZY_LOADING;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.converter.nameprovider.DisplayNameConverter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.notification.NotificationBoxComponent;
import de.cuioss.jsf.bootstrap.notification.NotificationBoxRenderer;
import de.cuioss.jsf.bootstrap.waitingindicator.WaitingIndicatorComponent;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link LazyLoadingRenderer}
 */
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for LazyLoadingRenderer")
class LazyLoadingRendererTest extends AbstractComponentRendererTest<LazyLoadingRenderer> {

    private static final String DEFAULT_ID = "j_id__v_0";

    @Override
    protected LazyLoadingComponent getComponent() {
        return new LazyLoadingComponent();
    }

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
        decorator.registerUIComponent(NotificationBoxComponent.class).registerRenderer(NotificationBoxRenderer.class)
                .registerConverter(DisplayNameConverter.class)
                .registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.WAITING_INDICATOR_RENDERER)
                .registerUIComponent(WaitingIndicatorComponent.class);
    }

    @Nested
    @DisplayName("Tests for basic rendering")
    class BasicRenderingTests {

        @Test
        @DisplayName("Should render minimal component correctly")
        void shouldRenderMinimal(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = getComponent();
            component.getClientId();
            component.processEvent(new PostAddToViewEvent(component));

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                    .withAttribute(LazyLoadingRenderer.DATA_WAITING_INDICATOR_ID, DEFAULT_ID + ":waitingIndicator")
                    .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                    .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                    .withNode("WaitingIndicatorComponent").withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator")
                    .withAttribute("style", "display: block;");
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render component with additional style class")
        void shouldRenderAdditionalStyleClass(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = getComponent();
            var styleClass = "additiv";
            component.setStyleClass(styleClass);
            component.getClientId();
            component.processEvent(new PostAddToViewEvent(component));

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                    .withAttribute(LazyLoadingRenderer.DATA_WAITING_INDICATOR_ID, DEFAULT_ID + ":waitingIndicator")
                    .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(styleClass)
                            .append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                    .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                    .withNode("WaitingIndicatorComponent").withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator")
                    .withAttribute("style", "display: block;");
            assertRenderResult(component, expected.getDocument(), facesContext);
        }

        @Test
        @DisplayName("Should render spinner correctly")
        void shouldRenderSpinner(FacesContext facesContext) throws Exception {
            // Arrange
            final var component = createComponentWithContent();
            component.getClientId();
            component.processEvent(new PostAddToViewEvent(component));

            // Act & Assert
            var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                    .withAttribute(LazyLoadingRenderer.DATA_WAITING_INDICATOR_ID, DEFAULT_ID + ":waitingIndicator")
                    .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                    .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                    .withNode("WaitingIndicatorComponent").withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator")
                    .withAttribute("style", "display: block;");
            assertRenderResult(component, expected.getDocument(), facesContext);
        }
    }

    @Test
    void shouldRenderContent(FacesContext facesContext) throws Exception {
        final var component = createComponentWithContent();
        component.getClientId();
        component.setInitialized(true);
        component.processEvent(new PostAddToViewEvent(component));
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                .withAttribute(LazyLoadingRenderer.DATA_WAITING_INDICATOR_ID, DEFAULT_ID + ":waitingIndicator")
                .withAttribute(AttributeName.DATA_CONTENT_LOADED, "true")
                .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                .withNode(Node.DIV).withAttribute(LazyLoadingRenderer.DATA_LAZY_LOADING_CONTENT, LazyLoadingRenderer.DATA_LAZY_LOADING_CONTENT)
                .withAttributeNameAndId(DEFAULT_ID + "_content").withNode(Node.SPAN).withTextContent("lazy loading...")
                .currentHierarchyUp().currentHierarchyUp().withNode("WaitingIndicatorComponent")
                .withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator").withAttribute("style", "display: none;");
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldRenderContentAfterDecode(FacesContext facesContext) throws Exception {
        final var component = createComponentWithContent();
        component.getClientId();
        facesContext.getViewRoot().getChildren().add(component);
        component.processEvent(new PostAddToViewEvent(component));
        ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getParameterMap()
                .put(DEFAULT_ID + "_" + LazyLoadingComponent.ID_SUFFIX_IS_LOADED, new String[]{"true"});
        component.decode(facesContext);
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                .withAttribute(LazyLoadingRenderer.DATA_WAITING_INDICATOR_ID, DEFAULT_ID + ":waitingIndicator")
                .withAttribute(AttributeName.DATA_CONTENT_LOADED, "true")
                .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                .withNode(Node.DIV).withAttribute(LazyLoadingRenderer.DATA_LAZY_LOADING_CONTENT, LazyLoadingRenderer.DATA_LAZY_LOADING_CONTENT)
                .withAttributeNameAndId(DEFAULT_ID + "_content").withNode(Node.SPAN).withTextContent("lazy loading...")
                .currentHierarchyUp().currentHierarchyUp().withNode("WaitingIndicatorComponent")
                .withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator").withAttribute("style", "display: none;");
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldRenderNotificationBox(FacesContext facesContext) throws Exception {
        final var component = createComponentWithContent();
        component.getClientId();
        component.setInitialized(true);
        component.setNotificationBoxValue(new de.cuioss.uimodel.nameprovider.DisplayName("Error"));
        component.setRenderContent(false);
        component.processEvent(new PostAddToViewEvent(component));
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                .withAttribute(AttributeName.DATA_CONTENT_LOADED, "true")
                .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                .withAttribute("data-ignore-auto-update", "false")
                .withAttribute(LazyLoadingRenderer.DATA_WAITING_INDICATOR_ID, DEFAULT_ID + ":waitingIndicator")
                .withAttribute("data-async", "false").withNode(Node.DIV)
                .withAttribute(LazyLoadingRenderer.DATA_LAZY_LOADING_CONTENT, LazyLoadingRenderer.DATA_LAZY_LOADING_CONTENT)
                .withAttributeNameAndId(DEFAULT_ID + "_content").withNode(Node.DIV)
                .withAttribute("data-resultNotificationBox", "data-resultNotificationBox")
                .withStyleClass("alert alert-warning").withAttribute(AttributeName.ROLE, "alert")
                .withTextContent("Error").currentHierarchyUp().currentHierarchyUp()
                .withNode("WaitingIndicatorComponent").withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator")
                .withAttribute("style", "display: none;");
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    private LazyLoadingComponent createComponentWithContent() {
        final var component = getComponent();
        var htmlOutputText = new HtmlOutputText();
        htmlOutputText.setValue("lazy loading...");
        component.getChildren().add(htmlOutputText);
        return component;
    }
}
