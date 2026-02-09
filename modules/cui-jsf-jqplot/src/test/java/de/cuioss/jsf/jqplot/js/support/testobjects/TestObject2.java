/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
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
import lombok.ToString;

import java.io.Serial;

/**
 * Example implementation
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class TestObject2 extends JsObject {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = -2458207725778585937L;

    public TestObject2() {
        super("testObject2");
    }

    private JsString someStringProperty;

    public TestObject2 setSomeStringProperty(final String value) {
        someStringProperty = new JsString(value);
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("someStringProperty", someStringProperty);
        return createAsJSON();
    }

}
