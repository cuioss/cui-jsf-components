package com.icw.ehf.cui.core.api.application.bundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.MissingResourceException;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.junit5.AbstractPropertyAwareFacesTest;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@JsfTestConfiguration(TestBundleConfigurator.class)
@PropertyReflectionConfig(skip = true)
@PropertyGenerator(TestBundleConfigurator.class)
@PropertyConfig(name = "resourceBundleWrapper", propertyClass = ResourceBundleWrapper.class)
@EnableJsfEnvironment(useIdentityResourceBundle = false)
class CuiResourceBundleTest extends AbstractPropertyAwareFacesTest<CuiResourceBundle> {

    @Test
    void testGetString() {
        var cuiResourceBundle = anyBean();
        assertEquals("bundle1.value1", cuiResourceBundle.getString("bundle1.property1"));
        assertEquals("bundle2.value1", cuiResourceBundle.getString("bundle2.property1"));
        assertEquals("From property1", cuiResourceBundle.getString("common.property"));
    }

    private CuiResourceBundle anyBean() {
        var cuiResourceBundle = new CuiResourceBundle();
        cuiResourceBundle.setResourceBundleWrapper(new TestBundleConfigurator().next());
        return cuiResourceBundle;
    }

    @Test
    void shouldReturnKeys() {
        assertTrue(anyBean().getKeys().hasMoreElements());
    }

    @Test
    void shouldBeProvidedByAccessor() {
        getBeanConfigDecorator().register(anyBean(), CuiResourceBundle.BEAN_NAME);
    }

    @Test
    void shouldFailOnInvalidKey() {
        assertThrows(MissingResourceException.class, () -> {
            anyBean().getString("not.there");
        });
    }

}
