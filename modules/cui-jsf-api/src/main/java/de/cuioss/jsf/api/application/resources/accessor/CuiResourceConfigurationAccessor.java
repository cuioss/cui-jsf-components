package de.cuioss.jsf.api.application.resources.accessor;

import de.cuioss.jsf.api.application.resources.CuiResourceConfiguration;
import de.cuioss.jsf.api.application.resources.impl.CuiResourceConfigurationImpl;
import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

/**
 * @author Oliver Wolff
 *
 */
public class CuiResourceConfigurationAccessor extends
        ManagedBeanAccessor<CuiResourceConfiguration> {

    private static final long serialVersionUID = 1800107762288234081L;

    /**
     * Constructor.
     */
    public CuiResourceConfigurationAccessor() {
        super(CuiResourceConfigurationImpl.BEAN_NAME,
                CuiResourceConfiguration.class, false);
    }

}
