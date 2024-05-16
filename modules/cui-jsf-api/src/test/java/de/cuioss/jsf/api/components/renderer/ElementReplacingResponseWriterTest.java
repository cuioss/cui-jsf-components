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
package de.cuioss.jsf.api.components.renderer;

import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringWriter;

import jakarta.faces.component.UIComponent;

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
