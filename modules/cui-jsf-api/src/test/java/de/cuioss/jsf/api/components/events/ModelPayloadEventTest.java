package de.cuioss.jsf.api.components.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.faces.component.html.HtmlInputText;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelPayloadEventTest {

    private static final String PAYLOAD = "payload";

    private ModelPayloadEvent event;

    @BeforeEach
    void before() {
        event = new ModelPayloadEvent(new HtmlInputText(), PAYLOAD);
    }

    @Test
    void shouldCreateAndDeliver() {
        assertEquals(PAYLOAD, event.getModel());
    }

    @Test
    void shouldAlwaysReturnFalseOnListner() {
        assertFalse(event.isAppropriateListener(null));
    }

    @Test
    void shouldNotProcessListener() {
        assertThrows(UnsupportedOperationException.class, () -> event.processListener(null));
    }
}
