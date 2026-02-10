/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import org.jboss.weld.junit5.ExplicitParamInjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"offsetSize", "size"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableTestLogger
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@ExplicitParamInjection
@DisplayName("Tests for ColumnComponent")
class ColumnComponentTest extends AbstractUiComponentTest<ColumnComponent> {

    private static final String COL_SM_OFFSET_PREFIX = " " + ColumnCssResolver.COL_OFFSET_PREFIX;

    private static final String COL_SM_PREFIX = ColumnCssResolver.COL_PREFIX;

    private final TypedGenerator<Integer> validNumbers = Generators.integers(1, 12);

    @BeforeEach
    void configureComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
        decorator.registerUIComponent(ColumnComponent.class)
                .registerMockRenderer(BootstrapFamily.COMPONENT_FAMILY, BootstrapFamily.LAYOUT_RENDERER);
    }

    @Nested
    @DisplayName("Tests for style class resolution")
    class StyleClassResolutionTests {

        @Test
        @DisplayName("Should resolve size and offset size correctly")
        void shouldResolveSizeAndOffsetSize() {
            // Arrange
            final var underTest = new ColumnComponent();
            final var size = validNumbers.next();
            final var offsetSize = validNumbers.next();

            // Act
            underTest.setSize(size);
            underTest.setOffsetSize(offsetSize);

            // Assert
            assertEquals(
                    COL_SM_PREFIX + size + COL_SM_OFFSET_PREFIX + offsetSize,
                    underTest.resolveStyleClass().getStyleClass(),
                    "Style class should contain correct size and offset"
            );
        }

        @Test
        @DisplayName("Should resolve size only when offset size is not set")
        void shouldResolveSizeOnly() {
            // Arrange
            final var underTest = new ColumnComponent();
            final var size = validNumbers.next();

            // Act
            underTest.setSize(size);

            // Assert
            assertEquals(
                    COL_SM_PREFIX + size,
                    underTest.resolveStyleClass().getStyleClass(),
                    "Style class should contain only size when offset is not set"
            );
        }
    }

    @Nested
    @DisplayName("Tests for property initialization")
    class PropertyInitializationTests {

        @Test
        @DisplayName("Should initialize with default values")
        void shouldInitializeWithDefaultValues() {
            // Arrange
            final var underTest = new ColumnComponent();

            // Act & Assert
            assertNull(underTest.getSize(), "Size should be null by default");
            assertNull(underTest.getOffsetSize(), "Offset size should be null by default");
        }
    }
}
