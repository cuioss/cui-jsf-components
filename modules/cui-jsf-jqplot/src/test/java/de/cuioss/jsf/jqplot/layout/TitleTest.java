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
package de.cuioss.jsf.jqplot.layout;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import org.junit.jupiter.api.Test;

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
