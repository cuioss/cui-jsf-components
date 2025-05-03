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
package de.cuioss.jsf.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import org.junit.jupiter.api.Test;

class HtmlSanitizingConverterTest extends AbstractConverterTest<HtmlSanitizingConverter, String> {

    public static final String PLAIN_TEXT = "Plain_Text";
    public static final String SIMPLE_HTML = "<div><p>" + PLAIN_TEXT + "</p></div>";

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addRoundtripValues(PLAIN_TEXT).addValidObjectWithStringResult(SIMPLE_HTML, PLAIN_TEXT);
    }

    @Test
    void shouldBeConfigurable() {
        var converter = getConverter();
        converter.setStrategy(CuiSanitizer.PASSTHROUGH.name());
        assertEquals(SIMPLE_HTML, converter.getAsString(getFacesContext(), getComponent(), SIMPLE_HTML));
    }

    @Test
    void shouldIgnoreEmptyConfig() {
        var converter = getConverter();
        converter.setStrategy("");
        assertEquals(PLAIN_TEXT, converter.getAsString(getFacesContext(), getComponent(), SIMPLE_HTML));
    }

    @Test
    void shouldHandleFullSanitation() {
        var converter = getConverter();
        assertEquals("", converter.getAsString(getFacesContext(), getComponent(), "<p></p>"));
    }

}
