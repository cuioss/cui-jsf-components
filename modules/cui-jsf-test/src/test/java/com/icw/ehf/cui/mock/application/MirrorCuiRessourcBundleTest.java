package com.icw.ehf.cui.mock.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundle;

import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.junit5.AbstractBeanTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(skip = true)
class MirrorCuiRessourcBundleTest extends AbstractBeanTest<MirrorCuiRessourcBundle> {

    @Test
    void shouldRegisterAsBean() {
        assertNull(BeanConfigDecorator.getBean(CuiResourceBundle.BEAN_NAME, getFacesContext(), ResourceBundle.class));
        new MirrorCuiRessourcBundle().configureBeans(getBeanConfigDecorator());
        assertNotNull(
                BeanConfigDecorator.getBean(CuiResourceBundle.BEAN_NAME, getFacesContext(), ResourceBundle.class));
    }

    @Test
    void resourceBundleContainsKey() {
        assertFalse(new MirrorCuiRessourcBundle().containsKey("something"));
    }

    @Test
    void shouldMirrorKey() {
        assertEquals("123", anyBean().getString("123"));
    }
}
