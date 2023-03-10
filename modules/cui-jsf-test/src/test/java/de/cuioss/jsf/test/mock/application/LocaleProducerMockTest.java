package de.cuioss.jsf.test.mock.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.locale.LocaleProducer;
import de.cuioss.jsf.api.application.locale.LocaleProducerImpl;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.junit5.AbstractBeanTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBeanProperty;

@VerifyBeanProperty(defaultValued = "locale")
class LocaleProducerMockTest extends AbstractBeanTest<LocaleProducerMock> {

    @Test
    void shouldRegisterAsBean() {
        assertNull(BeanConfigDecorator.getBean(LocaleProducerImpl.BEAN_NAME, getFacesContext(), LocaleProducer.class));
        new LocaleProducerMock().configureBeans(getBeanConfigDecorator());
        assertNotNull(
                BeanConfigDecorator.getBean(LocaleProducerImpl.BEAN_NAME, getFacesContext(), LocaleProducer.class));
    }
}
