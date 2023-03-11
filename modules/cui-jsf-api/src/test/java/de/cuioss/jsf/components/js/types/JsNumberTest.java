package de.cuioss.jsf.components.js.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.ValueObjectTest;

class JsNumberTest extends ValueObjectTest<JsNumber<Number>> {

    @Override
    protected JsNumber<Number> anyValueObject() {
        return JsNumber.create(Generators.integers().next());
    }

    @Test
    void shouldSupportIntegerValue() {
        final JsNumber<Integer> target = JsNumber.create(10);
        assertEquals("10", target.getValueAsString());
        final JsNumber<Integer> targetEmpty = JsNumber.create(null);
        assertNull(targetEmpty.getValueAsString());
    }

    @Test
    void shouldSupportDoubleValue() {
        final JsNumber<Double> target = JsNumber.create(10.1);
        assertEquals("10.100", target.getValueAsString());
        final JsNumber<Double> targetEmpty = JsNumber.create(null);
        assertNull(targetEmpty.getValueAsString());
    }

    @Test
    void shouldReactOnUnsupportedNumberType() {
        assertThrows(UnsupportedOperationException.class, () -> JsNumber.create(Byte.MAX_VALUE));
    }
}
