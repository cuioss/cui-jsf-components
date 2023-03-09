package com.icw.ehf.cui.core.api.application.theme.accessor;

import com.icw.ehf.cui.core.api.application.theme.ThemeConfiguration;
import com.icw.ehf.cui.core.api.application.theme.impl.ThemeConfigurationImpl;
import com.icw.ehf.cui.core.api.common.accessor.ManagedBeanAccessor;

/**
 * Accesses instances of {@link ThemeConfiguration}
 *
 * @author Oliver Wolff
 */
public class ThemeConfigurationAccessor extends ManagedBeanAccessor<ThemeConfiguration> {

    private static final long serialVersionUID = 806728698655588358L;

    /**
     * Constructor.
     */
    public ThemeConfigurationAccessor() {
        super(ThemeConfigurationImpl.BEAN_NAME, ThemeConfiguration.class, false);
    }
}
