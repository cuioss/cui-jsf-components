package com.icw.ehf.cui.application.history.accessor;

import com.icw.ehf.cui.application.history.HistoryConfiguration;
import com.icw.ehf.cui.application.history.impl.HistoryConfigurationImpl;
import com.icw.ehf.cui.core.api.common.accessor.ManagedBeanAccessor;

/**
 * Accesses instances of {@link HistoryConfiguration}
 *
 * @author Oliver Wolff
 */
public class HistoryConfigurationAccessor extends ManagedBeanAccessor<HistoryConfiguration> {

    private static final long serialVersionUID = 612034267461384704L;

    /**
     * Constructor.
     */
    public HistoryConfigurationAccessor() {
        super(HistoryConfigurationImpl.BEAN_NAME, HistoryConfiguration.class, false);
    }

}
