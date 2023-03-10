package de.cuioss.jsf.test.mock.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.theme.ThemeNameProducer;
import de.cuioss.jsf.api.application.theme.impl.ThemeNameProducerImpl;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.junit5.AbstractBeanTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(defaultValued = { "theme" })
class ThemeNameProducerMockTest extends AbstractBeanTest<ThemeNameProducerMock> {

    @Test
    void shouldRegisterAsBean() {
        assertNull(BeanConfigDecorator.getBean(ThemeNameProducerImpl.BEAN_NAME, getFacesContext(),
                ThemeNameProducer.class));
        new ThemeNameProducerMock().configureBeans(getBeanConfigDecorator());
        assertNotNull(BeanConfigDecorator.getBean(ThemeNameProducerImpl.BEAN_NAME, getFacesContext(),
                ThemeNameProducer.class));
    }
}
