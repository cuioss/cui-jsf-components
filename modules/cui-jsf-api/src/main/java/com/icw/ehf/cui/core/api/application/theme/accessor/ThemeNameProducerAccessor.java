package com.icw.ehf.cui.core.api.application.theme.accessor;

import com.icw.ehf.cui.core.api.application.theme.ThemeNameProducer;
import com.icw.ehf.cui.core.api.application.theme.impl.ThemeNameProducerImpl;
import com.icw.ehf.cui.core.api.common.accessor.ManagedBeanAccessor;

/**
 * Accesses instances of {@link ThemeNameProducer}
 *
 * @author Oliver Wolff
 */
public class ThemeNameProducerAccessor extends ManagedBeanAccessor<ThemeNameProducer> {

    private static final long serialVersionUID = 3261175635684872539L;

    /**
     * Constructor.
     */
    public ThemeNameProducerAccessor() {
        super(ThemeNameProducerImpl.BEAN_NAME, ThemeNameProducer.class, true);
    }
}
