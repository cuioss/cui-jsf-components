package com.icw.ehf.cui.components.bootstrap.tag;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.UIInput;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.ValueChangeEvent;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.button.CloseCommandButton;
import com.icw.ehf.cui.components.bootstrap.layout.input.support.MockUIInput;
import com.icw.ehf.cui.core.api.components.JsfHtmlComponent;
import com.icw.ehf.cui.core.api.components.events.ModelPayloadEvent;

import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.mocks.CuiMockMethodExpression;

@VerifyComponentProperties(of = { "model", "disposable", "contentKey", "contentValue", "size", "state", "titleKey",
    "titleValue", "contentEscape" })
class TagComponentTest extends AbstractUiComponentTest<TagComponent> implements ComponentConfigurator {

    private static final String PAYLOAD = "data";

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.TAG_COMPONENT_RENDERER, anyComponent().getRendererType());
    }

    @Test
    void shouldProvideCorrectEventInfo() {
        assertEquals("click", anyComponent().getDefaultEventName());
        assertEquals(immutableList("click"), anyComponent().getEventNames());
    }

    @Test
    void shouldConsiderIsDisposedOnRenderedAttribute() {
        final var component = createComponent();
        assertTrue(component.isRendered());
        simulateDisposeItem(component);
        assertFalse(component.isRendered());
        // component.setRendered(false);
        assertFalse(component.isRendered());
        // component.setDisposed(false);
        assertFalse(component.isRendered());
    }

    private static TagComponent createComponent() {
        final var tagComponent = new TagComponent();
        tagComponent.setDisposable(true);
        tagComponent.processEvent(new PostAddToViewEvent(new MockUIInput()));
        return tagComponent;
    }

    private static void simulateDisposeItem(final TagComponent component) {
        final var hiddenInput = (UIInput) component.getChildren().stream().filter(child -> child instanceof UIInput)
                .findFirst().orElseThrow(
                        () -> new IllegalStateException("Hidden input is missing but should be available as child"));
        hiddenInput.setValue("true");
    }

    @Test
    void shouldBroadcastDisposeEvent() {
        final var component = new TagComponent();
        final var expression = new CuiMockMethodExpression();
        component.setDisposeListener(expression);
        component.broadcast(new ModelPayloadEvent(component, PAYLOAD));
        assertTrue(expression.isInvoked());
    }

    @Test
    void shouldNotBroadcastOnInvalidEvent() {
        final var component = new TagComponent();
        final var expression = new CuiMockMethodExpression();
        component.setDisposeListener(expression);
        component.broadcast(new ValueChangeEvent(component, PAYLOAD, PAYLOAD));
        assertFalse(expression.isInvoked());
    }

    @Test
    void shouldNotBroadcastIfNoListenerIsSet() {
        final var component = new TagComponent();
        final var expression = new CuiMockMethodExpression();
        component.broadcast(new ModelPayloadEvent(component, PAYLOAD));
        assertFalse(expression.isInvoked());
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(CloseCommandButton.class).registerUIComponent(
                JsfHtmlComponent.HIDDEN.getComponentType(), JsfHtmlComponent.HIDDEN.getComponentClass());
    }
}
