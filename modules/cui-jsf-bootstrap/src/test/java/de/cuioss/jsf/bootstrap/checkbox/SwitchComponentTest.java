package de.cuioss.jsf.bootstrap.checkbox;

import static de.cuioss.tools.collect.CollectionLiterals.immutableMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.CoreJsfTestConfiguration;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;

@VerifyComponentProperties(of = { "offTextValue", "offTextKey", "onTextValue", "onTextKey", "titleValue", "titleKey",
    "rendered", "disabled" })
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class SwitchComponentTest extends AbstractComponentTest<SwitchComponent> {

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.SWITCH_RENDERER, anyComponent().getRendererType());
        assertEquals(BootstrapFamily.COMPONENT_FAMILY, anyComponent().getFamily());
    }

    @Test
    void shouldResolveText() {
        var onText = Generators.nonEmptyStrings().next();
        var offText = Generators.nonEmptyStrings().next();
        var underTest = anyComponent();
        underTest.setOnTextValue(onText);
        underTest.setOffTextValue(offText);
        assertEquals(offText, underTest.resolveOffText());
        assertEquals(onText, underTest.resolveOnText());
    }

    @Test
    void shouldResolveTitleText() {
        var sample = Generators.nonEmptyStrings().next();
        var underTest = anyComponent();
        underTest.setTitleValue(sample);
        assertEquals(sample, underTest.resolveTitle());
    }

    @Test
    void shouldResolveStyle() {
        var sample = Generators.nonEmptyStrings().next();
        var underTest = anyComponent();
        underTest.setStyle(sample);
        assertNull(underTest.getStyle());
        assertEquals(sample, underTest.resolveStyle());
    }

    @Test
    void shouldResolveStyleClass() {
        var sample = Generators.nonEmptyStrings().next().trim();
        var underTest = anyComponent();
        underTest.setStyleClass(sample);
        assertNull(underTest.getStyleClass());
        assertEquals(sample, underTest.getStyleClassBuilder().getStyleClass());
    }

    @Test
    void shouldResolvePassThroughAttributes() {
        var underTest = anyComponent();
        underTest.setDisabled(false);
        assertEquals(immutableMap("data-switch-disabled", "false"), underTest.resolvePassThroughAttributes());
        underTest.setDisabled(true);
        assertEquals(immutableMap("data-switch-disabled", "true"), underTest.resolvePassThroughAttributes());
    }
}
