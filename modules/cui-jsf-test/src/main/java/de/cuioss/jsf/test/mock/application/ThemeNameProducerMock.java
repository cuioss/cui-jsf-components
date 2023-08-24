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

import java.io.Serializable;

import de.cuioss.jsf.api.application.theme.ThemeNameProducer;
import de.cuioss.jsf.api.application.theme.impl.ThemeNameProducerImpl;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Mock implementation of {@link ThemeNameProducer} that default to
 * {@link ThemeConfigurationMock#DEFAULT_THEME} It can be easily configured as
 * bean by using {@link JsfTestConfiguration}
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class ThemeNameProducerMock implements ThemeNameProducer, Serializable, BeanConfigurator {

    private static final long serialVersionUID = -1951944023763474080L;

    @Getter
    @Setter
    private String theme = ThemeConfigurationMock.DEFAULT_THEME;

    @Override
    public void configureBeans(final BeanConfigDecorator decorator) {
        decorator.register(this, ThemeNameProducerImpl.BEAN_NAME);
    }

}
