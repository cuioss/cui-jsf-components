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
package de.cuioss.jsf.api.application.locale;

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
