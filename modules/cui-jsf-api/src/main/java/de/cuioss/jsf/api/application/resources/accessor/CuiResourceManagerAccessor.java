package de.cuioss.jsf.api.application.resources.accessor;

import de.cuioss.jsf.api.application.resources.CuiResourceManager;
import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

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
