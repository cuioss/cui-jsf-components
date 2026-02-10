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
package de.cuioss.jsf.bootstrap.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.icon.support.CssMimeTypeIcon;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"decoratorClass", "mimeTypeIcon", "mimeTypeString", "size", "titleKey",
        "titleValue"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@DisplayName("Tests for MimeTypeIconComponent")
class MimeTypeIconComponentTest extends AbstractUiComponentTest<MimeTypeIconComponent> {

    @BeforeEach
    void configureCuiComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    @Nested
    @DisplayName("Default behavior tests")
    class DefaultBehaviorTests {

        @Test
        @DisplayName("Should default to no decorator class")
        void shouldDefaultToNoDecorator() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(CssMimeTypeIcon.CUI_STACKED_ICON_NO_DECORATOR.getStyleClass(), component.getDecoratorClass(),
                    "Default decorator class should be CUI_STACKED_ICON_NO_DECORATOR");
        }

        @Test
        @DisplayName("Should resolve to UNDEFINED mime type icon by default")
        void shouldResolveToDefaultIcon() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(MimeTypeIcon.UNDEFINED, component.resolveMimeTypeIcon(),
                    "Default mime type icon should be UNDEFINED");
        }
    }

    @Nested
    @DisplayName("MimeType resolution tests")
    class MimeTypeResolutionTests {

        @Test
        @DisplayName("Should prioritize MimeTypeIcon object over string representation")
        void shouldPrioritizeModelOverString() {
            // Arrange
            var component = anyComponent();
            component.setMimeTypeIcon(MimeTypeIcon.AUDIO_BASIC);
            component.setMimeTypeString(MimeTypeIcon.AUDIO_MPEG.name());

            // Act
            var result = component.resolveMimeTypeIcon();

            // Assert
            assertEquals(MimeTypeIcon.AUDIO_BASIC, result,
                    "Should use MimeTypeIcon object when both are set");
        }

        @Test
        @DisplayName("Should resolve MimeTypeIcon from string representation")
        void shouldResolveModelFromString() {
            // Arrange
            var component = anyComponent();
            component.setMimeTypeString(MimeTypeIcon.AUDIO_MPEG.name());

            // Act
            var result = component.resolveMimeTypeIcon();

            // Assert
            assertEquals(MimeTypeIcon.AUDIO_MPEG, result,
                    "Should resolve MimeTypeIcon from string representation");
        }
    }

    @Nested
    @DisplayName("Component metadata tests")
    class MetadataTests {

        @Test
        @DisplayName("Should provide correct renderer type")
        void shouldProvideCorrectRendererType() {
            // Arrange
            var component = anyComponent();

            // Act & Assert
            assertEquals(BootstrapFamily.MIME_TYPE_ICON_COMPONENT_RENDERER, component.getRendererType(),
                    "Renderer type should match MIME_TYPE_ICON_COMPONENT_RENDERER");
        }
    }
}
