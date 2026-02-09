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
 * Provides security related classes focused on preventing common web application vulnerabilities.
 * <p>
 * This package contains utilities and classes for ensuring application security,
 * particularly focusing on:
 * </p>
 * <ul>
 *   <li>HTML content sanitizing to prevent Cross-Site Scripting (XSS) attacks</li>
 *   <li>Email address validation and sanitization</li>
 *   <li>Input validation and content filtering</li>
 * </ul>
 * <p>
 * The primary components in this package are:
 * </p>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.security.CuiSanitizer} - An enum providing various HTML sanitizing 
 *       strategies with different levels of allowed HTML elements</li>
 *   <li>{@link de.cuioss.jsf.api.security.SanitizedIDNInternetAddress} - Support for internationalized 
 *       domain names in email addresses with proper sanitization</li>
 * </ul>
 * <p>
 * Usage examples:
 * </p>
 * <pre>
 * // Sanitize user input to plain text
 * String sanitizedText = CuiSanitizer.PLAIN_TEXT.apply(userInput);
 * 
 * // Create a sanitized email address
 * SanitizedIDNInternetAddress emailAddress = new SanitizedIDNInternetAddress("user@example.com");
 * </pre>
 *
 * @author Oliver Wolff
 */
package de.cuioss.jsf.api.security;
