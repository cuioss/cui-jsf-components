package com.icw.ehf.cui.components.chart.layout;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.contract.SerializableContractImpl;

class TitleTest {

    @Test
    void shouldBeSerializable() {
        assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(new Title()));
    }

    @Test
    void shouldProvideTitleText() {
        final var target = new Title("any text");
        final var json = target.asJavaScriptObjectNotation();
        assertEquals("title: {text:\"any text\",escapeHtml:true}", json);
        assertEquals(json, target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldNotReturnObjectOnEmptyProperties() {
        final var target = new Title();
        assertNull(target.asJavaScriptObjectNotation());
    }
}
