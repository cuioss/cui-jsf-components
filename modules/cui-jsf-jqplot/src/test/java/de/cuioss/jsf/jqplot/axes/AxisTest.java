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
package de.cuioss.jsf.jqplot.axes;

import static org.junit.jupiter.api.Assertions.*;

import de.cuioss.jsf.jqplot.renderer.DateAxisRenderer;
import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import org.junit.jupiter.api.Test;

class AxisTest {

    @Test
    void shouldBeSerializable() {
        assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(Axis.createXAxis()));
    }

    @Test
    void shouldNotReturnObjectOnEmptyProperties() {
        final var target = Axis.createYAxis();
        assertNull(target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldAcceptRenderer() {
        final var target = Axis.createXAxis2();
        target.setRenderer(new DateAxisRenderer());
        assertEquals("x2axis: {renderer:$.jqplot.DateAxisRenderer}", target.asJavaScriptObjectNotation());
    }

    @Test
    void shouldProvideLabel() {
        final var target = Axis.createYAxis2();
        target.setLabel("some label");
        assertEquals("y2axis: {label:\"some label\",showLabel:true}", target.asJavaScriptObjectNotation());
    }
}
