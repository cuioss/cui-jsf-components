package com.icw.ehf.cui.core.api.application.locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.defaults.BasicApplicationConfiguration;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@JsfTestConfiguration(BasicApplicationConfiguration.class)
@PropertyReflectionConfig(skip = true)
class LocaleProducerImplTest extends JsfEnabledTestEnvironment {

    @Test
    void shouldBeProvidedByAccessor() {
        new BeanConfigDecorator(getFacesContext()).register(new LocaleProducerImpl(), LocaleProducerImpl.BEAN_NAME);
        assertNotNull(new LocaleProducerAccessor().getValue());
    }

    @Test
    void shouldProvideLocale() {
        var producer = new LocaleProducerImpl();
        producer.initBean();
        assertEquals(Locale.ENGLISH, producer.getLocale());
    }

    public LocaleProducerImpl anyBean() {
        var any = new LocaleProducerImpl();
        any.initBean();
        return any;
    }
}
