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
package de.cuioss.jsf.api.application.resources.impl;

import java.util.List;

import de.cuioss.jsf.api.application.resources.CuiResourceConfiguration;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Simple default implementation for {@link CuiResourceConfiguration} to be
 * managed completely within facesConfig
 *
 * @author Oliver Wolff
 *
 */
@EqualsAndHashCode
@ToString
public class CuiResourceConfigurationImpl implements CuiResourceConfiguration {

    private static final long serialVersionUID = -6989079897199012765L;

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "cuiResourceConfiguration";

    @Getter
    @Setter
    private String version;

    @Getter
    @Setter
    private List<String> handledLibraries;

    @Getter
    @Setter
    private List<String> handledSuffixes;
}
