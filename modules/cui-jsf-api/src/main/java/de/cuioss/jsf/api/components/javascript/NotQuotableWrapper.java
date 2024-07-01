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
package de.cuioss.jsf.api.components.javascript;

import java.io.Serial;
import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Helper class to be used in conjunction with {@link JavaScriptOptions}. The
 * problem: Each value will automatically put into double quotes. If you wrap
 * the corresponding element in an instance of this type it will be taken
 * directly and not quoted.
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor
@ToString
public class NotQuotableWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = -6239824476027054699L;

    @Getter
    private final Serializable value;

}
