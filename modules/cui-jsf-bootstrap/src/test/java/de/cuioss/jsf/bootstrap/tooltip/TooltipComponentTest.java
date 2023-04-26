package de.cuioss.jsf.bootstrap.tooltip;

import static de.cuioss.jsf.api.components.html.AttributeValue.DATA_TOGGLE;
import static de.cuioss.jsf.bootstrap.tooltip.TooltipComponent.DATA_DELAY;
import static de.cuioss.jsf.bootstrap.tooltip.TooltipComponent.DATA_PLACEMENT;
import static de.cuioss.jsf.bootstrap.tooltip.TooltipComponent.DATA_TRIGGER;
import static de.cuioss.jsf.bootstrap.tooltip.TooltipComponent.PLACEMENT_DEFAULT;
import static de.cuioss.jsf.bootstrap.tooltip.TooltipComponent.TOOLTIP;
import static de.cuioss.jsf.bootstrap.tooltip.TooltipComponent.TRIGGER_DEFAULT;
import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.html.HtmlInputText;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostAddToViewEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.CoreJsfTestConfiguration;
import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import lombok.Getter;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class TooltipComponentTest extends AbstractComponentTest<TooltipComponent> {

    @Getter
    private TooltipComponent underTest;

    private HtmlInputText parent;

    private ComponentSystemEvent expectedEvent;

    private final TypedGenerator<String> names = Generators.letterStrings(2, 5);

    @BeforeEach
    void before() {
        underTest = new TooltipComponent();
        parent = new HtmlInputText();
        underTest.setParent(parent);
        expectedEvent = new PostAddToViewEvent(underTest);
    }

    @Test
    void shouldDecorateMinimal() {
        assertTrue(parent.getPassThroughAttributes(true).isEmpty());
        underTest.processEvent(expectedEvent);
        final var attributes = parent.getPassThroughAttributes();
        assertEquals(4, attributes.size());
        assertEquals(PLACEMENT_DEFAULT, attributes.get(DATA_PLACEMENT));
        assertEquals(TRIGGER_DEFAULT, attributes.get(DATA_TRIGGER));
        assertEquals(TOOLTIP, attributes.get(DATA_TOGGLE.getContent()));
        // title should not be touched
        assertNull(emptyToNull(parent.getTitle()));
    }

    @Test
    void shouldDecorateComplex() {
        assertTrue(parent.getPassThroughAttributes(true).isEmpty());
        final var content = names.next();
        final var placement = names.next();
        final var trigger = names.next();
        final var delay = Generators.integers(501, 999).next();
        underTest.setContentValue(content);
        underTest.setPlacement(placement);
        underTest.setTrigger(trigger);
        underTest.setDelay(delay);
        underTest.processEvent(expectedEvent);
        final var attributes = parent.getPassThroughAttributes();
        assertEquals(4, attributes.size());
        assertEquals(placement, attributes.get(DATA_PLACEMENT));
        assertEquals(trigger, attributes.get(DATA_TRIGGER));
        assertEquals(TOOLTIP, attributes.get(DATA_TOGGLE.getContent()));
        assertEquals(delay, attributes.get(DATA_DELAY));
        // title should be set to content
        assertEquals(content, parent.getTitle());
    }
}
