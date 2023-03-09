package com.icw.ehf.cui.components.bootstrap.button;

import static com.icw.ehf.cui.components.bootstrap.CssBootstrap.BUTTON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.event.PreRenderComponentEvent;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;
import com.icw.ehf.cui.core.api.components.css.AlignHolder;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "icon", "size", "state", "titleKey", "titleValue", "iconAlign", "keyBinding" })
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class ButtonTest extends AbstractComponentTest<Button> {

    private static final String KEY_BINDING = AttributeValue.CUI_CLICK_BINDING.getContent();

    @Test
    void shouldHandleStyleClass() {
        var component = anyComponent();
        var expected = BUTTON.getStyleClass() + " btn-default";
        assertEquals(expected, component.getStyleClass());
        var custom = "custom";
        component.setStyleClass(custom);
        assertEquals(expected + " " + custom, component.getStyleClass());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.BUTTON_RENDERER, anyComponent().getRendererType());
        assertEquals(BootstrapFamily.COMPONENT_FAMILY, anyComponent().getFamily());
    }

    @Test
    void shouldFailToSetValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            anyComponent().setValue("");
        });
    }

    @Test
    void shouldResolveGetValueFromLabel() {
        var component = anyComponent();
        assertNull(component.getValue());
        var label = Generators.letterStrings().next();
        component.setLabelValue(label);
        assertEquals(label, component.getValue());
    }

    @Test
    void shouldResolveGetTitleFromTitleProvider() {
        var component = anyComponent();
        assertNull(component.getTitle());
        var label = Generators.letterStrings().next();
        component.setTitleValue(label);
        assertEquals(label, component.getTitle());
    }

    @Test
    void shouldDetermineRightIcon() {
        var component = anyComponent();
        assertFalse(component.isDisplayIconRight());
        component.setIcon("icon");
        assertFalse(component.isDisplayIconRight());
        component.setIconAlign(AlignHolder.RIGHT.name());
        assertTrue(component.isDisplayIconRight());
    }

    @Test
    void shouldDetermineLeftIcon() {
        var component = anyComponent();
        assertFalse(component.isDisplayIconLeft());
        component.setIcon("icon");
        assertTrue(component.isDisplayIconLeft());
        component.setIconAlign(AlignHolder.RIGHT.name());
        assertFalse(component.isDisplayIconLeft());
    }

    @Test
    void shouldAddKeyBindingOnPreRender() {
        var component = anyComponent();
        assertFalse(component.getPassThroughAttributes().containsKey(KEY_BINDING));
        component.processEvent(new PreRenderComponentEvent(component));
        assertFalse(component.getPassThroughAttributes().containsKey(KEY_BINDING));
        component.setKeyBinding("s");
        component.processEvent(new PreRenderComponentEvent(component));
        assertTrue(component.getPassThroughAttributes().containsKey(KEY_BINDING));
        assertEquals("s", component.getPassThroughAttributes().get(KEY_BINDING));
    }
}
