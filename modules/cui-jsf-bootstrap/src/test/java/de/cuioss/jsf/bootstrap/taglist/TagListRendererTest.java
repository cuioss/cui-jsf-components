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
package de.cuioss.jsf.bootstrap.taglist;

import static de.cuioss.jsf.bootstrap.taglist.TagTestUtils.insertTag;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.support.ConceptKeyTypeGenerator;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class TagListRendererTest extends AbstractComponentRendererTest<TagListRenderer> {

    private static final ConceptKeyTypeImpl CODE_TYPE_1 = ConceptKeyTypeImpl.builder().identifier("identifier1")
            .labelResolver(new I18nDisplayNameProvider("resolved1"))
            .category(ConceptKeyTypeGenerator.TestConceptCategory).build();

    private static final ConceptKeyTypeImpl CODE_TYPE_2 = ConceptKeyTypeImpl.builder().identifier("identifier2")
            .labelResolver(new I18nDisplayNameProvider("resolved2"))
            .category(ConceptKeyTypeGenerator.TestConceptCategory).build();

    private static final List<ConceptKeyType> TYPES = immutableList(CODE_TYPE_1, CODE_TYPE_2);

    @Test
    void shouldNotRenderWithNoValueSet(FacesContext facesContext) {
        assertEmptyRenderResult(new TagListComponent(), facesContext);
    }

    @Test
    void shouldRenderMinimal(FacesContext facesContext) throws Exception {
        var component = new TagListComponent();
        component.setValue(CODE_TYPE_1);
        var expected = new HtmlTreeBuilder();
        expected.withNode(Node.UL).withStyleClass(CssBootstrap.LIST_INLINE.getStyleClass());
        insertTag(expected);
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Test
    void shouldRenderList(FacesContext facesContext) throws Exception {
        var component = new TagListComponent();
        component.setValue(TYPES);
        var expected = new HtmlTreeBuilder();
        expected.withNode(Node.UL).withStyleClass(CssBootstrap.LIST_INLINE.getStyleClass());
        insertTag(expected, 0);
        insertTag(expected, 1);
        assertRenderResult(component, expected.getDocument(), facesContext);
    }

    @Override
    protected UIComponent getComponent() {
        var component = new TagListComponent();
        component.setValue(TYPES);
        return component;
    }

    @BeforeEach
    void setUp(ComponentConfigDecorator decorator) {
        decorator.registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.TAG_COMPONENT_RENDERER);
    }
}
