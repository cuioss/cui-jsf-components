package de.cuioss.jsf.api.ui.model;

import static de.cuioss.test.generator.Generators.integers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBeanProperty;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.object.ObjectTestConfig;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;

@VerifyBeanProperty(of = "typedValue")
@VerifyConstructor(of = "typedValue")
@VerifyConstructor(of = { "typedValue", "label" })
@PropertyConfig(name = "typedValue", propertyClass = Integer.class)
@PropertyConfig(name = "label", propertyClass = String.class)
@ObjectTestConfig(equalsAndHashCodeOf = "typedValue")
class TypedSelectItemTest extends ValueObjectTest<IntegerTypedValueItem> {

    @Test
    void shouldSetValue() {
        var item = new IntegerTypedValueItem();
        var integer = integers().next();
        assertNotEquals(integer, item.getTypedValue());
        item.setValue(integer);
        assertEquals(integer, item.getTypedValue());
    }
}
