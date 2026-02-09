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
 * <h2>Accessor Pattern for Transient JSF Resources</h2>
 * <p>
 * This package provides implementations of the accessor pattern which addresses common challenges
 * in JSF applications related to accessing transient resources while maintaining proper serialization.
 * 
 * <h3>Core Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.common.accessor.ManagedAccessor}: The core interface defining
 *       the accessor pattern for serialization-safe access to transient resources</li>
 *   <li>{@link de.cuioss.jsf.api.common.accessor.ConverterAccessor}: Specialized accessor for
 *       resolving and accessing JSF {@link jakarta.faces.convert.Converter} instances based
 *       on configurable criteria</li>
 *   <li>{@link de.cuioss.jsf.api.common.accessor.LocaleAccessor}: Accessor for determining
 *       the current user's locale in a JSF application</li>
 *   <li>{@link de.cuioss.jsf.api.common.accessor.CuiProjectStageAccessor}: Accessor for
 *       determining the current project stage (development, test, production)</li>
 * </ul>
 * 
 * <h3>Key Benefits</h3>
 * <p>
 * The accessor pattern provides several advantages for JSF development:
 * <ul>
 *   <li>Safe implementation of the {@link java.io.Serializable} / transient contract</li>
 *   <li>Implicit lazy loading of resources</li>
 *   <li>Encapsulation of the loading mechanics</li>
 *   <li>Unified approach to accessing common JSF resources</li>
 *   <li>Simplified recovery after deserialization in JSF view state</li>
 * </ul>
 *
 * <h3>Usage Guidelines</h3>
 * <p>
 * When implementing custom accessors:
 * <ul>
 *   <li>Wrap transient resources as transient fields</li>
 *   <li>Implement proper lazy initialization logic</li>
 *   <li>Consider serialization and deserialization scenarios</li>
 *   <li>Document any specific lifecycle constraints (e.g., request-scoped usage)</li>
 * </ul>
 * 
 * <h3>Common Usage Examples</h3>
 * <pre>
 * // Accessing a converter
 * ConverterAccessor&lt;MyType&gt; converter = new ConverterAccessor&lt;&gt;();
 * converter.setTargetClass(MyType.class);
 * 
 * // Accessing the current locale
 * LocaleAccessor localeAccessor = new LocaleAccessor();
 * Locale userLocale = localeAccessor.getValue();
 * 
 * // Checking project stage
 * CuiProjectStageAccessor projectStage = new CuiProjectStageAccessor();
 * if (projectStage.getValue().isDevelopment()) {
 *     // Development-specific behavior
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.common.accessor;
