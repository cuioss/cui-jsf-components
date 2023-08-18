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

import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.myfaces.test.mock.MockFacesContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import lombok.AllArgsConstructor;
import lombok.Data;

class AbstractSelectMenuModelAndConverterTest {

    @Data
    @AllArgsConstructor
    public static class MockValue implements Serializable, Comparable<MockValue> {

        private static final long serialVersionUID = -5208555634855366343L;

        private String identifier;

        private String label;

        @Override
        public int compareTo(MockValue o) {

            if (null != getLabel() && null != o.getLabel() && !getLabel().equals(o.getLabel())) {
                return getLabel().compareTo(o.getLabel());
            }
            return getIdentifier().compareTo(o.getIdentifier());
        }
    }

    public static class MockAbstractSelectMenuModelAndConverter extends AbstractSelectMenuModelAndConverter<MockValue> {

        private static final long serialVersionUID = 4088103713423702562L;

        public MockAbstractSelectMenuModelAndConverter(final Set<MockValue> sourceData) {
            super(sourceData);
        }

        @Override
        protected String getLabel(final MockValue value) {
            return value.getLabel();
        }

        @Override
        protected String getIdentifier(final MockValue value) {
            return value.getIdentifier();
        }
    }

    @Test
    void testNullConstructor() {
        var underTest = new MockAbstractSelectMenuModelAndConverter(null);
        assertNotNull(underTest.getSelectableValues());
        assertTrue(underTest.getSelectableValues().isEmpty());
        assertNull(underTest.getSelectedValue());
        assertFalse(underTest.isSelectionAvailable());
        assertFalse(underTest.isValueSelected());
    }

    @Test
    void testEmptyConstructor() {
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet());
        assertNotNull(underTest.getSelectableValues());
        assertTrue(underTest.getSelectableValues().isEmpty());
        assertNull(underTest.getSelectedValue());
        assertFalse(underTest.isSelectionAvailable());
        assertFalse(underTest.isValueSelected());
    }

    @Test
    void testSingleValueConstructor() {
        var value = new MockValue("id1", "label1");
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet(value));
        assertNotNull(underTest.getSelectableValues());
        assertEquals(1, underTest.getSelectableValues().size());
        var selectItem = underTest.getSelectableValues().get(0);
        assertEquals("label1", selectItem.getLabel());
        Assertions.assertEquals(value, selectItem.getValue());
        assertNull(underTest.getSelectedValue());
        assertTrue(underTest.isSelectionAvailable());
        assertFalse(underTest.isValueSelected());
        Assertions.assertEquals(value, underTest.getAsObject(new MockFacesContext(), new DummyComponent(), "id1"));
        Assertions.assertEquals("id1", underTest.getAsString(new MockFacesContext(), new DummyComponent(), value));
    }

    @Test
    void testInitToFirst() {
        var value = new MockValue("id1", "label1");
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet(value));
        underTest.initToFirstElement();
        assertTrue(underTest.isValueSelected());
        Assertions.assertEquals(value, underTest.getSelectedValue());
    }

    @Test
    void selectableValuesShouldBeInAlphabeticalOrder_whenUnsortedSet() {
        SortedSet<MockValue> sortedSet = new TreeSet<>(Comparator.comparing(MockValue::getLabel).reversed());
        sortedSet.add(new MockValue("id5", "label5"));
        sortedSet.add(new MockValue("id4", "label4"));
        sortedSet.add(new MockValue("id3", "label3"));
        sortedSet.add(new MockValue("id2", "label2"));
        sortedSet.add(new MockValue("id1", "label1"));

        Set<MockValue> set = new HashSet<>(sortedSet);

        var underTest = new MockAbstractSelectMenuModelAndConverter(set);
        underTest.initToFirstElement();
        assertTrue(underTest.isValueSelected());
        Assertions.assertEquals(new MockValue("id1", "label1"), underTest.getSelectedValue());

    }

    @Test
    void selectableValuesShouldStayInOriginalOrder_whenSortedSet() {
        SortedSet<MockValue> sortedSet = new TreeSet<>(Comparator.comparing(MockValue::getLabel).reversed());
        sortedSet.add(new MockValue("id5", "label5"));
        sortedSet.add(new MockValue("id4", "label4"));
        sortedSet.add(new MockValue("id3", "label3"));
        sortedSet.add(new MockValue("id2", "label2"));
        sortedSet.add(new MockValue("id1", "label1"));

        var underTest = new MockAbstractSelectMenuModelAndConverter(sortedSet);
        underTest.initToFirstElement();
        assertTrue(underTest.isValueSelected());
        Assertions.assertEquals(new MockValue("id5", "label5"), underTest.getSelectedValue());

    }

    @Test
    void testAdd() {
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet());
        var value = new MockValue("id1", "label1");
        underTest.add(0, value);
        assertNotNull(underTest.getSelectableValues());
        assertEquals(1, underTest.getSelectableValues().size());
        var selectItem = underTest.getSelectableValues().get(0);
        assertEquals("label1", selectItem.getLabel());
        Assertions.assertEquals(value, selectItem.getValue());
        assertNull(underTest.getSelectedValue());
        assertTrue(underTest.isSelectionAvailable());
        assertFalse(underTest.isValueSelected());
        Assertions.assertEquals(value, underTest.getAsObject(new MockFacesContext(), new DummyComponent(), "id1"));
        Assertions.assertEquals("id1", underTest.getAsString(new MockFacesContext(), new DummyComponent(), value));
    }

    @Test
    void testSerialize() {
        var value = new MockValue("id1", "label1");
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet(value));
        underTest.initToFirstElement();
        var result = SerializableContractImpl.serializeAndDeserialize(underTest);
        assertEquals(underTest, result);
        assertEquals(underTest.toString(), result.toString());
    }
}
