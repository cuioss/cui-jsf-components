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
package de.cuioss.jsf.api.application.view.matcher;

import de.cuioss.jsf.api.common.view.ViewDescriptor;

import java.io.Serializable;

/**
 * View matchers are used to check whether a given view matches to given set of
 * parameters that are usually configured for the application.
 *
 * @author Oliver Wolff
 */
public interface ViewMatcher extends Serializable {

    /**
     * Checks whether a given view matches to given set of parameters that are
     * usually configured for the application.
     *
     * @param viewDescriptor to be matched. Must not be null
     * @return whether the given view matches.
     */
    boolean match(ViewDescriptor viewDescriptor);
}
