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
import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.myfaces.test.mock.MockFacesContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@DisplayName("Tests for AbstractSelectMenuModelAndConverter")
class AbstractSelectMenuModelAndConverterTest {

    @Data
    @AllArgsConstructor
    public static class MockValue implements Serializable, Comparable<MockValue> {

        @Serial
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

        @Serial
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

    @Nested
    @DisplayName("Constructor tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should handle null source data")
        void nullConstructor() {
            // Arrange & Act
            var underTest = new MockAbstractSelectMenuModelAndConverter(null);

            // Assert
            assertNotNull(underTest.getSelectableValues());
            assertTrue(underTest.getSelectableValues().isEmpty());
            assertNull(underTest.getSelectedValue());
            assertFalse(underTest.isSelectionAvailable());
            assertFalse(underTest.isValueSelected());
        }

        @Test
        @DisplayName("Should handle empty source data")
        void emptyConstructor() {
            // Arrange & Act
            var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet());

            // Assert
            assertNotNull(underTest.getSelectableValues());
            assertTrue(underTest.getSelectableValues().isEmpty());
            assertNull(underTest.getSelectedValue());
            assertFalse(underTest.isSelectionAvailable());
            assertFalse(underTest.isValueSelected());
        }

        @Test
        @DisplayName("Should properly initialize with a single value")
        void singleValueConstructor() {
            // Arrange
            var value = new MockValue("id1", "label1");

            // Act
            var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet(value));

            // Assert
            assertNotNull(underTest.getSelectableValues());
            assertEquals(1, underTest.getSelectableValues().size());
            var selectItem = underTest.getSelectableValues().get(0);
            assertEquals("label1", selectItem.getLabel());
            assertEquals(value, selectItem.getValue());
            assertNull(underTest.getSelectedValue());
            assertTrue(underTest.isSelectionAvailable());
            assertFalse(underTest.isValueSelected());
            assertEquals(value, underTest.getAsObject(new MockFacesContext(), new DummyComponent(), "id1"));
            assertEquals("id1", underTest.getAsString(new MockFacesContext(), new DummyComponent(), value));
        }
    }

    @Nested
    @DisplayName("Initialization tests")
    class InitializationTests {

        @Test
        @DisplayName("Should initialize to first element")
        void initToFirst() {
            // Arrange
            var value = new MockValue("id1", "label1");
            var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet(value));

            // Act
            underTest.initToFirstElement();

            // Assert
            assertTrue(underTest.isValueSelected());
            assertEquals(value, underTest.getSelectedValue());
        }
    }

    @Nested
    @DisplayName("Ordering tests")
    class OrderingTests {

        @Test
        @DisplayName("Should sort values alphabetically when using unsorted set")
        void selectableValuesShouldBeInAlphabeticalOrder_whenUnsortedSet() {
            // Arrange
            SortedSet<MockValue> sortedSet = new TreeSet<>(Comparator.comparing(MockValue::getLabel).reversed());
            sortedSet.add(new MockValue("id5", "label5"));
            sortedSet.add(new MockValue("id4", "label4"));
            sortedSet.add(new MockValue("id3", "label3"));
            sortedSet.add(new MockValue("id2", "label2"));
            sortedSet.add(new MockValue("id1", "label1"));

            Set<MockValue> set = new HashSet<>(sortedSet);

            var underTest = new MockAbstractSelectMenuModelAndConverter(set);

            // Act
            underTest.initToFirstElement();

            // Assert
            assertTrue(underTest.isValueSelected());
            assertEquals(new MockValue("id1", "label1"), underTest.getSelectedValue());
        }

        @Test
        @DisplayName("Should maintain original order when using sorted set")
        void selectableValuesShouldStayInOriginalOrder_whenSortedSet() {
            // Arrange
            SortedSet<MockValue> sortedSet = new TreeSet<>(Comparator.comparing(MockValue::getLabel).reversed());
            sortedSet.add(new MockValue("id5", "label5"));
            sortedSet.add(new MockValue("id4", "label4"));
            sortedSet.add(new MockValue("id3", "label3"));
            sortedSet.add(new MockValue("id2", "label2"));
            sortedSet.add(new MockValue("id1", "label1"));

            var underTest = new MockAbstractSelectMenuModelAndConverter(sortedSet);

            // Act
            underTest.initToFirstElement();

            // Assert
            assertTrue(underTest.isValueSelected());
            assertEquals(new MockValue("id5", "label5"), underTest.getSelectedValue());
        }
    }

    @Nested
    @DisplayName("Modification tests")
    class ModificationTests {

        @Test
        @DisplayName("Should add value to the model")
        void add() {
            // Arrange
            var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet());
            var value = new MockValue("id1", "label1");

            // Act
            underTest.add(0, value);

            // Assert
            assertNotNull(underTest.getSelectableValues());
            assertEquals(1, underTest.getSelectableValues().size());
            var selectItem = underTest.getSelectableValues().get(0);
            assertEquals("label1", selectItem.getLabel());
            assertEquals(value, selectItem.getValue());
            assertNull(underTest.getSelectedValue());
            assertTrue(underTest.isSelectionAvailable());
            assertFalse(underTest.isValueSelected());
            assertEquals(value, underTest.getAsObject(new MockFacesContext(), new DummyComponent(), "id1"));
            assertEquals("id1", underTest.getAsString(new MockFacesContext(), new DummyComponent(), value));
        }
    }

    @Nested
    @DisplayName("Serialization tests")
    class SerializationTests {

        @Test
        @DisplayName("Should be serializable and deserializable")
        void serialize() {
            // Arrange
            var value = new MockValue("id1", "label1");
            var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet(value));
            underTest.initToFirstElement();

            // Act
            var result = SerializableContractImpl.serializeAndDeserialize(underTest);

            // Assert
            assertEquals(underTest, result);
            assertEquals(underTest.toString(), result.toString());
        }
    }
}
