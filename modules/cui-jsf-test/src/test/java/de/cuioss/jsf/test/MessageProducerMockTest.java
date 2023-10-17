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
package de.cuioss.jsf.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.junit5.AbstractBeanTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import lombok.Getter;

@PropertyReflectionConfig(skip = true)
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
class MessageProducerMockTest extends AbstractBeanTest<MessageProducerMock> {

    private static final String SOME_KEY = "some.key";
    private static final String COMPONENT_ID = "componentId";

    @Inject
    @Getter
    MessageProducerMock underTest;

    @Test
    void shouldDetectMissingMessage() {
        var mock = new MessageProducerMock();
        assertThrows(AssertionError.class, () -> mock.assertSingleGlobalMessageWithKeyPresent(SOME_KEY));
    }

    @Test
    void shouldAssertThatNoMessageIsPresent() {
        new MessageProducerMock().assertThatNoMessageIsPresent();
    }

    @Test
    void shouldHandleGlobalMessageWithKey() {
        var underTest = new MessageProducerMock();
        underTest.setGlobalInfoMessage(SOME_KEY);
        underTest.assertSingleGlobalMessageWithKeyPresent(SOME_KEY);
    }

    @Test
    void shouldHandleComponentMessage() {
        var underTest = new MessageProducerMock();
        assertTrue(underTest.getComponentMessages().isEmpty());
        assertTrue(underTest.getGlobalMessages().isEmpty());
        underTest.setFacesMessage(SOME_KEY, FacesMessage.SEVERITY_INFO, COMPONENT_ID);
        assertEquals(1, underTest.getComponentMessages().size());
        assertTrue(underTest.getGlobalMessages().isEmpty());
    }

    @Test
    void shouldHandleGlobalMessage() {
        var underTest = new MessageProducerMock();
        underTest.addGlobalMessage(SOME_KEY, FacesMessage.SEVERITY_ERROR);
        underTest.assertSingleGlobalMessageWithKeyPresent(SOME_KEY);
    }

    @Test
    void shouldAddMsg() {
        var underTest = new MessageProducerMock();
        assertTrue(underTest.getComponentMessages().isEmpty());
        assertTrue(underTest.getGlobalMessages().isEmpty());
        underTest.addMessage(SOME_KEY, FacesMessage.SEVERITY_INFO, COMPONENT_ID);
        assertEquals(1, underTest.getComponentMessages().size());
        assertTrue(underTest.getGlobalMessages().isEmpty());
    }

    @Test
    void shouldAssertGlobalMessageIsPresent() {
        var underTest = new MessageProducerMock();
        underTest.addGlobalMessage(SOME_KEY, FacesMessage.SEVERITY_ERROR);
        underTest.assertGlobalMessageWithKeyPresent(FacesMessage.SEVERITY_ERROR, SOME_KEY);

        var compMessages = underTest.getComponentMessagesForKey(FacesMessage.SEVERITY_ERROR, SOME_KEY);
        assertNotNull(compMessages);
        assertTrue(compMessages.isEmpty());
    }

    @Test
    void shouldAssertComponentMessageIsPresent() {
        var underTest = new MessageProducerMock();
        underTest.addMessage(SOME_KEY, FacesMessage.SEVERITY_ERROR, COMPONENT_ID);
        underTest.assertComponentMessageWithKeyPresent(FacesMessage.SEVERITY_ERROR, SOME_KEY);

        var globalMessages = underTest.getGlobalMessagesForKey(FacesMessage.SEVERITY_ERROR, SOME_KEY);
        assertNotNull(globalMessages);
        assertTrue(globalMessages.isEmpty());
    }
}
