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
package de.cuioss.jsf.components.converter;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * <p>JSF converter that transforms plain text with newline characters into HTML-formatted
 * text with appropriate HTML line break elements (e.g., {@code <br>} tags). This converter
 * is particularly useful for displaying multi-line text from text areas or similar input
 * fields in HTML contexts where newlines are not normally preserved.</p>
 * 
 * <p>The converter performs two important functions:</p>
 * <ol>
 *   <li>It sanitizes the input text to prevent XSS attacks and ensure HTML validity</li>
 *   <li>It converts newline characters to configurable HTML line break elements</li>
 * </ol>
 * 
 * <p>The sanitization strategy can be configured through the {@code sanitizingStrategy}
 * attribute. By default, it uses {@link CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES},
 * which escapes HTML but preserves entities.</p>
 * 
 * <p>The HTML line break delimiter can be configured through the {@code delimiter}
 * attribute. By default, it uses the HTML {@code <br>} tag.</p>
 * 
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * &lt;h:outputText value="#{bean.multiLineText}"&gt;
 *     &lt;f:converter converterId="cui.core.lineBreakConverter" /&gt;
 * &lt;/h:outputText&gt;
 * </pre>
 * 
 * <p>With custom delimiter and sanitizing strategy:</p>
 * 
 * <pre>
 * &lt;h:outputText value="#{bean.multiLineText}"&gt;
 *     &lt;f:converter converterId="cui.core.lineBreakConverter" /&gt;
 *     &lt;f:attribute name="delimiter" value="&lt;br/&gt;" /&gt;
 *     &lt;f:attribute name="sanitizingStrategy" value="POLICY_DEFINITION" /&gt;
 * &lt;/h:outputText&gt;
 * </pre>
 * 
 * <p>The converter is intended for display purposes only and does not support 
 * converting from string to object (it will return the input unchanged in that direction).</p>
 * 
 * <p>This converter is thread-safe as long as it's not modified during use.</p>
 *
 * @author Matthias Walliczek
 * @see CuiSanitizer For available sanitization strategies
 * @see AbstractConverter The parent class providing converter infrastructure
 * @since 1.0
 */
@FacesConverter("cui.core.lineBreakConverter")
public class LineBreakConverter extends AbstractConverter<String> {

    private static final CuiLogger log = new CuiLogger(LineBreakConverter.class);

    /**
     * The concrete sanitizer to be utilized for preventing XSS attacks and ensuring
     * HTML validity. Defaults to {@link CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES},
     * which escapes HTML but preserves entities.
     */
    @Getter
    private CuiSanitizer sanitizer = CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES;

    /**
     * Sets the sanitization strategy to use when processing the input text.
     * The strategy determines how the text is cleaned to prevent XSS attacks
     * while maintaining necessary formatting.
     *
     * @param sanitizerIdentifier The name of the sanitizer to use, case-insensitive.
     *                           Must match one of the enum values in {@link CuiSanitizer}.
     *                           If empty or null, the default sanitizer will be used.
     * @see CuiSanitizer For available sanitization strategies
     */
    public void setSanitizingStrategy(final String sanitizerIdentifier) {
        if (!isEmpty(sanitizerIdentifier)) {
            sanitizer = CuiSanitizer.valueOf(sanitizerIdentifier.toUpperCase());
        }
    }

    /**
     * The HTML element or string to be used as a line break delimiter in the output.
     * Defaults to the HTML line break tag {@code <br>}.
     */
    @Getter
    @Setter
    private String delimiter = "<br>";

    /**
     * <p>Converts a plain text string with newlines into an HTML-formatted string with
     * appropriate line break elements. The conversion process follows these steps:</p>
     * <ol>
     *   <li>Sanitize the input text using the configured sanitizer</li>
     *   <li>Split the text into lines using a BufferedReader</li>
     *   <li>Join the lines using the configured delimiter</li>
     * </ol>
     * 
     * <p>If any IOException occurs during the conversion process, it is logged at debug
     * level and the method attempts to continue with the conversion.</p>
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The plain text string to be converted
     * @return The HTML-formatted string with proper line breaks, or null if input is null
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value) {

        var lines = new ArrayList<String>();

        try (var bufferedReader = new BufferedReader(new StringReader(sanitizer.apply(value)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.debug("splitting failed", e);
        }
        return Joiner.on(delimiter).join(lines);
    }
}
