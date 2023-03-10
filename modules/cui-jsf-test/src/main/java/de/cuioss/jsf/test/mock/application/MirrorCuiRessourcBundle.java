package de.cuioss.jsf.test.mock.application;

import static java.util.Collections.emptyEnumeration;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;

import de.cuioss.jsf.api.application.bundle.CuiResourceBundle;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Identity version of {@link CuiResourceBundle}. It will always return the key
 * It can be easily configured as bean by using {@link JsfTestConfiguration}
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class MirrorCuiRessourcBundle extends ResourceBundle implements BeanConfigurator, Serializable {

    private static final long serialVersionUID = 7035144185855294606L;

    @Override
    protected Object handleGetObject(final String key) {
        return key;
    }

    @Override
    public Enumeration<String> getKeys() {
        return emptyEnumeration();
    }

    @Override
    public void configureBeans(final BeanConfigDecorator decorator) {
        decorator.register(this, CuiResourceBundle.BEAN_NAME);
    }

}
