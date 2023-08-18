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
package de.cuioss.jsf.api.application.theme.impl;

import java.io.Serializable;

import de.cuioss.jsf.api.application.theme.ThemeConfiguration;
import de.cuioss.jsf.api.application.theme.ThemeNameProducer;
import de.cuioss.jsf.api.application.theme.accessor.ThemeConfigurationAccessor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Simple implementation of {@link ThemeNameProducer} that will always return
 * the default theme derived from {@link ThemeConfiguration}
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class ThemeNameProducerImpl implements ThemeNameProducer, Serializable {

    private static final long serialVersionUID = -8491235324464722495L;

    private final ThemeConfigurationAccessor themeConfiguration = new ThemeConfigurationAccessor();

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "themeNameProducer";

    @Override
    public String getTheme() {
        return themeConfiguration.getValue().getDefaultTheme();
    }

}
