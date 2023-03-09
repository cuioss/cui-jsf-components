package com.icw.ehf.cui.application.history.impl;

import javax.annotation.PostConstruct;

import com.icw.ehf.cui.application.history.HistoryManager;
import com.icw.ehf.cui.application.history.accessor.HistoryConfigurationAccessor;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * Bean keeping track of the view history. For configuration see package-info The implementation
 * utilizes a stack to store the history. The actual work is done by {@link HistoryManagerImpl}
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(of = "delegate")
@ToString(of = "delegate")
public class HistoryManagerBean implements HistoryManager {

    private static final long serialVersionUID = 2205593126500409010L;

    private final HistoryConfigurationAccessor configurationAccessor =
        new HistoryConfigurationAccessor();

    @Delegate(types = HistoryManager.class)
    private HistoryManager delegate;

    /**
     * Initializes the bean. See class documentation for expected result.
     */
    @PostConstruct
    public void initBean() {
        final var configuration = configurationAccessor.getValue();
        delegate = new HistoryManagerImpl(configuration);
    }

}
