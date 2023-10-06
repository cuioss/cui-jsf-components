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
package de.cuioss.jsf.api.application.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.converter.nameprovider.LabeledKeyConverter;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.valueobjects.contract.EqualsAndHashcodeContractImpl;
import de.cuioss.test.valueobjects.contract.ReflectionUtil;
import de.cuioss.uimodel.nameprovider.LabeledKey;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;

@JsfTestConfiguration({ CoreJsfTestConfiguration.class })
class DisplayNameProviderMessageProducerTest extends JsfEnabledTestEnvironment {

    private static final String STRING_RESULT = "test";

    private static final String MESSAGE_KEY = "bundle1.property1";

    private static final LabeledKey DETAIL = new LabeledKey(MESSAGE_KEY);

    private MessageProducerMock messageProducerMock;

    private DisplayNameProviderMessageProducer underTest;

    @BeforeEach
    void before() {
        messageProducerMock = new MessageProducerMock();
        underTest = new DisplayNameProviderMessageProducer(messageProducerMock);
    }

    @Test
    void testShowAsGlobalMessageAndLogWithError() {
        getComponentConfigDecorator().registerConverter(LabeledKeyConverter.class, LabeledKey.class);
        var result = new ResultObject<>(STRING_RESULT, ResultState.ERROR, new ResultDetail(DETAIL));
        underTest.showAsGlobalMessageAndLog(result);
        assertEquals(1, messageProducerMock.getGlobalMessages().size());
        messageProducerMock.assertSingleGlobalMessageWithKeyPresent(MESSAGE_KEY);
    }

    @Test
    void testShowAsGlobalMessageAndLogWithWarn() {
        getComponentConfigDecorator().registerConverter(LabeledKeyConverter.class, LabeledKey.class);
        var result = new ResultObject<>(STRING_RESULT, ResultState.WARNING, new ResultDetail(DETAIL));
        underTest.showAsGlobalMessageAndLog(result);
        assertEquals(1, messageProducerMock.getGlobalMessages().size());
        messageProducerMock.assertSingleGlobalMessageWithKeyPresent(MESSAGE_KEY);
    }

    @Test
    void testShowAsGlobalMessageAndLogWithInfo() {
        getComponentConfigDecorator().registerConverter(LabeledKeyConverter.class, LabeledKey.class);
        var result = new ResultObject<>(STRING_RESULT, ResultState.INFO, new ResultDetail(DETAIL));
        underTest.showAsGlobalMessageAndLog(result);
        assertEquals(1, messageProducerMock.getGlobalMessages().size());
        messageProducerMock.assertSingleGlobalMessageWithKeyPresent(MESSAGE_KEY);
    }

    @Test
    void testShowAsGlobalMessageAndLogWithValid() {
        var result = new ResultObject<>(STRING_RESULT, ResultState.VALID, null);
        underTest.showAsGlobalMessageAndLog(result);
        assertEquals(0, messageProducerMock.getGlobalMessages().size());
    }

    @Test
    void shouldFailOnNullConstructor() {
        assertThrows(NullPointerException.class, () -> new DisplayNameProviderMessageProducer(null));
    }

    @Test
    void shouldHandleObjectContract() {
        ReflectionUtil.assertToStringMethodIsOverriden(DisplayNameProviderMessageProducer.class);
        assertNotNull(underTest.toString());
        EqualsAndHashcodeContractImpl.assertBasicContractOnEquals(underTest);
        EqualsAndHashcodeContractImpl.assertBasicContractOnHashCode(underTest);
    }
}
