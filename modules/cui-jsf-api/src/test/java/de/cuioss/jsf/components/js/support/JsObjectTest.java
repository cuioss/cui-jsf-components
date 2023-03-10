package de.cuioss.jsf.components.js.support;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.components.js.support.testobjects.TestObject1;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.valueobjects.ValueObjectTest;

class JsObjectTest extends ValueObjectTest<JsObject> {

    @Test
    void shouldProduceEmptyObject() {
        final JsObject target = new TestObject1();
        assertNull(target.asJavaScriptObjectNotation());
    }

    @Override
    protected JsObject anyValueObject() {
        final var target = new TestObject1();
        target.setSomeStringProperty(Generators.nonEmptyStrings().next());
        return target;
    }
}
