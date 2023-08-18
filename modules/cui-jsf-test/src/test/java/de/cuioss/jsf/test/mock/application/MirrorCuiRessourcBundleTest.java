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
package de.cuioss.jsf.test.mock.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.bundle.CuiResourceBundle;
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
