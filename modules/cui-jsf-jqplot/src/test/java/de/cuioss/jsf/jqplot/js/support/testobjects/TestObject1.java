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
package de.cuioss.jsf.jqplot.js.support.testobjects;

import de.cuioss.jsf.jqplot.js.support.JsObject;
import de.cuioss.jsf.jqplot.js.types.JsString;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * Example implementation
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("javadoc")
public class TestObject1 extends JsObject {

    /** serial Version UID */
    private static final long serialVersionUID = -6791338760451480884L;

    public TestObject1() {
        super("testObject1");
    }

    private JsString someStringProperty;

    public TestObject1 setSomeStringProperty(final String value) {
        someStringProperty = new JsString(value);
        return this;
    }

    @Setter
    private TestObject2 testObject2;

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("someStringProperty", someStringProperty);
        this.addProperty(testObject2);
        return createAsJSON();
    }

}
