package com.icw.ehf.cui.mock.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.application.theme.ThemeConfiguration;
import com.icw.ehf.cui.core.api.application.theme.impl.ThemeConfigurationImpl;

import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.junit5.AbstractBeanTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(
        defaultValued = { "availableThemes", "defaultTheme", "cssName", "cssLibrary", "useMinVersion" })
class ThemeConfigurationMockTest extends AbstractBeanTest<ThemeConfigurationMock> {

    @Test
    void shouldRegisterAsBean() {
        assertNull(BeanConfigDecorator.getBean(ThemeConfigurationImpl.BEAN_NAME, getFacesContext(),
                ThemeConfiguration.class));
        new ThemeConfigurationMock().configureBeans(getBeanConfigDecorator());
        assertNotNull(BeanConfigDecorator.getBean(ThemeConfigurationImpl.BEAN_NAME, getFacesContext(),
                ThemeConfiguration.class));
    }
}
