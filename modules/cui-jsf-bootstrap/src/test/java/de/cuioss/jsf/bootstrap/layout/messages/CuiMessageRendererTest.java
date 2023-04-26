package de.cuioss.jsf.bootstrap.layout.messages;

import static javax.faces.application.FacesMessage.SEVERITY_WARN;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class CuiMessageRendererTest extends AbstractComponentRendererTest<CuiMessageRenderer>
        implements ComponentConfigurator {

    /**
     * Should render placeholder if no violation available
     */
    @Test
    void shouldRenderWarning() {
        UIComponent parent = new HtmlPanelGrid();
        var component = new CuiMessageComponent();
        final var htmlInputText = new HtmlInputText();
        htmlInputText.setId("input");
        parent.getChildren().add(htmlInputText);
        final var summary = "warning message";
        final var detail = "detail msg";
        prepareViolationMessage(summary, detail);
        component.setParent(parent);
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.CUI_MESSAGE)
                .withAttribute(AttributeName.ARIA_LIVE, "polite").withNode(Node.SPAN).withStyleClass("cui_msg_warn")
                .withAttribute(AttributeName.TITLE, detail).withTextContent(summary);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderDetail() {
        UIComponent parent = new HtmlPanelGrid();
        var component = new CuiMessageComponent();
        component.setShowDetail(true);
        final var htmlInputText = new HtmlInputText();
        htmlInputText.setId("input");
        parent.getChildren().add(htmlInputText);
        final var summary = "warning message";
        final var detail = "detail msg";
        prepareViolationMessage(summary, detail);
        component.setParent(parent);
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.CUI_MESSAGE)
                .withAttribute(AttributeName.ARIA_LIVE, "polite").withNode(Node.SPAN).withStyleClass("cui_msg_warn")
                .withTextContent(summary + ' ' + detail);
        assertRenderResult(component, expected.getDocument());
    }

    private void prepareViolationMessage(final String summary, final String detail) {
        var message = new FacesMessage(SEVERITY_WARN, summary, detail);
        getFacesContext().addMessage("input", message);
    }

    @Test
    void shouldRenderMinimal() {
        var component = new CuiMessageComponent();
        assertEmptyRenderResult(component);
    }

    @Override
    protected UIComponent getComponent() {
        UIComponent parent = new HtmlPanelGrid();
        var component = new CuiMessageComponent();
        final var htmlInputText = new HtmlInputText();
        htmlInputText.setId("input");
        parent.getChildren().add(htmlInputText);
        final var summary = "warning message";
        final var detail = "detail msg";
        prepareViolationMessage(summary, detail);
        component.setParent(parent);
        return component;
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRenderer(UIPanel.COMPONENT_FAMILY, new HtmlPanelGrid().getRendererType());
    }
}
