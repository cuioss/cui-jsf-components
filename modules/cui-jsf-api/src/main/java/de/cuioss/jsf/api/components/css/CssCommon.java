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
package de.cuioss.jsf.api.components.css;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents common styleClasses that are not specific to a concrete library
 * like bootstrap.
 *
 * @author Oliver Wolff
 *
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CssCommon implements StyleClassProvider {

    /** General disabled class. */
    DISABLED("disabled"),
    /** Shorthand for Bootstrap-style float:right. */
    PULL_RIGHT("pull-right"),
    /** Shorthand for Bootstrap-style float:left. */
    PULL_LEFT("pull-left");

    private final String styleClass;

}
