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
package de.cuioss.jsf.bootstrap.taginput;

import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.support.ConceptKeyTypeGenerator;
import de.cuioss.jsf.bootstrap.support.ConceptKeyTypeSetGenerator;
import de.cuioss.jsf.bootstrap.tag.support.TagHelper;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.test.valueobjects.property.util.CollectionType;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

@VerifyComponentProperties(of = { "maxItems", "letUserCreateTags", "delimiter", "itemConverterId",
        "displayRemoveButton" }, defaultValued = { "maxItems", "displayRemoveButton" })
@PropertyConfig(name = "sourceSet", propertyClass = ConceptKeyType.class, collectionType = CollectionType.SET)
@PropertyConfig(name = "clientCreated", propertyClass = ConceptKeyType.class, collectionType = CollectionType.SET)
@PropertyGenerator(ConceptKeyTypeGenerator.class)
class TagInputComponentTest extends AbstractUiComponentTest<TagInputComponent> {

    private final ConceptKeyTypeSetGenerator conceptKeyTypeSetGenerator = new ConceptKeyTypeSetGenerator();

    private final ConceptKeyTypeGenerator conceptKeyTypeGenerator = new ConceptKeyTypeGenerator();

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.TAG_INPUT_COMPONENT_RENDERER, anyComponent().getRendererType());
        assertNotNull(anyComponent().getConverter());
        assertTrue(anyComponent().getConverter() instanceof ConceptKeyStringConverter);
    }

    @Test
    void shouldDefaultTo10MaxItems() {
        assertEquals(10, anyComponent().getMaxItems());
    }

    @Test
    void shouldSetValueSets() {
        final var underTest = anyComponent();
        assertTrue(TagHelper.getValueAsSet(underTest.getSubmittedValue(), underTest.getValue()).isEmpty());
        underTest.setValue(Collections.singleton(conceptKeyTypeGenerator.next()));
        assertEquals(1, TagHelper.getValueAsSet(underTest.getSubmittedValue(), underTest.getValue()).size());
        final Set<ConceptKeyType> valueSet = mutableSet(conceptKeyTypeSetGenerator.next());
        valueSet.add(ConceptKeyTypeImpl.builder().identifier("same").labelResolver(new I18nDisplayNameProvider("same1"))
                .build());
        valueSet.add(ConceptKeyTypeImpl.builder().identifier("same").labelResolver(new I18nDisplayNameProvider("same2"))
                .build());
        underTest.setValue(valueSet);
        final var result = TagHelper.getValueAsSet(underTest.getSubmittedValue(), underTest.getValue());
        assertEquals(valueSet.size(), result.size());
    }

    @Test
    void shouldNotSetSingleValue() {
        final var underTest = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> underTest.setValue(conceptKeyTypeGenerator.next()));
    }

    @Test
    void shouldNotAllowStringValue() {
        var underTest = anyComponent();
        assertTrue(TagHelper.getValueAsSet(underTest.getSubmittedValue(), underTest.getValue()).isEmpty());
        var name = Generators.nonEmptyStrings().next();
        assertThrows(IllegalArgumentException.class, () -> underTest.setValue(name));
    }

    @Test
    void shouldFailOnInvalidSingleValue() {
        final var component = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> component.setValue(1));
    }

    @Test
    void shouldFailOnInvalidSetValue() {
        final var component = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> component.setValue(mutableSet(1)));
    }

    @Test
    void shouldNotAllowEmptyStringValue() {
        final var component = anyComponent();
        assertThrows(IllegalArgumentException.class, () -> component.setValue(""));
    }

    @Test
    void shouldResolveItemConverter() {
        getFacesContext().getApplication().addConverter(TestTagItemConverter.ID, TestTagItemConverter.class.getName());
        final var component = anyComponent();
        component.setItemConverterId(TestTagItemConverter.ID);
        assertNotNull(component.getItemConverter());
        assertTrue(component.getItemConverter().isPresent());
    }

    @Test
    void shouldHaveRemoveButtonPerDefault() {
        assertTrue(anyComponent().isDisplayRemoveButton());
    }
}
