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

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.test.valueobjects.util.IdentityResourceBundle;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.model.SelectItem;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LabeledEnumSelectMenuModelTest extends AbstractConverterTest<LabeledEnumSelectMenuModel<TestEnum>, TestEnum> {

    @Getter
    private LabeledEnumSelectMenuModel<TestEnum> converter;

    @Getter(AccessLevel.PROTECTED)
    private TestItems<TestEnum> testItems;

    @Override
    @BeforeEach
    public void initConverter() {
        converter = new LabeledEnumSelectMenuModel<>(TestEnum.class, new IdentityResourceBundle());
        testItems = new TestItems<>();
        populate(testItems);
    }

    @Test
    void shouldConstructCorrectly() {
        assertNotNull(converter.getSelectableValues());
        assertEquals(3, converter.getSelectableValues().size());
    }

    @Test
    void shouldProvideCorrectTranslations() {
        final var expected = new TreeSet<String>();
        for (final TestEnum testEnum : TestEnum.values()) {
            expected.add(testEnum.getLabelKey());
        }
        final var actual = new TreeSet<String>();
        for (final SelectItem selectItem : converter.getSelectableValues()) {
            actual.add(selectItem.getLabel());
        }
        assertEquals(expected, actual);
    }

    @Test
    void shouldSelectProperly() {
        assertNull(converter.getSelectedValue());
        assertFalse(converter.isValueSelected());
        converter.setSelectedValue(TestEnum.DAY);
        assertTrue(converter.isValueSelected());
        assertEquals(TestEnum.DAY, converter.getSelectedValue());
        converter.processValueChange(new ValueChangeEvent(getComponent(), TestEnum.DAY, TestEnum.MONTH));
        assertEquals(TestEnum.MONTH, converter.getSelectedValue());
    }

    @Override
    public void populate(final TestItems<TestEnum> testItems) {
        testItems.addRoundtripValues(TestEnum.DAY.toString(), TestEnum.MONTH.toString(), TestEnum.YEAR.toString());
    }
}
