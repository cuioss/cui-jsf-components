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

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.faces.convert.DateTimeConverter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

class CuiDateTimeConverterTest extends AbstractConverterTest<CuiDateTimeConverter, Object> {

    @Override
    public void configure(final CuiDateTimeConverter toBeConfigured) {
        toBeConfigured.setDateStyle("long");
        toBeConfigured.setTimeStyle("long");
        toBeConfigured.setType("both");
    }

    @Override
    public void populate(final TestItems<Object> testItems) {
        Locale.setDefault(Locale.ENGLISH);

        var jsfConverter = new DateTimeConverter();
        jsfConverter.setDateStyle("long");
        jsfConverter.setTimeStyle("long");
        jsfConverter.setType("both");

        var roundTrip = jsfConverter.getAsString(getFacesContext(), new HtmlInputText(), new Date());

        testItems.addValidObject(LocalDateTime.now().minusSeconds(10)).addRoundtripValues(roundTrip);
    }
}
