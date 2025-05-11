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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for Axis class")
class AxisTest {

    @Nested
    @DisplayName("Serialization tests")
    class SerializationTests {

        @Test
        @DisplayName("Should be serializable")
        void shouldBeSerializable() {
            // Arrange & Act & Assert
            assertDoesNotThrow(() -> SerializableContractImpl.serializeAndDeserialize(Axis.createXAxis()));
        }
    }

    @Nested
    @DisplayName("Property tests")
    class PropertyTests {

        @Test
        @DisplayName("Should not return object on empty properties")
        void shouldNotReturnObjectOnEmptyProperties() {
            // Arrange
            final var target = Axis.createYAxis();

            // Act & Assert
            assertNull(target.asJavaScriptObjectNotation());
        }
    }

    @Nested
    @DisplayName("Renderer tests")
    class RendererTests {

        @Test
        @DisplayName("Should accept renderer")
        void shouldAcceptRenderer() {
            // Arrange
            final var target = Axis.createXAxis2();

            // Act
            target.setRenderer(new DateAxisRenderer());

            // Assert
            assertEquals("x2axis: {renderer:$.jqplot.DateAxisRenderer}", target.asJavaScriptObjectNotation());
        }
    }

    @Nested
    @DisplayName("Label tests")
    class LabelTests {

        @Test
        @DisplayName("Should provide label")
        void shouldProvideLabel() {
            // Arrange
            final var target = Axis.createYAxis2();

            // Act
            target.setLabel("some label");

            // Assert
            assertEquals("y2axis: {label:\"some label\",showLabel:true}", target.asJavaScriptObjectNotation());
        }
    }
}
