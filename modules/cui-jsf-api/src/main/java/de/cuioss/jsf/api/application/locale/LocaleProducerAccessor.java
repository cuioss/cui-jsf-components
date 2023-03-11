package de.cuioss.jsf.api.application.locale;

import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

/**
 * Class for lookup managed bean which implements {@linkplain LocaleProducer} interface. The implementation assumes an
 * instance of {@link LocaleProducer} being present under the name of {@link LocaleProducerImpl#BEAN_NAME}
 *
 * @author Eugen Fischer
 * @deprecated ManagedBeans are replaced by CDI. Use {@code PortalBeanManager.resolveBeanOrThrowIllegalStateException(Locale.class,
 * PortalLocale.class)}; instead
 */
@Deprecated
public class LocaleProducerAccessor extends ManagedBeanAccessor<LocaleProducer> {

    private static final long serialVersionUID = -7372535413254248257L;

    /**
     * Constructor.
     */
    public LocaleProducerAccessor() {
        super(LocaleProducerImpl.BEAN_NAME, LocaleProducer.class, true);
    }

}
