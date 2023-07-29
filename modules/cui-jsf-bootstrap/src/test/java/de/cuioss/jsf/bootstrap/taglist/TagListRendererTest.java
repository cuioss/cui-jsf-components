package de.cuioss.jsf.bootstrap.taglist;

import static de.cuioss.jsf.bootstrap.taglist.TagTestUtils.insertTag;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.util.List;

import javax.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.support.ConceptKeyTypeGenerator;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class TagListRendererTest extends AbstractComponentRendererTest<TagListRenderer> implements ComponentConfigurator {

    private static final ConceptKeyTypeImpl CODE_TYPE_1 = ConceptKeyTypeImpl.builder().identifier("identifier1")
            .labelResolver(new I18nDisplayNameProvider("resolved1"))
            .category(ConceptKeyTypeGenerator.TestConceptCategory).build();

    private static final ConceptKeyTypeImpl CODE_TYPE_2 = ConceptKeyTypeImpl.builder().identifier("identifier2")
            .labelResolver(new I18nDisplayNameProvider("resolved2"))
            .category(ConceptKeyTypeGenerator.TestConceptCategory).build();

    private static final List<ConceptKeyType> TYPES = immutableList(CODE_TYPE_1, CODE_TYPE_2);

    @Test
    void shouldNotRenderWithNoValueSet() {
        assertEmptyRenderResult(new TagListComponent());
    }

    @Test
    void shouldRenderMinimal() {
        var component = new TagListComponent();
        component.setValue(CODE_TYPE_1);
        var expected = new HtmlTreeBuilder();
        expected.withNode(Node.UL).withStyleClass(CssBootstrap.LIST_INLINE.getStyleClass());
        insertTag(expected);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderList() {
        var component = new TagListComponent();
        component.setValue(TYPES);
        var expected = new HtmlTreeBuilder();
        expected.withNode(Node.UL).withStyleClass(CssBootstrap.LIST_INLINE.getStyleClass());
        insertTag(expected, 0);
        insertTag(expected, 1);
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        var component = new TagListComponent();
        component.setValue(TYPES);
        return component;
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.TAG_COMPONENT_RENDERER);
    }
}
