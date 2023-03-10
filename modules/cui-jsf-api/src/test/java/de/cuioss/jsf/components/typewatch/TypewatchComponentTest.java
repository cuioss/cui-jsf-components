package de.cuioss.jsf.components.typewatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostAddToViewEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;

@VerifyComponentProperties(of = { "process", "update", "allowSubmit", "wait", "highlight", "captureLength" })
class TypewatchComponentTest extends AbstractComponentTest<TypewatchComponent> implements ComponentConfigurator {

    private HtmlInputText parent;

    private ComponentSystemEvent expectedEvent;

    @Test
    void shouldDecorateMinimal() {
        var underTest = anyComponent();
        assertEquals(1, parent.getParent().getChildren().size());
        assertEquals(0, parent.getParent().getChildren().get(0).getPassThroughAttributes().size());
        underTest.processEvent(expectedEvent);
        assertEquals(1, parent.getParent().getChildren().size());
        final var pt = parent.getParent().getChildren().get(0).getPassThroughAttributes();
        assertEquals("data-typewatch", pt.get("data-typewatch"));
        assertEquals(false, pt.get("data-typewatch-allowsubmit"));
        assertEquals(false, pt.get("data-typewatch-highlight"));
    }

    @Test
    void shouldDecorateMaximal() {
        var underTest = anyComponent();
        underTest.setAllowSubmit(true);
        underTest.setWait(666);
        underTest.setHighlight(true);
        underTest.setCaptureLength(42);
        underTest.processEvent(expectedEvent);
        final var pt = parent.getParent().getChildren().get(0).getPassThroughAttributes();
        assertEquals("data-typewatch", pt.get("data-typewatch"));
        assertEquals(true, pt.get("data-typewatch-allowsubmit"));
        assertEquals(666, pt.get("data-typewatch-wait"));
        assertEquals(true, pt.get("data-typewatch-highlight"));
        assertEquals(42, pt.get("data-typewatch-capturelength"));
    }

    @Override
    public void configure(final TypewatchComponent toBeConfigured) {
        super.configure(toBeConfigured);
        var panel = new HtmlPanelGroup();
        parent = new HtmlInputText();
        panel.getChildren().add(parent);
        toBeConfigured.setParent(parent);
        expectedEvent = new PostAddToViewEvent(toBeConfigured);
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerMockRendererForHtmlInputText();
    }
}
