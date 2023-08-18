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

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class StringToListConverterTest extends AbstractConverterTest<StringToListConverter, String> {

    private static final List<String> MINIMAL_LIST = immutableList("a", "b");

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addRoundtripValues("a;b;c", "a");
    }

    @Test
    void shouldHandleSeparator() {
        var converter = new StringToListConverter();
        // Default
        assertEquals(StringToListConverter.SEPARATOR_DEFAULT, converter.getSeparator());
        converter.setSeparator("-");
        assertEquals("a-b", converter.getAsString(getFacesContext(), getComponent(), MINIMAL_LIST));
        converter.setSeparator(" ");
        assertEquals("a b", converter.getAsString(getFacesContext(), getComponent(), MINIMAL_LIST));
        assertEquals(" a   b ", converter.getAsString(getFacesContext(), getComponent(), immutableList(" a ", " b ")));
        assertEquals(MINIMAL_LIST, converter.getAsObject(getFacesContext(), getComponent(), "a b"));
        assertEquals(immutableList("", "a", "b"), converter.getAsObject(getFacesContext(), getComponent(), " a b "));
        assertEquals(immutableList("", "a", "", "b"),
                converter.getAsObject(getFacesContext(), getComponent(), " a  b "));
    }

    @Test
    void shouldHandleTargetType() {
        var converter = new StringToListConverter();
        // Default
        assertEquals(StringToListConverter.TARGET_TYPE_LIST, converter.getTargetType());
        assertTrue(converter.getAsObject(getFacesContext(), getComponent(), "a;b") instanceof List);
        converter.setTargetType(StringToListConverter.TARGET_TYPE_SORTED_SET);
        assertTrue(converter.getAsObject(getFacesContext(), getComponent(), "a;b") instanceof SortedSet);
    }

    @Test
    void shouldOmitEmptyStrings() {
        var converter = new StringToListConverter();
        converter.setOmitEmptyStrings(true);
        assertEquals(MINIMAL_LIST, converter.getAsObject(getFacesContext(), getComponent(), ";;a;b;;"));
        converter.setSeparator(" ");
        assertEquals(MINIMAL_LIST, converter.getAsObject(getFacesContext(), getComponent(), "a b"));
        assertEquals(MINIMAL_LIST, converter.getAsObject(getFacesContext(), getComponent(), " a b "));
        assertEquals(MINIMAL_LIST, converter.getAsObject(getFacesContext(), getComponent(), " a  b "));
    }

    @Test
    void shouldTrimResults() {
        var converter = new StringToListConverter();
        converter.setTrimResults(true);
        assertEquals(MINIMAL_LIST, converter.getAsObject(getFacesContext(), getComponent(), " a ; b "));
        assertEquals(immutableList("a b", "c d"),
                converter.getAsObject(getFacesContext(), getComponent(), " a b ; c d "));
    }
}
