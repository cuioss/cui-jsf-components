package com.icw.ehf.cui.components.bootstrap.layout.input;

import static com.icw.ehf.cui.components.bootstrap.layout.input.HelpTextComponent.FIXED_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.button.CommandButton;
import com.icw.ehf.cui.components.bootstrap.layout.messages.CuiMessageComponent;
import com.icw.ehf.cui.components.bootstrap.layout.messages.CuiMessageRenderer;
import com.icw.ehf.cui.core.api.CoreJsfTestConfiguration;
import com.icw.ehf.cui.core.api.components.JsfComponentIdentifier;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;

@VerifyComponentProperties(of = { "titleKey", "titleValue", "contentKey", "contentValue", "contentEscape" })
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class HelpTextComponentTest extends AbstractComponentTest<HelpTextComponent> implements ComponentConfigurator {

    @Test
    void shouldProvideMetadata() {
        assertEquals(JsfComponentIdentifier.INPUT_FAMILY, anyComponent().getFamily());
        assertEquals(JsfComponentIdentifier.HIDDEN_RENDERER_TYPE, anyComponent().getRendererType());
    }

    @Test
    void shouldProvideFixedAttributes() {
        assertEquals(FIXED_ID, anyComponent().getId());
    }

    @Override
    public void configure(HelpTextComponent toBeConfigured) {
        var parent = new LabeledContainerComponent();
        parent.setId("labeledContainer");
        parent.getChildren().add(toBeConfigured);
    }

    @Override
    public void configureComponents(ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(CommandButton.class).registerUIComponent(CuiMessageComponent.class)
                .registerRenderer(CuiMessageRenderer.class).registerRenderer(LabeledContainerRenderer.class)
                .registerMockRendererForHtmlInputText();
    }
}
