package com.icw.ehf.cui.application.history.impl;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static de.cuioss.tools.collect.CollectionLiterals.mutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.application.view.matcher.ViewMatcherImpl;
import com.icw.ehf.cui.core.api.common.view.ViewDescriptorImpl;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class HistoryManagerImplTest extends JsfEnabledTestEnvironment {

    private static final String FIRST_NAVIGATION = "/current/view3.jsf";

    public static final String CURRENT_VIEW_XHTML = "current/view.jsf";

    public static final String FALLBACK_OUTCOME = "fallbackOutcome";

    public static final String FALLBACK_VIEW = "current/fallback.jsf";

    public static final String SECOND_VIEW_XHTML = "current/view2.jsf";

    public static final String VETO_VIEW_XHTML = "current/veto.jsf";

    private final ViewDescriptorImpl currentView =
        ViewDescriptorImpl.builder().withViewId(CURRENT_VIEW_XHTML).withLogicalViewId(CURRENT_VIEW_XHTML).build();

    private final ViewDescriptorImpl secondView =
        ViewDescriptorImpl.builder().withViewId(SECOND_VIEW_XHTML).withLogicalViewId(SECOND_VIEW_XHTML).build();

    private final ViewDescriptorImpl vetoView =
        ViewDescriptorImpl.builder().withViewId(VETO_VIEW_XHTML).withLogicalViewId(VETO_VIEW_XHTML).build();

    private HistoryConfigurationImpl historyConfiguration;

    private HistoryManagerImpl underTest;

    @BeforeEach
    void setUpBefore() {
        historyConfiguration = new HistoryConfigurationImpl();
        historyConfiguration.setExcludeFromHistoryMatcher(new ViewMatcherImpl(immutableList(VETO_VIEW_XHTML)));
        historyConfiguration.setFallbackOutcome(FALLBACK_OUTCOME);
        getApplicationConfigDecorator().registerNavigationCase(FALLBACK_OUTCOME, FALLBACK_VIEW);
        underTest = new HistoryManagerImpl(historyConfiguration);
    }

    @Test
    void shouldFailOnToLowHistorSize() {
        historyConfiguration.setHistorySize(1);
        assertThrows(IllegalStateException.class, () -> {
            new HistoryManagerImpl(historyConfiguration);
        });
    }

    @Test
    void shouldFailOnToHighHistorSize() {
        historyConfiguration.setHistorySize(100);
        assertThrows(IllegalStateException.class, () -> {
            new HistoryManagerImpl(historyConfiguration);
        });
    }

    @Test
    void shouldAddViewToHistory() {
        assertEntryCount(1, underTest);
        underTest.addCurrentUriToHistory(currentView);
        assertEntryCount(2, underTest);
        // adding the same view again should not change anything
        underTest.addCurrentUriToHistory(currentView);
        assertEntryCount(2, underTest);
        getRequestConfigDecorator().setViewId(SECOND_VIEW_XHTML);
        underTest.addCurrentUriToHistory(secondView);
        assertEntryCount(3, underTest);
    }

    @Test
    void shouldLimitStackSizeCorrectly() {
        for (var i = 0; i < 15; i++) {
            final var viewId = i + "/" + CURRENT_VIEW_XHTML;
            final var currentView =
                ViewDescriptorImpl.builder().withViewId(viewId).withLogicalViewId(viewId).build();
            underTest.addCurrentUriToHistory(currentView);
        }
        // Limit is 10 + fallback
        assertEntryCount(11, underTest);
    }

    @Test
    void shouldPopViewFromHistory() {
        assertEntryCount(1, underTest);
        underTest.addCurrentUriToHistory(currentView);
        assertEntryCount(2, underTest);
        underTest.addCurrentUriToHistory(secondView);
        assertEntryCount(3, underTest);
        final var current = underTest.popPrevious();
        assertEntryCount(2, underTest);
        assertEquals(CURRENT_VIEW_XHTML, current.getViewId());
        var fallback = underTest.popPrevious();
        assertEntryCount(1, underTest);
        assertEquals(FALLBACK_OUTCOME, fallback.getOutcome());
        // The last one should always stay
        fallback = underTest.popPrevious();
        assertEntryCount(1, underTest);
        assertEquals(FALLBACK_OUTCOME, fallback.getOutcome());
    }

    @Test
    void shouldPeekViewFromHistory() {
        assertEntryCount(1, underTest);
        underTest.addCurrentUriToHistory(currentView);
        assertEntryCount(2, underTest);
        underTest.addCurrentUriToHistory(secondView);
        assertEntryCount(3, underTest);
        var current = underTest.peekPrevious();
        assertEntryCount(3, underTest);
        assertEquals(CURRENT_VIEW_XHTML, current.getViewId());
        // peek should not change the stack
        current = underTest.peekPrevious();
        assertEntryCount(3, underTest);
        assertEquals(CURRENT_VIEW_XHTML, current.getViewId());
    }

    @Test
    void shouldVetoCorrectly() {
        assertEntryCount(1, underTest);
        underTest.addCurrentUriToHistory(vetoView);
        assertEntryCount(1, underTest);
        underTest.addCurrentUriToHistory(secondView);
        assertEntryCount(2, underTest);
    }

    @Test
    void shouldReturnFallbackIfOnDeeplinkEnteringScenario() {
        final var fallback = underTest.peekPrevious();
        assertEquals(FALLBACK_OUTCOME, fallback.getOutcome());
    }

    @Test
    void shouldOnlyAddViewToHistoryIfWasNotLast() {
        final var firstNavigation =
            ViewDescriptorImpl.builder().withViewId(FIRST_NAVIGATION).withLogicalViewId(FIRST_NAVIGATION).build();
        historyConfiguration.setHistorySize(2);
        underTest = new HistoryManagerImpl(historyConfiguration);
        underTest.addCurrentUriToHistory(firstNavigation);
        underTest.addCurrentUriToHistory(secondView);
        underTest.addCurrentUriToHistory(currentView);
        // stay on page 3 times
        underTest.addCurrentUriToHistory(currentView);
        underTest.addCurrentUriToHistory(currentView);
        underTest.addCurrentUriToHistory(currentView);
        // pop from history
        assertEquals(SECOND_VIEW_XHTML, underTest.popPrevious().getViewId());
        assertEquals(FIRST_NAVIGATION, underTest.popPrevious().getViewId());
        // is default
        assertEquals(FALLBACK_VIEW, underTest.popPrevious().getViewId());
    }

    /**
     * Verify count of iterable
     *
     * @param expected value
     * @param iterable {@link Iterable}
     */
    public static void assertEntryCount(final int expected, final Iterable<?> iterable) {
        assertEquals(expected, mutableList(iterable).size());
    }
}
