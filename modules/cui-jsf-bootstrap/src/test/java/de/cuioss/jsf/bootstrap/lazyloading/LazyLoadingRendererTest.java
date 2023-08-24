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
package de.cuioss.jsf.bootstrap.lazyloading;

import static de.cuioss.jsf.bootstrap.CssCuiBootstrap.CUI_LAZY_LOADING;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.event.PostAddToViewEvent;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;

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
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.uimodel.nameprovider.DisplayName;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class LazyLoadingRendererTest extends AbstractComponentRendererTest<LazyLoadingRenderer>
        implements ComponentConfigurator {

    private static final String DEFAULT_ID = "j_id__v_0";

    @Override
    protected LazyLoadingComponent getComponent() {
        return new LazyLoadingComponent();
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(NotificationBoxComponent.class).registerRenderer(NotificationBoxRenderer.class)
                .registerConverter(DisplayNameConverter.class)
                .registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.WAITING_INDICATOR_RENDERER)
                .registerUIComponent(WaitingIndicatorComponent.class);
    }

    @Test
    void shouldRenderMinimal() {
        final var component = getComponent();
        component.getClientId();
        component.processEvent(new PostAddToViewEvent(component));
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                .withAttribute("data-lazyloading-waiting-indicator-id", DEFAULT_ID + ":waitingIndicator")
                .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                .withNode("WaitingIndicatorComponent").withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator")
                .withAttribute("style", "display: block;");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderAdditionalStyleClass() {
        final var component = getComponent();
        var styleClass = "additiv";
        component.setStyleClass(styleClass);
        component.getClientId();
        component.processEvent(new PostAddToViewEvent(component));
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                .withAttribute("data-lazyloading-waiting-indicator-id", DEFAULT_ID + ":waitingIndicator")
                .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(styleClass)
                        .append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                .withNode("WaitingIndicatorComponent").withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator")
                .withAttribute("style", "display: block;");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderSpinner() {
        final var component = createComponentWithContent();
        component.getClientId();
        component.processEvent(new PostAddToViewEvent(component));
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                .withAttribute("data-lazyloading-waiting-indicator-id", DEFAULT_ID + ":waitingIndicator")
                .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                .withNode("WaitingIndicatorComponent").withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator")
                .withAttribute("style", "display: block;");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderContent() {
        final var component = createComponentWithContent();
        component.getClientId();
        component.setInitialized(true);
        component.processEvent(new PostAddToViewEvent(component));
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                .withAttribute("data-lazyloading-waiting-indicator-id", DEFAULT_ID + ":waitingIndicator")
                .withAttribute(AttributeName.DATA_CONTENT_LOADED, "true")
                .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                .withNode(Node.DIV).withAttribute("data-lazyloading-content", "data-lazyloading-content")
                .withAttributeNameAndId(DEFAULT_ID + "_content").withNode(Node.SPAN).withTextContent("lazy loading...")
                .currentHierarchyUp().currentHierarchyUp().withNode("WaitingIndicatorComponent")
                .withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator").withAttribute("style", "display: none;");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderContentAfterDecode() {
        final var component = createComponentWithContent();
        component.getClientId();
        var facesContext = getFacesContext();
        facesContext.getViewRoot().getChildren().add(component);
        component.processEvent(new PostAddToViewEvent(component));
        ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getParameterMap()
                .put(DEFAULT_ID + "_isloaded", new String[] { "true" });
        component.decode(facesContext);
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                .withAttribute("data-lazyloading-waiting-indicator-id", DEFAULT_ID + ":waitingIndicator")
                .withAttribute(AttributeName.DATA_CONTENT_LOADED, "true")
                .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                .withAttribute("data-ignore-auto-update", "false").withAttribute("data-async", "false")
                .withNode(Node.DIV).withAttribute("data-lazyloading-content", "data-lazyloading-content")
                .withAttributeNameAndId(DEFAULT_ID + "_content").withNode(Node.SPAN).withTextContent("lazy loading...")
                .currentHierarchyUp().currentHierarchyUp().withNode("WaitingIndicatorComponent")
                .withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator").withAttribute("style", "display: none;");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderNotificationBox() {
        final var component = createComponentWithContent();
        component.getClientId();
        component.setInitialized(true);
        component.setNotificationBoxValue(new DisplayName("Error"));
        component.setRenderContent(false);
        component.processEvent(new PostAddToViewEvent(component));
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withAttributeNameAndId(DEFAULT_ID)
                .withAttribute(AttributeName.DATA_CONTENT_LOADED, "true")
                .withStyleClass(CUI_LAZY_LOADING.getStyleClassBuilder().append(CssCuiBootstrap.UI_HIDDEN_CONTAINER))
                .withAttribute("data-ignore-auto-update", "false")
                .withAttribute("data-lazyloading-waiting-indicator-id", DEFAULT_ID + ":waitingIndicator")
                .withAttribute("data-async", "false").withNode(Node.DIV)
                .withAttribute("data-lazyloading-content", "data-lazyloading-content")
                .withAttributeNameAndId(DEFAULT_ID + "_content").withNode(Node.DIV)
                .withAttribute("data-resultNotificationBox", "data-resultNotificationBox")
                .withStyleClass("alert alert-warning").withAttribute(AttributeName.ROLE, "alert")
                .withTextContent("Error").currentHierarchyUp().currentHierarchyUp()
                .withNode("WaitingIndicatorComponent").withAttributeNameAndId(DEFAULT_ID + ":waitingIndicator")
                .withAttribute("style", "display: none;");
        assertRenderResult(component, expected.getDocument());
    }

    private LazyLoadingComponent createComponentWithContent() {
        final var component = getComponent();
        var htmlOutputText = new HtmlOutputText();
        htmlOutputText.setValue("lazy loading...");
        component.getChildren().add(htmlOutputText);
        return component;
    }
}
