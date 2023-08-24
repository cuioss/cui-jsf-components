/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.application.bundle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
        assertDoesNotThrow(() -> getBeanConfigDecorator().register(anyBean(), CuiResourceBundle.BEAN_NAME));
    }

    @Test
    void shouldFailOnInvalidKey() {
        var bean = anyBean();
        assertThrows(MissingResourceException.class, () -> bean.getString("not.there"));
    }

}
