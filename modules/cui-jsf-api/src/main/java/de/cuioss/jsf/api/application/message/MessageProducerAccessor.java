package de.cuioss.jsf.api.application.message;

import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

/**
 * Helper class for accessing instances of {@link MessageProducer} within objects that are not under
 * control of the MangedBeanFacility, e.g. Converter, validators, components. Managed Beans should
 * use the managed property facility.
 *
 * @author Oliver Wolff
 */
public class MessageProducerAccessor extends ManagedBeanAccessor<MessageProducer> {

    private static final long serialVersionUID = -8226816546645652258L;

    /**
     * Constructor.
     */
    public MessageProducerAccessor() {
        super(MessageProducerImpl.BEAN_NAME, MessageProducer.class, true);
    }

}
