package de.cuioss.jsf.bootstrap.menu;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutcomeTarget;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutcomeTargetLink;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.partial.IconProvider;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import de.cuioss.jsf.bootstrap.icon.IconRenderer;
import de.cuioss.jsf.bootstrap.menu.model.NavigationMenuItemContainer;
import de.cuioss.jsf.bootstrap.menu.model.NavigationMenuItemContainerImpl;
import de.cuioss.jsf.bootstrap.menu.model.NavigationMenuItemExternalSingleImpl;
import de.cuioss.jsf.bootstrap.menu.model.NavigationMenuItemSeparatorImpl;
import de.cuioss.jsf.bootstrap.menu.model.NavigationMenuItemSingleImpl;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class NavigationMenuRendererTest extends AbstractComponentRendererTest<NavigationMenuRenderer>
        implements ComponentConfigurator {

    private static final String CUI_ICON_PREFIX = "cui-icon ";

    private static final String CUI_ICON_ICON = "cui-icon-icon";

    private static final String HTML_OUTPUT_TEXT = "HtmlOutputText";

    private static final String HTML_OUTCOME_TARGET_LINK = "HtmlOutcomeTargetLink";

    private static final String HTML_OUTPUT_LINK = "HtmlOutputLink";

    private static final String RESOLVED_LABEL = "resolvedLabel";

    private static final String ID_EXTENSION = "_0_menu";

    private static final String CLIENT_ID = "j_id__v_0" + ID_EXTENSION;

    private static final String OUTCOME_HOME = "home";

    @Test
    void shouldRenderMinimalWithCommand() {
        final var menuModelItem = new NavigationMenuItemSingleImpl(10);
        final var resovledLabel = RESOLVED_LABEL;
        menuModelItem.setOutcome(OUTCOME_HOME);
        menuModelItem.setLabelValue(resovledLabel);
        final var component = new NavigationMenuComponent();
        component.setModel(menuModelItem);
        final var builder = new HtmlTreeBuilder();
        withCommandElement(builder, CLIENT_ID, OUTCOME_HOME, RESOLVED_LABEL);
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldRenderMinimalWithActiveState() {
        final var menuModelItem = new NavigationMenuItemSingleImpl(10);
        final var resovledLabel = RESOLVED_LABEL;
        menuModelItem.setOutcome(OUTCOME_HOME);
        menuModelItem.setLabelValue(resovledLabel);
        menuModelItem.getActiveForAdditionalViewId().add("/viewId");
        final var component = new NavigationMenuComponent();
        component.setModel(menuModelItem);
        final var builder = new HtmlTreeBuilder();
        builder.withNode(Node.LI).withAttributeNameAndId(CLIENT_ID)
                .withAttribute(AttributeName.DATA_ITEM_ACTIVE, "true").withNode(HTML_OUTCOME_TARGET_LINK)
                .withAttribute("outcome", OUTCOME_HOME).withNode(HTML_OUTPUT_TEXT)
                .withAttribute("class", CssCuiBootstrap.CUI_NAVIGATION_MENU_TEXT.getStyleClass())
                .withTextContent(RESOLVED_LABEL).currentHierarchyUp().currentHierarchyUp().currentHierarchyUp();
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldRenderMinimalWithExternalHref() {
        final var menuModelItem = new NavigationMenuItemExternalSingleImpl(10);
        final var resovledLabel = RESOLVED_LABEL;
        menuModelItem.setHRef("http://www.google.de");
        menuModelItem.setLabelValue(resovledLabel);
        menuModelItem.setTarget("_blank");
        final var component = new NavigationMenuComponent();
        component.setModel(menuModelItem);
        final var builder = new HtmlTreeBuilder();
        builder.withNode(Node.LI).withAttributeNameAndId(CLIENT_ID).withNode(HTML_OUTPUT_LINK)
                .withAttribute("value", "http://www.google.de").withAttribute("target", "_blank")
                .withNode(HTML_OUTPUT_TEXT)
                .withAttribute("class", CssCuiBootstrap.CUI_NAVIGATION_MENU_TEXT.getStyleClass())
                .withTextContent(RESOLVED_LABEL).currentHierarchyUp().currentHierarchyUp().currentHierarchyUp();
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldRenderMinimalWithTarget() {
        final var menuModelItem = new NavigationMenuItemSingleImpl(10);
        final var resovledLabel = RESOLVED_LABEL;
        menuModelItem.setOutcome(OUTCOME_HOME);
        menuModelItem.setLabelValue(resovledLabel);
        menuModelItem.setTarget("_blank");
        final var component = new NavigationMenuComponent();
        component.setModel(menuModelItem);
        final var builder = new HtmlTreeBuilder();
        builder.withNode(Node.LI).withAttributeNameAndId(CLIENT_ID).withNode(HTML_OUTCOME_TARGET_LINK)
                .withAttribute("outcome", OUTCOME_HOME).withAttribute("target", "_blank").withNode(HTML_OUTPUT_TEXT)
                .withAttribute("class", CssCuiBootstrap.CUI_NAVIGATION_MENU_TEXT.getStyleClass())
                .withTextContent(RESOLVED_LABEL).currentHierarchyUp().currentHierarchyUp().currentHierarchyUp();
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldRenderMinimalWithCommandAndIcon() {
        final var menuModelItem = new NavigationMenuItemSingleImpl(10);
        final var resovledLabel = RESOLVED_LABEL;
        menuModelItem.setOutcome(OUTCOME_HOME);
        menuModelItem.setLabelValue(resovledLabel);
        menuModelItem.setIconStyleClass(CUI_ICON_ICON);
        final var component = new NavigationMenuComponent();
        component.setModel(menuModelItem);
        final var builder = new HtmlTreeBuilder();
        builder.withNode(Node.LI).withAttributeNameAndId(CLIENT_ID).withNode(HTML_OUTCOME_TARGET_LINK)
                .withAttribute("outcome", OUTCOME_HOME).withNode(Node.SPAN)
                .withStyleClass(CUI_ICON_PREFIX + CUI_ICON_ICON).currentHierarchyUp().withNode(HTML_OUTPUT_TEXT)
                .withAttribute("class", CssCuiBootstrap.CUI_NAVIGATION_MENU_TEXT.getStyleClass())
                .withTextContent(RESOLVED_LABEL);
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldRenderMinimalWithSeparator() {
        final var component = new NavigationMenuComponent();
        component.setModel(new NavigationMenuItemSeparatorImpl(10));
        final var builder = new HtmlTreeBuilder().withNode(Node.LI).withAttributeNameAndId(CLIENT_ID)
                .withStyleClass(CssBootstrap.LIST_DIVIDER);
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldRenderMinimalWithContainer() {
        final var component = new NavigationMenuComponent();
        component.setId("foo");
        var navigationMenuItemContainer = new NavigationMenuItemContainerImpl(10);
        navigationMenuItemContainer.setId("bar");
        component.setModel(navigationMenuItemContainer);
        final var builder =
            new HtmlTreeBuilder().withNode(Node.LI).withAttributeNameAndId("foo_0_bar").withStyleClass("dropdown");
        withContainerElement(builder);
        assertRenderResult(component, builder.getDocument());
    }

    /**
     * expected icons: south, east, east
     * Structure: C1 > C2 > C3 > Single
     */
    @Test
    void shouldRenderNestedContainers() {
        final var component = new NavigationMenuComponent();
        NavigationMenuItemContainer container1 = new NavigationMenuItemContainerImpl(10);
        NavigationMenuItemContainer container2 = new NavigationMenuItemContainerImpl(10);
        NavigationMenuItemContainer container3 = new NavigationMenuItemContainerImpl(10);
        var single = new NavigationMenuItemSingleImpl(10);
        single.setOutcome(OUTCOME_HOME);
        single.setLabelValue(RESOLVED_LABEL);
        container1.getChildren().add(container2);
        container2.getChildren().add(container3);
        container3.getChildren().add(single);
        component.setModel(container1);
        final var builder = new HtmlTreeBuilder();
        // C1
        builder.withNode(Node.LI).withAttributeNameAndId(CLIENT_ID).withStyleClass("dropdown");
        withContainerElement(builder, "", false);
        builder.withNode(Node.LI).withAttributeNameAndId(CLIENT_ID + ID_EXTENSION)
                .withStyleClass("dropdown dropdown-submenu");
        withContainerElement(builder, "", true);
        builder.withNode(Node.LI).withAttributeNameAndId(CLIENT_ID + ID_EXTENSION + ID_EXTENSION)
                .withStyleClass("dropdown dropdown-submenu");
        withContainerElement(builder, "", true);
        withCommandElement(builder, CLIENT_ID + ID_EXTENSION + ID_EXTENSION + ID_EXTENSION, OUTCOME_HOME,
                RESOLVED_LABEL);
        assertRenderResult(component, builder.getDocument());
    }

    /**
     * Verify renderer. Expected is render list item with anchor element and children.
     */
    @Test
    void shouldRenderComplexMenuItem() {
        final var menuModelItem = new NavigationMenuItemContainerImpl(10);
        final var id = "linkId_0";
        final var topLevelText = "parentlabelValue";
        menuModelItem.setLabelValue(topLevelText);
        final var child1 = new NavigationMenuItemSingleImpl(10);
        child1.setOutcome(OUTCOME_HOME);
        final var child1_labelValue = "child1labelValue";
        child1.setLabelValue(child1_labelValue);
        child1.getOutcomeParameter().put("param1", "value1");
        final var child2 = new NavigationMenuItemSingleImpl(10);
        child2.setDisabled(true);
        final var child2Id = "1_menu";
        child2.setOutcome(OUTCOME_HOME);
        final var child2_labelValue = "child2labelValue";
        child2.setLabelValue(child2_labelValue);
        child2.getOutcomeParameter().put("param1", "value1");
        final var child3 = new NavigationMenuItemSingleImpl(10);
        child3.setOutcome(OUTCOME_HOME);
        child3.setRendered(false);
        final var child3_labelValue = "child3labelValue";
        child3.setLabelValue(child3_labelValue);
        menuModelItem.getChildren().add(child1);
        menuModelItem.getChildren().add(child2);
        menuModelItem.getChildren().add(child3);
        final var component = new NavigationMenuComponent();
        component.setId(id);
        component.setModel(menuModelItem);
        final var builder = new HtmlTreeBuilder().withNode(Node.LI)
                .withAttributeNameAndId(id + ID_EXTENSION).withStyleClass("dropdown");
        withContainerElement(builder, topLevelText);
        withCommandElement(builder, id + ID_EXTENSION + ID_EXTENSION, OUTCOME_HOME, child1_labelValue);
        withCommandElement(builder, id + ID_EXTENSION + "_" + child2Id, OUTCOME_HOME, child2_labelValue);
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldRenderModelItemsWithMultipleElements() {
        final var component = new NavigationMenuComponent();
        final NavigationMenuItemContainer menuModelItem1 = new NavigationMenuItemContainerImpl(10);
        final var menuModelItem2 = new NavigationMenuItemSingleImpl(10);
        menuModelItem2.setOutcome("foo");
        component.setModelItems(immutableList(menuModelItem1, menuModelItem2));
        final var builder =
            new HtmlTreeBuilder().withNode(Node.LI).withAttributeNameAndId(CLIENT_ID).withStyleClass("dropdown");
        withContainerElement(builder);
        // container
        builder.currentHierarchyUp().currentHierarchyUp();
        withCommandElement(builder, "j_id__v_0_1_menu", "foo", "");
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldRenderModelItemsWithOneElement() {
        final var menuModelItem = new NavigationMenuItemContainerImpl(10);
        // menuModelItem.setTitleValue("foo");
        menuModelItem.setIconStyleClass("fooicon");
        final var child1 = new NavigationMenuItemSingleImpl(10);
        child1.setId("model1");
        child1.setOutcome("out1");
        final var child2 = new NavigationMenuItemSingleImpl(10);
        child2.setId("model2");
        child2.setOutcome("out2");
        final var child3 = new NavigationMenuItemSingleImpl(10);
        child3.setOutcome("");
        menuModelItem.getChildren().add(child1);
        menuModelItem.getChildren().add(child2);
        menuModelItem.getChildren().add(child3);
        final var component = new NavigationMenuComponent();
        component.setModelItems(immutableList(menuModelItem));
        final var builder =
            new HtmlTreeBuilder().withNode(Node.LI).withAttributeNameAndId(CLIENT_ID).withStyleClass("dropdown");
        containerElement(builder, "", true, false);
        withCommandElement(builder, CLIENT_ID + "_0_model1", "out1", "");
        withCommandElement(builder, CLIENT_ID + "_1_model2", "out2", "");
        withCommandElement(builder, CLIENT_ID + "_2_menu", "", "");
        assertRenderResult(component, builder.getDocument());
    }

    /**
     * Verify renderer. Expected is render list item with anchor element and children with
     * separator.
     *
     */
    @Test
    void shouldRenderComplexMenuItemWithSeparator() {
        final var menuModelItem = new NavigationMenuItemContainerImpl(10);
        final var id = "linkId";
        final var topLevelText = "parentlabelValue";
        menuModelItem.setLabelValue(topLevelText);
        final var child1 = new NavigationMenuItemSingleImpl(10);
        child1.setOutcome(OUTCOME_HOME);
        final var child1_labelValue = "child1labelValue";
        child1.setLabelValue(child1_labelValue);
        child1.getOutcomeParameter().put("param1", "value1");
        final var separator = new NavigationMenuItemSeparatorImpl(10);
        separator.setId("separatorid");
        final var child2 = new NavigationMenuItemSingleImpl(10);
        final var child2Id = "2_menu";
        child2.setOutcome(OUTCOME_HOME);
        final var child2_labelValue = "child2labelValue";
        child2.setLabelValue(child2_labelValue);
        child2.getOutcomeParameter().put("param1", "value1");
        menuModelItem.getChildren().add(child1);
        menuModelItem.getChildren().add(separator);
        menuModelItem.getChildren().add(child2);
        final var component = new NavigationMenuComponent();
        component.setModel(menuModelItem);
        component.setId(id);
        final var builder = new HtmlTreeBuilder().withNode(Node.LI)
                .withAttributeNameAndId(id + ID_EXTENSION).withStyleClass("dropdown");
        withContainerElement(builder, topLevelText);
        withCommandElement(builder, id + ID_EXTENSION + ID_EXTENSION, OUTCOME_HOME, child1_labelValue);
        separatorElement(builder, id + ID_EXTENSION + "_1_separatorid");
        withCommandElement(builder, id + ID_EXTENSION + "_" + child2Id, OUTCOME_HOME, child2_labelValue);
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldRenderNothing() {
        final var component = new NavigationMenuComponent();
        assertEmptyRenderResult(component);
        component.setModel(new NavigationMenuItemSingleImpl(10));
        component.setRendered(false);
        assertEmptyRenderResult(component);
    }

    @Test
    void shouldRenderSeparator() {
        final var component = (NavigationMenuComponent) getComponent();
        component.setId("compid");
        component.setModel(new NavigationMenuItemSeparatorImpl(10));
        final var builder = new HtmlTreeBuilder().withNode(Node.LI)
                .withAttributeNameAndId("compid" + ID_EXTENSION).withStyleClass("divider").currentHierarchyUp();
        assertRenderResult(component, builder.getDocument());
    }

    @Test
    void shouldNotRenderChildsIfParentIsNotRendered() {
        final var menuModelItem = new NavigationMenuItemContainerImpl(10);
        menuModelItem.setRendered(false);
        menuModelItem.getChildren().add(new NavigationMenuItemSingleImpl(10));
        menuModelItem.getChildren().add(NavigationMenuItemSeparatorImpl.getInstance(10));
        menuModelItem.getChildren().add(new NavigationMenuItemSingleImpl(10));
        final var component = new NavigationMenuComponent();
        component.setModel(menuModelItem);
        component.setId("rendertest");
        assertEmptyRenderResult(component);
    }

    /**
     * Create expected structure for "separator" menu item.<br>
     *
     * @param builder
     * @return expected separator element structure
     */
    private static HtmlTreeBuilder separatorElement(final HtmlTreeBuilder builder, final String id) {
        return builder.withNode(Node.LI).withStyleClass("divider").withAttributeNameAndId(id).currentHierarchyUp();
    }

    private static void withCommandElement(final HtmlTreeBuilder builder, final String id, final String outcome,
            final String text) {
        builder.withNode(Node.LI).withAttributeNameAndId(id).withNode(HTML_OUTCOME_TARGET_LINK)
                .withAttribute("outcome", outcome).withNode(HTML_OUTPUT_TEXT)
                .withAttribute("class", CssCuiBootstrap.CUI_NAVIGATION_MENU_TEXT.getStyleClass()).withTextContent(text)
                .currentHierarchyUp().currentHierarchyUp().currentHierarchyUp();
    }

    private static void withContainerElement(final HtmlTreeBuilder builder) {
        containerElement(builder, "", false, false);
    }

    private static void withContainerElement(final HtmlTreeBuilder builder, final String text) {
        containerElement(builder, text, false, false);
    }

    private static void withContainerElement(final HtmlTreeBuilder builder, final String text,
            final boolean isSubItem) {
        containerElement(builder, text, false, isSubItem);
    }

    /**
     * Create expected link structure for "container" menu item.<br>
     *
     * @param builder
     * @param text
     * @param setIconStyleClass
     * @param isSubItem
     */
    private static void containerElement(final HtmlTreeBuilder builder, final String text,
            final boolean setIconStyleClass, final boolean isSubItem) {
        builder.withNode(HTML_OUTCOME_TARGET_LINK).withStyleClass("dropdown-toggle")
                .withAttribute(AttributeName.DATA_TOGGLE, "dropdown");
        if (setIconStyleClass) {
            builder.withNode(Node.SPAN).withStyleClass(IconProvider.FALLBACK_ICON_STRING).currentHierarchyUp();
        }
        builder.withNode(HTML_OUTPUT_TEXT)
                .withAttribute("class", CssCuiBootstrap.CUI_NAVIGATION_MENU_TEXT.getStyleClass()).withTextContent(text)
                .currentHierarchyUp();
        builder.withNode(Node.SPAN)
                .withStyleClass("cui-icon " + (isSubItem ? "cui-icon-triangle_e" : "cui-icon-triangle_s"))
                .currentHierarchyUp().currentHierarchyUp();
        builder.withNode(Node.UL).withStyleClass("dropdown-menu");
    }

    @Override
    protected UIComponent getComponent() {
        final var component = new NavigationMenuComponent();
        component.setModel(NavigationMenuItemSeparatorImpl.getInstance(10));
        return component;
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(HtmlOutcomeTargetLink.COMPONENT_TYPE, HtmlOutcomeTargetLink.class)
                .registerMockRenderer(UIOutcomeTarget.COMPONENT_FAMILY, "javax.faces.Link");
        decorator.registerUIComponent(HtmlOutputLink.COMPONENT_TYPE, HtmlOutputLink.class)
                .registerMockRenderer(UIOutput.COMPONENT_FAMILY, "javax.faces.Link");
        decorator.registerUIComponent(HtmlOutputText.COMPONENT_TYPE, HtmlOutputText.class)
                .registerMockRendererForHtmlOutputText();
        decorator.registerUIComponent(NavigationMenuComponent.class).registerRenderer(NavigationMenuRenderer.class);
        decorator.registerUIComponent(IconComponent.class).registerRenderer(IconRenderer.class);
    }
}
