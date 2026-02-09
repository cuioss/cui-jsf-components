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
/**
 * <h2>JavaScript Integration Package</h2>
 * <p>
 * Provides utilities and builders for generating and managing JavaScript code in JSF components.
 * This package enables seamless integration of client-side JavaScript with server-side Java code,
 * including proper escaping, formatting, and structure.
 * </p>
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.javascript.JavaScriptOptions} - Builder for JavaScript object literals</li>
 *   <li>{@link de.cuioss.jsf.api.components.javascript.NotQuotableWrapper} - Wrapper for JavaScript values that shouldn't be quoted</li>
 *   <li>{@link de.cuioss.jsf.api.components.javascript.ScriptProvider} - Interface for objects that can provide JavaScript code</li>
 *   <li>{@link de.cuioss.jsf.api.components.javascript.ComponentWrapperJQuerySelector} - jQuery selectors for components</li>
 *   <li>{@link de.cuioss.jsf.api.components.javascript.JQuerySelector} - General jQuery selector builder</li>
 * </ul>
 * 
 * <h3>Common Use Cases</h3>
 * <ul>
 *   <li>Creating JavaScript configuration objects for plugins and widgets</li>
 *   <li>Building jQuery selectors for component references</li>
 *   <li>Generating client-side JavaScript functions</li>
 *   <li>Managing JavaScript events and callbacks</li>
 * </ul>
 * 
 * <h3>Integration with JSF</h3>
 * <p>
 * The utilities in this package are designed to work seamlessly with JSF's client-side 
 * behavior and JavaScript generation mechanisms. They handle proper escaping and formatting
 * to ensure generated JavaScript is valid and secure.
 * </p>
 * 
 * <p>
 * Classes in this package are thread-safe for read operations when documented as such.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.components.javascript;
