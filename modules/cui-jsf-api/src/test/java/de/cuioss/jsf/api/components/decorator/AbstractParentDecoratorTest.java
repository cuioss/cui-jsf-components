package de.cuioss.jsf.api.components.decorator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.html.HtmlInputText;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PostValidateEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.component.AbstractComponentTest;

class AbstractParentDecoratorTest extends AbstractComponentTest<ParentDecoratorDummy> {

    private ComponentSystemEvent expectedEvent;

    private ComponentSystemEvent unexpectedEvent;

    @BeforeEach
    void init() {
        expectedEvent = new PostAddToViewEvent(new HtmlInputText());
        unexpectedEvent = new PostValidateEvent(new HtmlInputText());
    }

    @Override
    public void configure(final ParentDecoratorDummy toBeConfigured) {
        toBeConfigured.setParent(new HtmlInputText());
    }

    @Test
    void shouldReactOnExpectedEvent() {
        var underTest = anyComponent();
        assertFalse(underTest.isDecorateCalled());
        underTest.processEvent(expectedEvent);
        assertTrue(underTest.isDecorateCalled());
    }

    @Test
    void shouldIgnoreInvalidEvent() {
        var underTest = anyComponent();
        assertFalse(underTest.isDecorateCalled());
        underTest.processEvent(unexpectedEvent);
        assertFalse(underTest.isDecorateCalled());
    }

    @Test
    void shouldFailOnMissingParent() {
        var underTest = anyComponent();
        underTest.setParent(null);
        assertThrows(NullPointerException.class, () -> underTest.processEvent(expectedEvent));
    }

    @Test
    void shouldActAsComponentBridge() {
        assertNotNull(anyComponent().facesContext());
        assertNotNull(anyComponent().stateHelper());
    }
}
