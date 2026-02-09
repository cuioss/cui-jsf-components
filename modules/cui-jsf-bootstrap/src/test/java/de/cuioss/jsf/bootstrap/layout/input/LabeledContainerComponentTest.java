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
package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.jsf.bootstrap.BootstrapFamily.COMPONENT_FAMILY;
import static de.cuioss.jsf.bootstrap.BootstrapFamily.LABELED_CONTAINER_COMPONENT_RENDERER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.bootstrap.layout.input.support.MockComponentPlugin;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"placeholderKey", "placeholderValue", "titleKey", "titleValue",
        "placeholderConverter", "errorClass", "forIdentifier", "renderMessage", "layoutMode", "titleConverter",
        "contentSize", "labelSize", "disabled", "contentStyleClass", "labelStyleClass",
        "renderInputGroup"},
        defaultValued = {"errorClass", "forIdentifier", "renderMessage", "layoutMode",
                "contentSize", "labelSize"})
@DisplayName("Tests for LabeledContainerComponent")
class LabeledContainerComponentTest extends AbstractUiComponentTest<LabeledContainerComponent> {

    @Nested
    @DisplayName("Tests for plugin handling")
    class PluginHandlingTests {

        @Test
        @DisplayName("Should call plugin methods when plugin is a child")
        void shouldCallPluginChild(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerMockRenderer(COMPONENT_FAMILY, LABELED_CONTAINER_COMPONENT_RENDERER);
            var component = anyComponent();
            var plugin = new MockComponentPlugin();
            component.getChildren().add(plugin);

            // Assert - Initial state
            assertFalse(plugin.isPrerenderCalled(), "Prerender should not be called initially");
            assertFalse(plugin.isPostAddToViewCalled(), "PostAddToView should not be called initially");

            // Act - Process PreRenderComponentEvent
            component.processEvent(new PreRenderComponentEvent(component));

            // Assert - After PreRenderComponentEvent
            assertTrue(plugin.isPrerenderCalled(), "Prerender should be called after PreRenderComponentEvent");

            // Act - Process PostAddToViewEvent
            component.processEvent(new PostAddToViewEvent(component));

            // Assert - After PostAddToViewEvent
            assertTrue(plugin.isPostAddToViewCalled(), "PostAddToView should be called after PostAddToViewEvent");
        }

        @Test
        @DisplayName("Should call plugin methods when plugin is a facet")
        void shouldCallPluginAsFacet(ComponentConfigDecorator componentConfig) {
            // Arrange
            componentConfig.registerMockRenderer(COMPONENT_FAMILY, LABELED_CONTAINER_COMPONENT_RENDERER);
            var component = anyComponent();
            var plugin = new MockComponentPlugin();
            component.getFacets().put(ContainerFacets.LABEL.getName(), plugin);

            // Assert - Initial state
            assertFalse(plugin.isPrerenderCalled(), "Prerender should not be called initially");

            // Act - Process PreRenderComponentEvent
            component.processEvent(new PreRenderComponentEvent(component));

            // Assert - After PreRenderComponentEvent
            assertTrue(plugin.isPrerenderCalled(), "Prerender should be called after PreRenderComponentEvent");

            // Act - Process PostAddToViewEvent
            component.processEvent(new PostAddToViewEvent(component));

            // Assert - After PostAddToViewEvent
            assertTrue(plugin.isPostAddToViewCalled(), "PostAddToView should be called after PostAddToViewEvent");
        }
    }
}
