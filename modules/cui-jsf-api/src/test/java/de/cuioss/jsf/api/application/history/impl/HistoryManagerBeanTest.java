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
package de.cuioss.jsf.api.application.history.impl;

import static de.cuioss.jsf.api.application.history.impl.HistoryManagerImplTest.CURRENT_VIEW_XHTML;
import static de.cuioss.jsf.api.application.history.impl.HistoryManagerImplTest.FALLBACK_OUTCOME;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.ApplicationConfigurator;
import de.cuioss.test.jsf.config.decorator.ApplicationConfigDecorator;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.valueobjects.junit5.contracts.ShouldHandleObjectContracts;

class HistoryManagerBeanTest extends JsfEnabledTestEnvironment
        implements ApplicationConfigurator, ShouldHandleObjectContracts<HistoryManagerBean> {

    @Test
    void shouldHandleFallBackOucome() {
        var underTest = new HistoryManagerBean();
        var historyConfiguration = new HistoryConfigurationImpl();
        historyConfiguration.setFallbackOutcome(FALLBACK_OUTCOME);
        getBeanConfigDecorator().register(historyConfiguration, HistoryConfigurationImpl.BEAN_NAME);
        assertDoesNotThrow(underTest::initBean);
    }

    @Test
    void shouldHandleFallBackView() {
        var underTest = new HistoryManagerBean();
        var historyConfiguration = new HistoryConfigurationImpl();
        historyConfiguration.setFallback(CURRENT_VIEW_XHTML);
        getBeanConfigDecorator().register(historyConfiguration, HistoryConfigurationImpl.BEAN_NAME);
        assertDoesNotThrow(underTest::initBean);
    }

    @Test
    void shouldFailOnIncompleteConfigurationForNavigationCase() {
        var underTest = new HistoryManagerBean();
        var historyConfiguration = new HistoryConfigurationImpl();
        historyConfiguration.setFallbackOutcome(FALLBACK_OUTCOME);
        getBeanConfigDecorator().register(historyConfiguration, HistoryConfigurationImpl.BEAN_NAME);
        getApplicationConfigDecorator().getMockNavigationHandler().getNavigationCases().clear();
        underTest.initBean();
        assertThrows(IllegalStateException.class, underTest::iterator);

    }

    @Test
    void shouldFailOnInvalidConfiguration() {
        var underTest = new HistoryManagerBean();
        getBeanConfigDecorator().register(new HistoryConfigurationImpl(), HistoryConfigurationImpl.BEAN_NAME);
        underTest.initBean();
        assertThrows(IllegalStateException.class, underTest::iterator);
    }

    @Override
    public void configureApplication(final ApplicationConfigDecorator decorator) {
        decorator.registerNavigationCase(FALLBACK_OUTCOME, CURRENT_VIEW_XHTML);
    }

    @Override
    public HistoryManagerBean getUnderTest() {
        return new HistoryManagerBean();
    }
}
