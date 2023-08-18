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
package de.cuioss.jsf.api.application.theme;

/**
 * Producer for a concrete theme-name, separating the concerns of accessing and
 * storing a concrete theme.
 *
 * @author Oliver Wolff
 */
public interface ThemeNameProducer {

    /**
     * Access the user persisted theme
     *
     * @return Either the persisted theme or the given default theme. The default
     *         theme must be provided by the implementation.
     */
    String getTheme();

}
