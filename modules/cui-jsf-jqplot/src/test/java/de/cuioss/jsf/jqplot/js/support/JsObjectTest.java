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
package de.cuioss.jsf.jqplot.js.support;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.jqplot.js.support.testobjects.TestObject1;
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
