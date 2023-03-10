package de.cuioss.jsf.components.blockelement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostAddToViewEvent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.base.BaseCuiCommandButton;
import de.cuioss.test.jsf.component.AbstractComponentTest;

class BlockElementDecoratorTest extends AbstractComponentTest<BlockElementDecorator> {

    private BaseCuiCommandButton parent;

    private ComponentSystemEvent expectedEvent;

    @Test
    void shouldDecorate() {
        var underTest = anyComponent();
        Assertions.assertNull(parent.getPassThroughAttributes().get("data-cui-block-element"));
        underTest.processEvent(expectedEvent);
        assertEquals("data-cui-block-element", parent.getPassThroughAttributes().get("data-cui-block-element"));
    }

    @Override
    public void configure(final BlockElementDecorator toBeConfigured) {
        super.configure(toBeConfigured);
        parent = new BaseCuiCommandButton();
        toBeConfigured.setParent(parent);
        expectedEvent = new PostAddToViewEvent(toBeConfigured);
    }
}
