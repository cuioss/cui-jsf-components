package de.cuioss.jsf.test.mock.application;

import java.io.Serializable;
import java.util.Locale;

import de.cuioss.jsf.api.application.locale.LocaleProducer;
import de.cuioss.jsf.api.application.locale.LocaleProducerImpl;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * Mock implementation of {@link LocaleProducer}
 * It can be easily configured as bean by using {@link JsfTestConfiguration}
 *
 * @author Matthias Walliczek
 */
@EqualsAndHashCode
@ToString
public class LocaleProducerMock implements LocaleProducer, BeanConfigurator, Serializable {

    private static final long serialVersionUID = 2728554180387920399L;

    @Setter
    private Locale locale = Locale.ENGLISH;

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void configureBeans(final BeanConfigDecorator decorator) {
        decorator.register(this, LocaleProducerImpl.BEAN_NAME);

    }
}
