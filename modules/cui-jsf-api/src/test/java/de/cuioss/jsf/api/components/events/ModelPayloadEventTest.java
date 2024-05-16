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
package de.cuioss.jsf.api.components.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.faces.component.html.HtmlInputText;

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
