/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
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
