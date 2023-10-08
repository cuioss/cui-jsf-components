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
package de.cuioss.jsf.components.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.test.jsf.defaults.BasicApplicationConfiguration;

@JsfTestConfiguration(BasicApplicationConfiguration.class)
class PrettyTimeConverterTest extends AbstractConverterTest<PrettyTimeConverter, Object> {

    private static final String MOMENTS_AGO = "moments ago";

    @Override
    public void populate(final TestItems<Object> testItems) {
        testItems.addValidObjectWithStringResult(new Date(System.currentTimeMillis() - 10000), MOMENTS_AGO)
                .addInvalidObject(2).addValidObjectWithStringResult(Calendar.getInstance(), MOMENTS_AGO)
                .addValidObject(LocalDate.now()).addValidObject(ZonedDateTime.now())
                .addValidObjectWithStringResult(LocalDateTime.now().minusSeconds(10), MOMENTS_AGO);
    }

}
