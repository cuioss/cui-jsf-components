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
package de.cuioss.jsf.api.common.accessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.defaults.BasicApplicationConfiguration;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@JsfTestConfiguration(BasicApplicationConfiguration.class)
@PropertyReflectionConfig(skip = true)
class LocaleAccessorTest extends JsfEnabledTestEnvironment {

    @Test
    void shouldProvideLocale() {
        var producer = new LocaleAccessor();
        assertEquals(Locale.ENGLISH, producer.getValue());
        // Access cached Value
        assertEquals(Locale.ENGLISH, producer.getValue());
    }

}
