package com.icw.ehf.cui.application.resources.accessor;

import com.icw.ehf.cui.application.resources.CuiResourceManager;
import com.icw.ehf.cui.core.api.common.accessor.ManagedBeanAccessor;

/**
 * @author Oliver Wolff
 *
 */
public class CuiResourceManagerAccessor extends
        ManagedBeanAccessor<CuiResourceManager> {

    private static final long serialVersionUID = -4061081985792532561L;

    /**
     * Constructor.
     */
    public CuiResourceManagerAccessor() {
        super(CuiResourceManager.BEAN_NAME, CuiResourceManager.class, false);
    }

}
