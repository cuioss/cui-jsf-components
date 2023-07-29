package de.cuioss.jsf.components.selection;

import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;

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
        Assertions.assertNotNull(underTest.getSelectableValues());
        Assertions.assertTrue(underTest.getSelectableValues().isEmpty());
        Assertions.assertNull(underTest.getSelectedValue());
        Assertions.assertFalse(underTest.isSelectionAvailable());
        Assertions.assertFalse(underTest.isValueSelected());
    }

    @Test
    void testEmptyConstructor() {
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet());
        Assertions.assertNotNull(underTest.getSelectableValues());
        Assertions.assertTrue(underTest.getSelectableValues().isEmpty());
        Assertions.assertNull(underTest.getSelectedValue());
        Assertions.assertFalse(underTest.isSelectionAvailable());
        Assertions.assertFalse(underTest.isValueSelected());
    }

    @Test
    void testSingleValueConstructor() {
        var value = new MockValue("id1", "label1");
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet(value));
        Assertions.assertNotNull(underTest.getSelectableValues());
        Assertions.assertEquals(1, underTest.getSelectableValues().size());
        var selectItem = underTest.getSelectableValues().get(0);
        Assertions.assertEquals("label1", selectItem.getLabel());
        Assertions.assertEquals(value, selectItem.getValue());
        Assertions.assertNull(underTest.getSelectedValue());
        Assertions.assertTrue(underTest.isSelectionAvailable());
        Assertions.assertFalse(underTest.isValueSelected());
        Assertions.assertEquals(value, underTest.getAsObject(new MockFacesContext(), new DummyComponent(), "id1"));
        Assertions.assertEquals("id1", underTest.getAsString(new MockFacesContext(), new DummyComponent(), value));
    }

    @Test
    void testInitToFirst() {
        var value = new MockValue("id1", "label1");
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet(value));
        underTest.initToFirstElement();
        Assertions.assertTrue(underTest.isValueSelected());
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
        Assertions.assertTrue(underTest.isValueSelected());
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
        Assertions.assertTrue(underTest.isValueSelected());
        Assertions.assertEquals(new MockValue("id5", "label5"), underTest.getSelectedValue());

    }

    @Test
    void testAdd() {
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet());
        var value = new MockValue("id1", "label1");
        underTest.add(0, value);
        Assertions.assertNotNull(underTest.getSelectableValues());
        Assertions.assertEquals(1, underTest.getSelectableValues().size());
        var selectItem = underTest.getSelectableValues().get(0);
        Assertions.assertEquals("label1", selectItem.getLabel());
        Assertions.assertEquals(value, selectItem.getValue());
        Assertions.assertNull(underTest.getSelectedValue());
        Assertions.assertTrue(underTest.isSelectionAvailable());
        Assertions.assertFalse(underTest.isValueSelected());
        Assertions.assertEquals(value, underTest.getAsObject(new MockFacesContext(), new DummyComponent(), "id1"));
        Assertions.assertEquals("id1", underTest.getAsString(new MockFacesContext(), new DummyComponent(), value));
    }

    @Test
    void testSerialize() {
        var value = new MockValue("id1", "label1");
        var underTest = new MockAbstractSelectMenuModelAndConverter(mutableSet(value));
        underTest.initToFirstElement();
        var result = SerializableContractImpl.serializeAndDeserialize(underTest);
        Assertions.assertEquals(underTest, result);
        Assertions.assertEquals(underTest.toString(), result.toString());
    }
}
