package de.cuioss.jsf.api.components.renderer;

import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringWriter;

import javax.faces.component.UIComponent;

import org.apache.myfaces.test.mock.MockResponseWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class ElementReplacingResponseWriterTest extends JsfEnabledTestEnvironment {

    static final String FILTER = "filter";

    static final String REPLACEMENT = "replacement";

    static final String PASSTHROUGH = "passthrough";

    private UIComponent component;

    private StringWriter output;

    @SuppressWarnings("resource")
    @BeforeEach
    void beforeTest() {
        output = new StringWriter();
        getFacesContext().setResponseWriter(new MockResponseWriter(output));
        component = new DummyComponent();
    }

    @Test
    void shouldFilterElement() throws IOException {
        var writer = new ElementReplacingResponseWriter(getFacesContext().getResponseWriter(), FILTER, REPLACEMENT,
                false);
        assertTrue(isEmpty(output.toString()));
        writer.startElement(FILTER, component);
        writer.endElement(FILTER);
        assertTrue(output.toString().contains(REPLACEMENT));
        assertFalse(output.toString().contains(FILTER));
        assertFalse(output.toString().contains(PASSTHROUGH));
    }

    @Test
    void shouldPassthroughElement() throws IOException {
        var writer = new ElementReplacingResponseWriter(getFacesContext().getResponseWriter(), FILTER, REPLACEMENT,
                false);
        assertTrue(isEmpty(output.toString()));
        writer.startElement(PASSTHROUGH, component);
        writer.endElement(PASSTHROUGH);
        assertFalse(output.toString().contains(FILTER));
        assertTrue(output.toString().contains(PASSTHROUGH));
    }

    @Test
    void shouldFilterCloseElement() throws IOException {
        var writer = new ElementReplacingResponseWriter(getFacesContext().getResponseWriter(), FILTER, REPLACEMENT,
                true);
        writer.startElement(FILTER, component);
        writer.endElement(FILTER);
        assertEquals("<" + REPLACEMENT, output.toString());
    }
}
