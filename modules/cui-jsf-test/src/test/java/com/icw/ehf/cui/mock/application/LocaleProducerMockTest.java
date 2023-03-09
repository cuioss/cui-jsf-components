package com.icw.ehf.cui.mock.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.application.locale.LocaleProducer;
import com.icw.ehf.cui.core.api.application.locale.LocaleProducerImpl;

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
