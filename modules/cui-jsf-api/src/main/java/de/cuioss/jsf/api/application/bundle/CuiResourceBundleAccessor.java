package de.cuioss.jsf.api.application.bundle;

import java.util.ResourceBundle;

import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

/**
 * Accesses instances of {@link CuiResourceBundle}.
 *
 * @author Oliver Wolff
 */
public class CuiResourceBundleAccessor extends ManagedBeanAccessor<ResourceBundle> {

    private static final long serialVersionUID = -2944602525380062391L;

    /**
     * Constructor.
     */
    public CuiResourceBundleAccessor() {
        super(CuiResourceBundle.BEAN_NAME, ResourceBundle.class, true);
    }

}
