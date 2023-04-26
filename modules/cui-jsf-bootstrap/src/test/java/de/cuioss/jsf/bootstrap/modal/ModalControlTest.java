package de.cuioss.jsf.bootstrap.modal;

import static de.cuioss.jsf.bootstrap.modal.ModalControl.DATA_ACTION;
import static de.cuioss.jsf.bootstrap.modal.ModalControl.DATA_EVENT;
import static de.cuioss.jsf.bootstrap.modal.ModalControl.DATA_FOR;
import static de.cuioss.jsf.bootstrap.modal.ModalControl.DEFAULT_ACTION;
import static de.cuioss.jsf.bootstrap.modal.ModalControl.DEFAULT_EVENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.component.html.HtmlOutcomeTargetButton;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostAddToViewEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import lombok.Getter;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VerifyComponentProperties(of = { "action", "event", "for" }, defaultValued = { "action", "event" })
class ModalControlTest extends AbstractComponentTest<ModalControl> {

    private static final String FOR_ID = "forId";

    @Getter
    private ModalControl underTest;

    private HtmlOutcomeTargetButton parent;

    private ComponentSystemEvent expectedEvent;

    @BeforeEach
    void before() {
        underTest = new ModalControl();
        parent = JsfHtmlComponent.createComponent(getFacesContext(), JsfHtmlComponent.BUTTON);
        underTest.setParent(parent);
        underTest.setFor(FOR_ID);
        expectedEvent = new PostAddToViewEvent(underTest);
    }

    @Test
    void shouldDecorateDefault() {
        assertTrue(parent.getPassThroughAttributes(true).isEmpty());
        underTest.processEvent(expectedEvent);
        final var attributes = parent.getPassThroughAttributes();
        assertEquals(3, attributes.size());
        assertEquals(FOR_ID, attributes.get(DATA_FOR));
        assertEquals(DEFAULT_ACTION, attributes.get(DATA_ACTION));
        assertEquals(DEFAULT_EVENT, attributes.get(DATA_EVENT));
    }

    @Test
    void shouldFailOnMissingForIdentifier() {
        underTest.setFor(null);
        assertThrows(IllegalArgumentException.class, () -> underTest.processEvent(expectedEvent));
    }
}
