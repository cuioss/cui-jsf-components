package de.cuioss.jsf.bootstrap.layout.input;

import static de.cuioss.jsf.bootstrap.BootstrapFamily.COMPONENT_FAMILY;
import static de.cuioss.jsf.bootstrap.BootstrapFamily.LABELED_CONTAINER_COMPONENT_RENDERER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PreRenderComponentEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.layout.input.support.MockComponentPlugin;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(
    of = { "placeholderKey", "placeholderValue", "titleKey", "titleValue", "placeholderConverter", "errorClass",
        "forIdentifier", "renderMessage", "layoutMode", "titleConverter", "contentSize", "labelSize", "disabled",
        "contentStyleClass", "labelStyleClass", "renderInputGroup" },
    defaultValued = { "errorClass", "forIdentifier", "renderMessage", "layoutMode", "contentSize", "labelSize" })
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
