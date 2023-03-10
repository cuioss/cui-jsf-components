package de.cuioss.jsf.api.application.theme.accessor;

import de.cuioss.jsf.api.application.theme.ThemeConfiguration;
import de.cuioss.jsf.api.application.theme.impl.ThemeConfigurationImpl;
import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

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
