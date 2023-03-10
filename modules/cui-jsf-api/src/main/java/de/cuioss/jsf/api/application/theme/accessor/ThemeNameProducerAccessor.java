package de.cuioss.jsf.api.application.theme.accessor;

import de.cuioss.jsf.api.application.theme.ThemeNameProducer;
import de.cuioss.jsf.api.application.theme.impl.ThemeNameProducerImpl;
import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

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
