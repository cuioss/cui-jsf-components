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

import javax.inject.Inject;

import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.EnableJSFCDIEnvironment;
import de.cuioss.jsf.api.EnableResourceBundleSupport;
import de.cuioss.jsf.api.converter.nameprovider.LabeledKeyConverter;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.util.JsfEnvironmentConsumer;
import de.cuioss.test.jsf.util.JsfEnvironmentHolder;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;
import de.cuioss.uimodel.nameprovider.LabeledKey;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;
import lombok.Getter;
import lombok.Setter;

@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
@AddBeanClasses(MessageProducerMock.class)
@JsfTestConfiguration({ CoreJsfTestConfiguration.class })
class DisplayNameMessageProducerTest
        implements ShouldHandleObjectContracts<DisplayNameMessageProducer>, JsfEnvironmentConsumer {

    private static final String STRING_RESULT = "invalid e-Mail Address syntax";

    private static final String MESSAGE_KEY = "de.cuioss.common.email.invalid";

    private static final LabeledKey DETAIL = new LabeledKey(MESSAGE_KEY);

    @Setter
    @Getter
    private JsfEnvironmentHolder environmentHolder;

    @Inject
    private MessageProducerMock messageProducerMock;

    @Inject
    @Getter
    private DisplayNameMessageProducer underTest;

    @Test
    void testShowAsGlobalMessageAndLogWithError() {
        getComponentConfigDecorator().registerConverter(LabeledKeyConverter.class, LabeledKey.class);
        var result = new ResultObject<>(STRING_RESULT, ResultState.ERROR, new ResultDetail(DETAIL));
        underTest.showAsGlobalMessageAndLog(result);
        assertEquals(1, messageProducerMock.getGlobalMessages().size());
        messageProducerMock.assertSingleGlobalMessageWithKeyPresent(STRING_RESULT);
    }

    @Test
    void testShowAsGlobalMessageAndLogWithWarn() {
        getComponentConfigDecorator().registerConverter(LabeledKeyConverter.class, LabeledKey.class);
        var result = new ResultObject<>(STRING_RESULT, ResultState.WARNING, new ResultDetail(DETAIL));
        underTest.showAsGlobalMessageAndLog(result);
        assertEquals(1, messageProducerMock.getGlobalMessages().size());
        messageProducerMock.assertSingleGlobalMessageWithKeyPresent(STRING_RESULT);
    }

    @Test
    void testShowAsGlobalMessageAndLogWithInfo() {
        getComponentConfigDecorator().registerConverter(LabeledKeyConverter.class, LabeledKey.class);
        var result = new ResultObject<>(STRING_RESULT, ResultState.INFO, new ResultDetail(DETAIL));
        underTest.showAsGlobalMessageAndLog(result);
        assertEquals(1, messageProducerMock.getGlobalMessages().size());
        messageProducerMock.assertSingleGlobalMessageWithKeyPresent(STRING_RESULT);
    }

    @Test
    void testShowAsGlobalMessageAndLogWithValid() {
        var result = new ResultObject<>(STRING_RESULT, ResultState.VALID, null);
        underTest.showAsGlobalMessageAndLog(result);
        assertEquals(0, messageProducerMock.getGlobalMessages().size());
    }
}
