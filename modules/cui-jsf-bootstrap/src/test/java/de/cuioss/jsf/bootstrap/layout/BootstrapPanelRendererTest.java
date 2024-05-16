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
package de.cuioss.jsf.bootstrap.layout;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.converter.StringIdentConverter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageComponent;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageRenderer;
import de.cuioss.jsf.bootstrap.waitingindicator.WaitingIndicatorComponent;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.tools.string.Joiner;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.behavior.AjaxBehavior;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.component.html.HtmlSelectBooleanCheckbox;
import jakarta.faces.component.html.HtmlSelectOneRadio;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class BootstrapPanelRendererTest extends AbstractComponentRendererTest<BootstrapPanelRenderer>
    implements ComponentConfigurator {

    private static final String CLIENT_ID = "j_id__v_0";

    private static final String CLIENT_ID_HEADING = CLIENT_ID + "_heading";

    private static final String CLIENT_ID_BODY = CLIENT_ID + "_body";

    private static final String CLIENT_ID_ISEXPANDED = CLIENT_ID + "_isexpanded";

    private static final String CLIENT_ID_TOGGLER = CLIENT_ID + "_toggler";

    private static final String CLIENT_ID_ICON = CLIENT_ID + "_icon";

    private static final String CLIENT_ID_FOOTER = CLIENT_ID + "_footer";

    private static final String TEXT_VALUE = "FOO";

    @Test
    void shouldRenderMinimal() {
        final var component = new BootstrapPanelComponent();
        final var params = new PanelParams();
        params.isCollapsed = false;
        final var expected = getHtmlTree(params);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderSpinnerOnlyOnceTest() {
        final var component = new BootstrapPanelComponent();
        component.setId(CLIENT_ID);
        final var params = new PanelParams();
        params.renderHeader = true;
        params.isCollapsed = true;
        params.renderSpinner = true;
        params.renderFooter = false;
        params.asyncUpdate = false;
        params.deferredLoading = true;
        params.headerValue = TEXT_VALUE;
        component.setHeaderValue(TEXT_VALUE);
        component.setHeaderConverter(new StringIdentConverter());
        component.setDeferred(params.deferredLoading);
        // --> render spinner
        component.setCollapsed(params.isCollapsed);
        var expected = getHtmlTree(params);
        assertRenderResult(component, expected.getDocument());
        getRequestConfigDecorator().setRequestParameter(Joiner.on('_').join(component.getClientId(), "isexpanded"),
            "true");
        component.decode(getFacesContext());
        params.isCollapsed = false;
        params.renderSpinner = false;
        // --> render childs
        component.setCollapsed(params.isCollapsed);
        expected = getHtmlTree(params);
        assertRenderResult(component, expected.getDocument());
        params.isCollapsed = true;
        // --> render childs also!
        component.setCollapsed(params.isCollapsed);
        expected = getHtmlTree(params);
        assertRenderResult(component, expected.getDocument());
    }

    private static HtmlTreeBuilder getHtmlTree(final PanelParams params) {
        final var expected = new HtmlTreeBuilder();
        // panel
        expected.withNode(Node.DIV).withAttribute(AttributeName.ID, CLIENT_ID)
            .withAttribute(AttributeName.NAME, CLIENT_ID).withStyleClass("panel panel-default cui-panel");
        if (params.asyncUpdate) {
            expected.withAttribute(AttributeName.DATA_ASYNCUPDATE, "true");
        }
        if (params.deferredLoading) {
            expected.withAttribute(AttributeName.DATA_DEFERRED, "true");
            if (!params.renderSpinner) {
                expected.withAttribute(AttributeName.DATA_CONTENT_LOADED, "true");
            }
        }
        expected.withAttribute(AttributeName.DATA_NOT_COLLAPSED, String.valueOf(!params.isCollapsed));
        // state holder
        expected.withNode(Node.INPUT).withAttribute(AttributeName.ID, CLIENT_ID_ISEXPANDED)
            .withAttribute(AttributeName.NAME, CLIENT_ID_ISEXPANDED).withAttribute(AttributeName.TYPE, "hidden")
            .withAttribute(AttributeName.VALUE, String.valueOf(!params.isCollapsed)).currentHierarchyUp();
        if (params.renderHeader) {
            // header
            expected.withNode(Node.DIV).withAttribute(AttributeName.ID, CLIENT_ID_TOGGLER)
                .withAttribute(AttributeName.NAME, CLIENT_ID_TOGGLER)
                .withAttribute(AttributeName.ARIA_EXPANDED, String.valueOf(!params.isCollapsed))
                .withStyleClass("panel-heading");
            if (params.isCollapsible) {
                expected.withAttribute(AttributeName.DATA_TOGGLE, "collapse")
                    .withAttribute(AttributeName.DATA_TARGET, "#" + CLIENT_ID_BODY)
                    .withAttribute(AttributeName.ARIA_CONTROLS, CLIENT_ID_BODY)
                    .withAttribute(AttributeName.ROLE, "button").withStyleClass("panel-heading cui-collapsible");
            }
            // heading
            expected.withNode(params.headerTag).withAttribute(AttributeName.ID, CLIENT_ID_HEADING)
                .withAttribute(AttributeName.NAME, CLIENT_ID_HEADING).withStyleClass("panel-title")
                .withTextContent(params.headerValue);
            // collapse icon
            if (params.isCollapsible) {
                expected.withNode(Node.SPAN).withAttribute(AttributeName.ID, CLIENT_ID_ICON)
                    .withAttribute(AttributeName.NAME, CLIENT_ID_ICON)
                    .withStyleClass("cui-icon cui-collapsible-icon").currentHierarchyUp();
            }
            // leave heading
            expected.currentHierarchyUp();
            // leave header
            expected.currentHierarchyUp();
        } else if (params.renderHeaderFacetOnly) {
            expected.withNode("UIForm").currentHierarchyUp();
        }
        // body container
        expected.withNode(Node.DIV).withAttribute(AttributeName.ID, CLIENT_ID_BODY)
            .withAttribute(AttributeName.NAME, CLIENT_ID_BODY)
            .withAttribute(AttributeName.ARIA_LABELLEDBY, CLIENT_ID_TOGGLER)
            .withStyleClass("panel-collapse collapse" + (params.isCollapsed ? "" : " in")).withNode(Node.DIV)
            .withStyleClass("panel-body");
        if (params.renderSpinner) {
            // spinner
            expected.withNode("WaitingIndicatorComponent").currentHierarchyUp();
        } else if (params.childContent != null) {
            expected.withNode(params.childContent);
        }
        // leave inner body
        expected.currentHierarchyUp().currentHierarchyUp();
        // footer
        if (params.renderFooter) {
            expected.withNode(Node.DIV).withAttribute(AttributeName.ID, CLIENT_ID_FOOTER)
                .withAttribute(AttributeName.NAME, CLIENT_ID_FOOTER).withStyleClass("panel-footer")
                .withTextContent(params.footerValue);
        }
        return expected;
    }

    @Test
    void shouldRenderWithChildren() {
        final var component = new BootstrapPanelComponent();
        component.getChildren().add(new HtmlOutputText());
        getComponentConfigDecorator().registerMockRendererForHtmlOutputText().registerBehavior(AjaxBehavior.BEHAVIOR_ID,
            AjaxBehavior.class);
        final var params = new PanelParams();
        params.renderHeader = false;
        params.isCollapsed = false;
        params.childContent = Node.SPAN.getContent();
        final var expected = getHtmlTree(params);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderFooter() {
        final var component = new BootstrapPanelComponent();
        component.setCollapsed(true);
        component.setFooterValue(TEXT_VALUE);
        component.setFooterConverter(new StringIdentConverter());
        final var params = new PanelParams();
        params.isCollapsed = true;
        params.renderFooter = true;
        params.footerValue = TEXT_VALUE;
        final var expected = getHtmlTree(params);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderHeaderTag() {
        final var component = new BootstrapPanelComponent();
        component.setHeaderValue(TEXT_VALUE);
        component.setHeaderConverter(new StringIdentConverter());
        component.setHeaderTag("h2");
        final var params = new PanelParams();
        params.renderHeader = true;
        params.headerTag = Node.H2;
        params.headerValue = TEXT_VALUE;
        final var expected = getHtmlTree(params);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldNotRenderCollapsibleHeader() {
        final var component = new BootstrapPanelComponent();
        component.setCollapsible(false);
        component.setHeaderValue(TEXT_VALUE);
        component.setHeaderConverter(new StringIdentConverter());
        final var params = new PanelParams();
        params.renderHeader = true;
        params.isCollapsible = false;
        params.headerValue = TEXT_VALUE;
        final var expected = getHtmlTree(params);
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    public void shouldHandleRendererAttributeAsserts() {
        new ComponentConfigDecorator(getFacesContext().getApplication(), getFacesContext())
            .registerUIComponent(HtmlSelectBooleanCheckbox.COMPONENT_TYPE, HtmlSelectBooleanCheckbox.class)
            .registerMockRendererForHtmlSelectBooleanCheckbox();
        new ComponentConfigDecorator(getFacesContext().getApplication(), getFacesContext())
            .registerUIComponent(HtmlSelectOneRadio.COMPONENT_TYPE, HtmlSelectOneRadio.class)
            .registerMockRendererForHtmlSelectOneRadio();
        super.shouldHandleRendererAttributeAsserts();
    }

    @Override
    protected UIComponent getComponent() {
        return new BootstrapPanelComponent();
    }

    static class PanelParams {

        public boolean deferredLoading;

        public boolean asyncUpdate;

        public boolean renderSpinner;

        public boolean renderHeader;

        public boolean renderHeaderFacetOnly;

        public boolean renderFooter;

        public boolean isCollapsed;

        public boolean isCollapsible = true;

        public String headerValue;

        public String footerValue;

        public String childContent;

        public Node headerTag = Node.H4;
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(CuiMessageComponent.class).registerRenderer(CuiMessageRenderer.class)
            .registerMockRendererForHtmlForm().registerUIComponent(WaitingIndicatorComponent.class)
            .registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.WAITING_INDICATOR_RENDERER);
    }
}
