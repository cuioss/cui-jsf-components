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
        assertDoesNotThrow(() -> underTest.initBean());
    }

    @Test
    void shouldHandleFallBackView() {
        var underTest = new HistoryManagerBean();
        var historyConfiguration = new HistoryConfigurationImpl();
        historyConfiguration.setFallback(CURRENT_VIEW_XHTML);
        getBeanConfigDecorator().register(historyConfiguration, HistoryConfigurationImpl.BEAN_NAME);
        assertDoesNotThrow(() -> underTest.initBean());
    }

    @Test
    void shouldFailOnIncompleteConfigurationForNavigationCase() {
        var underTest = new HistoryManagerBean();
        var historyConfiguration = new HistoryConfigurationImpl();
        historyConfiguration.setFallbackOutcome(FALLBACK_OUTCOME);
        getBeanConfigDecorator().register(historyConfiguration, HistoryConfigurationImpl.BEAN_NAME);
        getApplicationConfigDecorator().getMockNavigationHandler().getNavigationCases().clear();
        underTest.initBean();
        assertThrows(IllegalStateException.class, () -> underTest.iterator());

    }

    @Test
    void shouldFailOnInvalidConfiguration() {
        var underTest = new HistoryManagerBean();
        getBeanConfigDecorator().register(new HistoryConfigurationImpl(), HistoryConfigurationImpl.BEAN_NAME);
        underTest.initBean();
        assertThrows(IllegalStateException.class, () -> {
            underTest.iterator();
        });
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
