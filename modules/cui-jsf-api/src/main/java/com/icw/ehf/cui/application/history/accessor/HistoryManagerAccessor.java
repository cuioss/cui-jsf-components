package com.icw.ehf.cui.application.history.accessor;

import com.icw.ehf.cui.application.history.HistoryManager;
import com.icw.ehf.cui.application.history.impl.HistoryManagerImpl;
import com.icw.ehf.cui.core.api.common.accessor.ManagedBeanAccessor;

/**
 * Helper class for accessing instances of {@link HistoryManager} within objects that are not under
 * control of the MangedBeanFacility, e.g. Converter, validators, components.
 *
 * @author Oliver Wolff
 */
public class HistoryManagerAccessor extends ManagedBeanAccessor<HistoryManager> {

    private static final long serialVersionUID = 654595256504186362L;

    /**
     * Constructor
     */
    public HistoryManagerAccessor() {
        super(HistoryManagerImpl.BEAN_NAME, HistoryManager.class, true);
    }

}
