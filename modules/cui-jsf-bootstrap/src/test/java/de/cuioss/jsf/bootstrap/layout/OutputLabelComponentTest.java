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
package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"labelKey", "labelValue", "labelEscape", "titleKey", "titleValue", "for"})
@DisplayName("Tests for OutputLabelComponent")
class OutputLabelComponentTest extends AbstractUiComponentTest<OutputLabelComponent> {

    @Nested
    @DisplayName("Tests for component metadata")
    class MetadataTests {

        @Test
        @DisplayName("Should provide correct component family")
        void shouldProvideCorrectMetadata() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals("jakarta.faces.Output", component.getFamily(),
                    "Component family should be 'jakarta.faces.Output'");
        }
    }
}
