package de.cuioss.jsf.api.application.history.accessor;

import de.cuioss.jsf.api.application.history.HistoryConfiguration;
import de.cuioss.jsf.api.application.history.impl.HistoryConfigurationImpl;
import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

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
