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
package de.cuioss.jsf.dev.ui.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.RequestConfigDecorator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Tests for SourceCodeComponent")
@VerifyComponentProperties(of = {"source", "sourcePath", "sourceContainerId", "enableClipboard", "type",
        "description"}, defaultValued = {"enableClipboard"})
class SourceCodeComponentTest extends AbstractComponentTest<SourceCodeComponent> {

    private static final String BASE = "/samples/";
    private static final String VIEW_ID = BASE + "source.xhtml";
    private static final String WRAPPER_ID = "panelRef";

    @Nested
    @DisplayName("Direct Source Tests")
    class DirectSourceTests {

        @Test
        @DisplayName("Should return the directly set source")
        void shouldReturnSource() {
            // Arrange
            var component = new SourceCodeComponent();
            var source = Generators.strings(5, 100).next();
            component.setSource(source);

            // Act
            var resolvedSource = component.resolveSource();

            // Assert
            assertEquals(source, resolvedSource);
        }
    }

    @Nested
    @DisplayName("Source Container ID Tests")
    class SourceContainerIdTests {

        @Test
        @DisplayName("Should resolve source from container ID")
        void shouldResolveSourceId(RequestConfigDecorator requestConfigDecorator) {
            // Arrange
            requestConfigDecorator.setViewId(VIEW_ID);
            var component = new SourceCodeComponent();
            component.setSourceContainerId(WRAPPER_ID);

            // Act
            var resolved = component.resolveSource();

            // Assert
            assertTrue(resolved.contains("Extract Sample Source"));
        }
    }

    @Nested
    @DisplayName("Source File Tests")
    class SourceFileTests {

        @ParameterizedTest
        @CsvSource({"test.properties,hello=world", "relativeResources.properties,hello=relative",
                "absoluteResources.properties,hello=absolute"})
        @DisplayName("Should read relative source files")
        void shouldReadRelativeSourceFile(String path, String result, RequestConfigDecorator requestConfigDecorator) {
            // Arrange
            requestConfigDecorator.setViewId(VIEW_ID);
            var component = new SourceCodeComponent();
            component.setSourcePath(path);

            // Act
            var resolved = component.resolveSource();

            // Assert
            assertEquals(result, resolved);
        }

        @Test
        @DisplayName("Should read absolute source file")
        void shouldReadAbsoluteSourceFile(RequestConfigDecorator requestConfigDecorator) {
            // Arrange
            requestConfigDecorator.setViewId(VIEW_ID);
            var component = new SourceCodeComponent();
            component.setSourcePath("/META-INF" + BASE + "absolut.properties");

            // Act
            var resolved = component.resolveSource();

            // Assert
            assertEquals("hello=world2", resolved);
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should handle missing absolute source file")
        void shouldFailToReadAbsoluteSourceFile(RequestConfigDecorator requestConfigDecorator) {
            // Arrange
            requestConfigDecorator.setViewId(VIEW_ID);
            var component = new SourceCodeComponent();
            component.setSourcePath("/META-INF" + BASE + "notthere.properties");

            // Act
            var resolved = component.resolveSource();

            // Assert
            assertTrue(resolved.startsWith("Unable lo load "), resolved);
        }

        @Test
        @DisplayName("Should handle missing relative source file")
        void shouldFailToLoadRelativeSourceFile(RequestConfigDecorator requestConfigDecorator) {
            // Arrange
            requestConfigDecorator.setViewId(VIEW_ID);
            var component = new SourceCodeComponent();
            component.setSourcePath("not.there");

            // Act
            var resolved = component.resolveSource();

            // Assert
            assertTrue(resolved.startsWith("Unable lo load "), resolved);
        }
    }
}
