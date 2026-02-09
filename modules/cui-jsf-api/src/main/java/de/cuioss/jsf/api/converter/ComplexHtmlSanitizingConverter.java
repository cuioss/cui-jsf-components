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
package de.cuioss.jsf.api.converter;

import de.cuioss.jsf.api.security.CuiSanitizer;
import jakarta.faces.convert.FacesConverter;

/**
 * A specialized HTML sanitizing converter that allows a rich set of HTML formatting elements
 * while still protecting against XSS attacks.
 * <p>
 * This converter extends {@link HtmlSanitizingConverter} and pre-configures it to use the
 * {@link CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES} sanitization strategy, which allows
 * a wide variety of HTML formatting elements and attributes while still protecting against
 * potentially dangerous content.
 * 
 * <p>
 * This converter is ideal for rich text content that needs to preserve formatting such as:
 * <ul>
 *   <li>Formatted text from rich text editors</li>
 *   <li>Content with complex formatting needs (tables, lists, headings, etc.)</li>
 *   <li>HTML content from trusted sources that still needs sanitization</li>
 * </ul>
 * 
 * <p>
 * The converter allows HTML formatting elements like tables, lists, formatting tags, etc.
 * while still preventing script injection and other XSS vectors. It also preserves HTML
 * entities for special characters.
 * 
 * <p>
 * Usage example in JSF:
 * <pre>
 * &lt;h:outputText value="#{bean.richTextContent}" 
 *               converter="cui.converter.ComplexHtmlSanitizingConverter" 
 *               escape="false" /&gt;
 * </pre>
 * 
 * <p>
 * Note: When using this converter, it's important to set {@code escape="false"} on the
 * output component to allow the formatted HTML to render properly.
 * 
 * <p>
 * Security Note: While this converter provides strong protection against XSS attacks,
 * it allows more HTML than stricter sanitizers. Only use it for content where rich 
 * formatting is required.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES
 * @see HtmlSanitizingConverter
 */
@FacesConverter(value = "cui.converter.ComplexHtmlSanitizingConverter")
public class ComplexHtmlSanitizingConverter extends HtmlSanitizingConverter {

    /**
     * Default constructor that pre-configures the converter to use
     * {@link CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES} strategy.
     * <p>
     * This sanitizer allows a rich set of HTML elements and attributes while still
     * preventing XSS attacks. It also preserves HTML entities.
     * </p>
     */
    public ComplexHtmlSanitizingConverter() {
        setSanitizer(CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES);
    }
}
