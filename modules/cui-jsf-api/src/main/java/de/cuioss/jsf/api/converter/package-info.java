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
 * <h2>Overview</h2>
 * <p>
 * Provides a collection of JSF converter implementations and base classes for creating custom
 * converters. This package focuses on reducing boilerplate code, providing consistent error 
 * handling, and ensuring proper implementation of JSF converter contracts.
 * </p>
 * 
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.converter.AbstractConverter} - Base class for all custom converters,
 *       providing error handling and common functionality</li>
 *   <li>{@link de.cuioss.jsf.api.converter.ObjectToStringConverter} - Simple converter for objects 
 *       using their toString() implementation</li>
 *   <li>{@link de.cuioss.jsf.api.converter.StringIdentConverter} - Identity converter for strings</li>
 *   <li>{@link de.cuioss.jsf.api.converter.HtmlSanitizingConverter} - Converter for sanitizing HTML input</li>
 *   <li>{@link de.cuioss.jsf.api.converter.PlainTextSanitizerConverter} - Converter for sanitizing plain text input</li>
 *   <li>{@link de.cuioss.jsf.api.converter.FallbackSanitizingConverter} - Converter with sanitizing fallback mechanism</li>
 *   <li>{@link de.cuioss.jsf.api.converter.ComplexHtmlSanitizingConverter} - Advanced HTML sanitizing converter</li>
 * </ul>
 * 
 * <h2>Sub-Packages</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.converter.nameprovider} - Specialized converters for display name handling</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * 
 * <h3>Creating a Custom Converter</h3>
 * <pre>
 * public class DateConverter extends AbstractConverter&lt;Date&gt; {
 * 
 *     private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 *     
 *     &#64;Override
 *     protected Date convertToObject(String value, UIComponent component) {
 *         try {
 *             return dateFormat.parse(value);
 *         } catch (ParseException e) {
 *             throw new ConverterException("Invalid date format");
 *         }
 *     }
 *     
 *     &#64;Override
 *     protected String convertToString(Date value, UIComponent component) {
 *         return dateFormat.format(value);
 *     }
 * }
 * </pre>
 * 
 * <h3>Using the Sanitizing Converters</h3>
 * <pre>
 * // Basic HTML sanitization
 * HtmlSanitizingConverter htmlConverter = new HtmlSanitizingConverter();
 * String sanitizedHtml = htmlConverter.getAsObject(context, component, inputValue);
 * 
 * // Plain text sanitization
 * PlainTextSanitizerConverter textConverter = new PlainTextSanitizerConverter();
 * String sanitizedText = textConverter.getAsObject(context, component, inputValue);
 * </pre>
 * 
 * <h2>Best Practices</h2>
 * <ul>
 *   <li>Always extend {@link de.cuioss.jsf.api.converter.AbstractConverter} for custom converters</li>
 *   <li>Use appropriate sanitizing converters when handling user input to prevent XSS attacks</li>
 *   <li>Implement proper error handling in the convert methods</li>
 *   <li>Use {@link de.cuioss.jsf.api.application.message.MessageProducer} for consistent error messages</li>
 * </ul>
 * 
 * <h2>Related Packages</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.validator} - Validation components</li>
 *   <li>{@link de.cuioss.jsf.api.security} - Security-related components</li>
 *   <li>{@link de.cuioss.jsf.api.application.message} - Message handling</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.converter;
