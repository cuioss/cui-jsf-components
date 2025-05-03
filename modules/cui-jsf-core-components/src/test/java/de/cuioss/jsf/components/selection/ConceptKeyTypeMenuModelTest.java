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
package de.cuioss.jsf.components.selection;

import static de.cuioss.jsf.components.selection.ConceptKeyTypeGenerator.*;
import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.test.valueobjects.property.util.CollectionType;
import de.cuioss.tools.property.PropertyReadWrite;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.event.ValueChangeEvent;
import org.apache.myfaces.test.mock.MockFacesContext;
import org.junit.jupiter.api.Test;

import java.util.Locale;

@PropertyReflectionConfig(skip = true)
@PropertyConfig(name = "sourceData", propertyClass = ConceptKeyType.class, generator = ConceptKeyTypeGenerator.class, collectionType = CollectionType.SET, propertyReadWrite = PropertyReadWrite.WRITE_ONLY)
@PropertyConfig(name = "locale", propertyClass = Locale.class, propertyReadWrite = PropertyReadWrite.WRITE_ONLY)
@VerifyConstructor(of = {"sourceData", "locale"}, required = "locale")
class ConceptKeyTypeMenuModelTest extends ValueObjectTest<ConceptKeyTypeMenuModel> {

    private static ConceptKeyTypeMenuModel testModel() {
        return new ConceptKeyTypeMenuModel(mutableSet(TEST_CODE, TEST_CODE2), Locale.ENGLISH);
    }

    @Test
    void setSelected() {
        var sut = testModel();
        assertEquals(2, sut.getSelectableValues().size());
        sut.setSelectedValue(TEST_CODE2);
        assertEquals(2, sut.getSelectableValues().size());
        assertEquals(TEST_CODE2, sut.getSelectedValue());
    }

    @Test
    void shouldProcessValueChangeAndSetSelectedValue() {
        final var sut = testModel();
        final var selected = TEST_CODE;
        sut.setSelectedValue(selected);
        assertEquals(selected, sut.getSelectedValue());
        sut.processValueChange(new ValueChangeEvent(new HtmlInputText(), null, null));
        // Assertions.assertEquals(selected, sut.getSelectedValue());
        final var selected2 = TEST_CODE2;
        sut.processValueChange(new ValueChangeEvent(new HtmlInputText(), null, selected2));
        assertEquals(selected2, sut.getSelectedValue());
        final var undefined = new BaseConceptCategory().createUndefinedConceptKey("undefined");
        assertEquals(2, sut.getSelectableValues().size());
        sut.setSelectedValue(undefined);
        assertEquals(undefined, sut.getSelectedValue());
        assertEquals(2 + 1, sut.getSelectableValues().size());
    }

    @Test
    void shouldSetDefault() {
        final var model = new ConceptKeyTypeMenuModel(mutableSet(TEST_CODE, TEST_CODE2, TEST_DEFAULT_CODE),
                Locale.ENGLISH);
        model.initToDefault();
        assertEquals(TEST_DEFAULT_CODE, model.getSelectedValue());
    }

    @Test
    void shouldHandleUndefined() {
        final var sut = testModel();
        var selected = new BaseConceptCategory().createUndefinedConceptKey("bla");
        assertEquals(2, sut.getSelectableValues().size());
        sut.setSelectedValue(selected);
        assertEquals(3, sut.getSelectableValues().size());
        assertEquals(selected, sut.getSelectableValues().get(0).getValue());
        assertTrue(sut.getSelectableValues().get(0).isDisabled());
        assertEquals("bla", sut.getAsString(new MockFacesContext(), new DummyComponent(), selected));
    }

    @Test
    void shouldConvertAnyObject() {
        final var sut = testModel();
        assertEquals("dummy",
                sut.getAsString(new MockFacesContext(), new DummyComponent(),
                        ConceptKeyTypeImpl.builder().identifier("dummy")
                                .labelResolver(new I18nDisplayNameProvider("dummyValue"))
                                .category(new BaseConceptCategory()).build()));

    }

}
