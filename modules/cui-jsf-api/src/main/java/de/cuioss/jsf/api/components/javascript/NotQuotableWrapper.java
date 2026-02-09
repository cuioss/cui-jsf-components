/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.javascript;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * Wrapper class that indicates a value should not be quoted in JavaScript code generation.
 * <p>
 * This class works in conjunction with {@link JavaScriptOptions} and other JavaScript
 * code generation utilities. By default, most JavaScript generators will automatically 
 * wrap string values in quotes. This wrapper signals that the contained value should be 
 * used as-is without additional quoting.
 * </p>
 * <p>
 * This is particularly useful for:
 * </p>
 * <ul>
 *   <li>JavaScript literals like objects, arrays, and functions</li>
 *   <li>JavaScript variables and expressions</li>
 *   <li>Pre-formatted JavaScript code segments</li>
 * </ul>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Without wrapper - result would be: {callback:'function() { alert("clicked"); }'}
 * options.withOption("callback", "function() { alert(\"clicked\"); }");
 * 
 * // With wrapper - correctly results in: {callback:function() { alert("clicked"); }}
 * options.withOption("callback", new NotQuotableWrapper("function() { alert(\"clicked\"); }"));
 * </pre>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see JavaScriptOptions
 */
@RequiredArgsConstructor
@ToString
public class NotQuotableWrapper implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = -6239824476027054699L;

    /**
     * The value that should not be quoted in JavaScript output.
     * <p>
     * This value will be used as-is in JavaScript code generation, without
     * being wrapped in quotes. It's the responsibility of the caller to ensure
     * the value represents valid JavaScript code.
     * </p>
     */
    @Getter
    private final Serializable value;

}
