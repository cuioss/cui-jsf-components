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
package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.jsf.bootstrap.BootstrapFamily.COMPONENT_FAMILY;
import static de.cuioss.jsf.bootstrap.BootstrapFamily.LABELED_CONTAINER_COMPONENT_RENDERER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.bootstrap.layout.input.support.MockComponentPlugin;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import jakarta.faces.event.PostAddToViewEvent;
import jakarta.faces.event.PreRenderComponentEvent;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"placeholderKey", "placeholderValue", "titleKey", "titleValue",
        "placeholderConverter", "errorClass", "forIdentifier", "renderMessage", "layoutMode", "titleConverter",
        "contentSize", "labelSize", "disabled", "contentStyleClass", "labelStyleClass",
        "renderInputGroup"},
        defaultValued = {"errorClass", "forIdentifier", "renderMessage", "layoutMode",
                "contentSize", "labelSize"})
class LabeledContainerComponentTest extends AbstractUiComponentTest<LabeledContainerComponent> {

    @Test
    void shouldCallPluginChild() {
        getComponentConfigDecorator().registerMockRenderer(COMPONENT_FAMILY, LABELED_CONTAINER_COMPONENT_RENDERER);
        var component = anyComponent();
        var plugin = new MockComponentPlugin();
        component.getChildren().add(plugin);
        assertFalse(plugin.isPrerenderCalled());
        assertFalse(plugin.isPostAddToViewCalled());
        component.processEvent(new PreRenderComponentEvent(component));
        assertTrue(plugin.isPrerenderCalled());
        component.processEvent(new PostAddToViewEvent(component));
        assertTrue(plugin.isPostAddToViewCalled());
    }

    @Test
    void shouldCallPluginAsFacet() {
        getComponentConfigDecorator().registerMockRenderer(COMPONENT_FAMILY, LABELED_CONTAINER_COMPONENT_RENDERER);
        var component = anyComponent();
        var plugin = new MockComponentPlugin();
        component.getFacets().put(ContainerFacets.LABEL.getName(), plugin);
        assertFalse(plugin.isPrerenderCalled());
        component.processEvent(new PreRenderComponentEvent(component));
        assertTrue(plugin.isPrerenderCalled());
        component.processEvent(new PostAddToViewEvent(component));
        assertTrue(plugin.isPostAddToViewCalled());
    }
}
