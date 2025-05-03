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
package de.cuioss.jsf.components.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import org.junit.jupiter.api.Test;

class TextSplittingConverterTest extends AbstractConverterTest<TextSplittingConverter, String> {

    private static final String LONG_TEXT = "123456789abcdef";

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addValidObjectWithStringResult(LONG_TEXT, LONG_TEXT);
    }

    @Test
    void shouldAbridgeText() {
        var converter = new TextSplittingConverter();
        converter.setAbridgedLengthCount(10);
        var converted = converter.getAsString(getFacesContext(), getComponent(), LONG_TEXT);
        assertEquals("123456 ...", converted);
    }
}
