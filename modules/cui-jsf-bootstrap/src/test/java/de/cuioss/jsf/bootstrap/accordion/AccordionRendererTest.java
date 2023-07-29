package de.cuioss.jsf.bootstrap.accordion;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import javax.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.support.ActiveIndexManagerImpl;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.layout.BootstrapPanelComponent;
import de.cuioss.jsf.bootstrap.layout.BootstrapPanelRenderer;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class AccordionRendererTest extends AbstractComponentRendererTest<AccordionRenderer> implements ComponentConfigurator {

    private static final String DEFAULT_ID = "j_id__v_0";

    private static final String SOME_ID = "someId";

    private static final String CHILD_ID = "childId";

    private static final String CLIENT_ID_HEADING = "_heading";

    private static final String CLIENT_ID_BODY = "_body";

    private static final String CLIENT_ID_ISEXPANDED = "_isexpanded";

    private static final String CLIENT_ID_TOGGLER = "_toggler";

    private static final String CLIENT_ID_ICON = "_icon";

    private static final String HEADER_TEXT = Generators.strings(5, 100).next();

    @Test
    void shouldRenderMinimal() {
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(CssBootstrap.PANEL_GROUP)
                .withAttribute(AttributeName.ID, DEFAULT_ID).withAttribute(AttributeName.NAME, DEFAULT_ID)
                .withAttribute(AttributeName.ROLE, "tablist")
                .withAttribute(AttributeName.ARIA_MULTISELECTABLE, AttributeValue.FALSE);
        assertRenderResult(new AccordionComponent(), expected.getDocument());
    }

    @Test
    void shouldRenderWithChildren() {
        final var component = new AccordionComponent();
        component.setId(SOME_ID);
        final var child = new BootstrapPanelComponent();
        child.setId(CHILD_ID);
        child.setHeaderValue(HEADER_TEXT);
        component.getChildren().add(child);
        assertRenderResult(component, getAccordionWithOnePanel(true).getDocument());
    }

    @Test
    void shouldRenderWithActiveIndexedChildren() {
        final var component = new AccordionComponent();
        component.setId(SOME_ID);
        component.setActiveIndexManager(new ActiveIndexManagerImpl(immutableList()));
        final var child = new BootstrapPanelComponent();
        child.setId(CHILD_ID);
        child.setCollapsible(true);
        child.setHeaderValue(HEADER_TEXT);
        // opposite to active index manager
        child.setCollapsed(false);
        component.getChildren().add(child);
        assertRenderResult(component, getAccordionWithOnePanel(false).getDocument());
        component.setActiveIndexManager(new ActiveIndexManagerImpl(immutableList(0)));
        assertRenderResult(component, getAccordionWithOnePanel(true).getDocument());
    }

    private static HtmlTreeBuilder getAccordionWithOnePanel(final boolean panelExpanded) {
        return new HtmlTreeBuilder().withNode(Node.DIV).withAttribute(AttributeName.ID, SOME_ID)
                .withAttribute(AttributeName.NAME, SOME_ID).withStyleClass(CssBootstrap.PANEL_GROUP)
                .withAttribute(AttributeName.ROLE, "tablist")
                .withAttribute(AttributeName.ARIA_MULTISELECTABLE, AttributeValue.FALSE).withNode(Node.DIV)
                .withStyleClass(CssBootstrap.PANEL.getStyleClassBuilder().append("panel-default cui-panel"))
                .withAttribute(AttributeName.ID, CHILD_ID).withAttribute(AttributeName.NAME, CHILD_ID)
                .withAttribute(AttributeName.DATA_NOT_COLLAPSED, String.valueOf(panelExpanded)).withNode(Node.INPUT)
                .withAttribute(AttributeName.ID, CHILD_ID + CLIENT_ID_ISEXPANDED)
                .withAttribute(AttributeName.NAME, CHILD_ID + CLIENT_ID_ISEXPANDED)
                .withAttribute(AttributeName.TYPE, "hidden")
                .withAttribute(AttributeName.VALUE, String.valueOf(panelExpanded)).currentHierarchyUp()
                .withNode(Node.DIV)
                .withStyleClass(CssBootstrap.PANEL_HEADING.getStyleClassBuilder().append("cui-collapsible"))
                .withAttribute(AttributeName.ROLE, "tab").withAttribute(AttributeName.ID, CHILD_ID + CLIENT_ID_TOGGLER)
                .withAttribute(AttributeName.NAME, CHILD_ID + CLIENT_ID_TOGGLER)
                .withAttribute(AttributeName.ARIA_EXPANDED, String.valueOf(panelExpanded))
                .withAttribute(AttributeName.ARIA_CONTROLS, CHILD_ID + CLIENT_ID_BODY)
                .withAttribute(AttributeName.DATA_TARGET, "#" + CHILD_ID + CLIENT_ID_BODY)
                .withAttribute(AttributeName.DATA_PARENT, "#" + SOME_ID)
                .withAttribute(AttributeName.DATA_TOGGLE, "collapse").withNode(Node.H4)
                .withAttribute(AttributeName.ID, CHILD_ID + CLIENT_ID_HEADING)
                .withAttribute(AttributeName.NAME, CHILD_ID + CLIENT_ID_HEADING)
                .withStyleClass(CssBootstrap.PANEL_TITLE).withTextContent(HEADER_TEXT).withNode(Node.SPAN)
                .withAttribute(AttributeName.ID, CHILD_ID + CLIENT_ID_ICON)
                .withAttribute(AttributeName.NAME, CHILD_ID + CLIENT_ID_ICON)
                .withStyleClass("cui-icon cui-collapsible-icon").currentHierarchyUp().currentHierarchyUp()
                .currentHierarchyUp().withNode(Node.DIV)
                .withStyleClass(CssBootstrap.PANEL_COLLAPSE.getStyleClassBuilder().append("collapse").append(// "in"
                                                                                                             // =
                                                                                                             // show
                                                                                                             // content
                        panelExpanded ? "in" : ""))
                .withAttribute(AttributeName.ID, CHILD_ID + CLIENT_ID_BODY)
                .withAttribute(AttributeName.NAME, CHILD_ID + CLIENT_ID_BODY)
                .withAttribute(AttributeName.ROLE, "tabpanel")
                .withAttribute(AttributeName.ARIA_LABELLEDBY, CHILD_ID + CLIENT_ID_TOGGLER).withNode(Node.DIV)
                .withStyleClass(CssBootstrap.PANEL_BODY);
    }

    @Override
    protected UIComponent getComponent() {
        return new AccordionComponent();
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerRenderer(BootstrapPanelRenderer.class);
    }
}
