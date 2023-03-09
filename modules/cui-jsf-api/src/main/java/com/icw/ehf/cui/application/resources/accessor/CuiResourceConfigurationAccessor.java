package com.icw.ehf.cui.application.resources.accessor;

import com.icw.ehf.cui.application.resources.CuiResourceConfiguration;
import com.icw.ehf.cui.application.resources.impl.CuiResourceConfigurationImpl;
import com.icw.ehf.cui.core.api.common.accessor.ManagedBeanAccessor;

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
