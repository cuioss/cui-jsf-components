package com.icw.ehf.cui.core.api.components.javascript;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class JavaScriptOptionsTest {

    private static final String KEY1 = "key1";

    private static final String KEY2 = "key2";

    @Test
    void shouldReturnEmptyStringOnEmptyMap() {
        assertTrue(JavaScriptOptions.builder().build().script().isEmpty());
    }

    @Test
    void shouldCreateWithSingleEntry() {
        assertEquals("{key1:'value1'}", JavaScriptOptions.builder().withOption(KEY1, "value1").build().script());
    }

    @Test
    void shouldIgnoreWrappedEntry() {
        assertEquals("{key1:value1}",
                JavaScriptOptions.builder().withOption(KEY1, new NotQuotableWrapper("value1")).build().script());
    }

    @Test
    void shouldCreateWithIdExtensionSingleEntry() {
        assertEquals("{key1:'value1'}", JavaScriptOptions.builder().withOption(KEY1, "value1").build().script());
    }

    @Test
    void shouldSkipBrackets() {
        assertEquals("key1:'value1'",
                JavaScriptOptions.builder().withOption(KEY1, "value1").withWrapInBrackets(false).build().script());
    }

    @Test
    void shouldCreateWithEntryMap() {
        Map<String, Serializable> options = new HashMap<>();
        options.put(KEY1, 1);
        options.put(KEY2, Boolean.TRUE);
        assertEquals("{key1:'1',key2:'true'}", JavaScriptOptions.builder().withOptions(options).build().script());
    }

    @Test
    void shouldAddSingleOption() {
        Map<String, Serializable> options = new HashMap<>();
        JavaScriptOptions.addStringOptions(options, Selectize.OPTION_KEY_SEARCH_FIELD,
                immutableList(Selectize.OPTION_VALUE_LABEL_KEY));
        assertEquals(1, options.size());
        assertEquals("['label']", ((NotQuotableWrapper) options.get(Selectize.OPTION_KEY_SEARCH_FIELD)).getValue());
    }

    @Test
    void shouldNotAddEmptyOption() {
        Map<String, Serializable> options = new HashMap<>();
        JavaScriptOptions.addStringOptions(options, Selectize.OPTION_KEY_SEARCH_FIELD, immutableList());
        assertEquals(0, options.size());
    }

    @Test
    void shouldAddMultipleOptions() {
        Map<String, Serializable> options = new HashMap<>();
        JavaScriptOptions.addStringOptions(options, Selectize.OPTION_KEY_SEARCH_FIELD,
                immutableList(Selectize.OPTION_VALUE_LABEL_KEY, Selectize.OPTION_VALUE_VALUE_KEY));
        assertEquals(1, options.size());
        assertEquals("['label','value']",
                ((NotQuotableWrapper) options.get(Selectize.OPTION_KEY_SEARCH_FIELD)).getValue());
    }
}
