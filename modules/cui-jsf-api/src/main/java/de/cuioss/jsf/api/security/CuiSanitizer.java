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
package de.cuioss.jsf.api.security;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import de.cuioss.tools.collect.MapBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Simple wrapper / utility around {@link org.owasp.html.PolicyFactory} providing some
 * predefined sanitizer configurations as enum constants. It helps to protect 
 * the application against XSS attacks by sanitizing user-provided HTML content.
 * <p>
 * The usage of this class is not mandatory because the fluent API of
 * {@link HtmlPolicyBuilder} is excellent, but it provides convenience for common use cases.
 * </p>
 * <p>
 * Usage examples:
 * </p>
 * <pre>
 * // Sanitize user input to plain text
 * String sanitizedText = CuiSanitizer.PLAIN_TEXT.apply(userInput);
 * 
 * // Sanitize user input allowing some HTML formatting
 * String sanitizedHtml = CuiSanitizer.COMPLEX_HTML.apply(userInput);
 * </pre>
 * <p>
 * Each enum constant represents a different sanitizing policy with varying levels 
 * of HTML elements and attributes allowed.
 * </p>
 * 
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CuiSanitizer implements UnaryOperator<String> {

    /**
     * Results always in plain text, removing all HTML tags and structure.
     * This is the most restrictive option and should be used when no HTML is expected or allowed.
     */
    PLAIN_TEXT(new HtmlPolicyBuilder().toFactory(), false),

    /**
     * Results always in plain text and preserves HTML entities like &amp;, &lt;, etc.
     * Useful when you need to display text with special characters but don't want any HTML formatting.
     */
    PLAIN_TEXT_PRESERVE_ENTITIES(new HtmlPolicyBuilder().toFactory(), true),

    /**
     * Allows simple HTML elements for basic document structure.
     * <p>Permitted elements include: "p", "div", "h1", "h2", "h3", "h4", "h5",
     * "h6", "ul", "ol", "li", "blockquote"</p>
     */
    SIMPLE_HTML(new HtmlPolicyBuilder().allowCommonBlockElements().toFactory(), false),

    /**
     * In addition to {@link #SIMPLE_HTML} this sanitizer allows common text formatting elements.
     * <p>Additional permitted elements include: "b", "i", "font", "s", "u", "o", "sup", "sub", "ins", "del", "strong",
     * "strike", "tt", "code", "big", "small", "br", "span"</p>
     * <p>Also allows styling attributes on elements.</p>
     */
    COMPLEX_HTML(new HtmlPolicyBuilder().allowCommonBlockElements().allowCommonInlineFormattingElements().allowStyling()
            .toFactory(), false),

    /**
     * In addition to {@link #COMPLEX_HTML} this sanitizer allows for table elements.
     * <p>Additional permitted elements include: "table", "tbody", "tfoot", "thead", "tr", "td", "th",
     * "caption", "colgroup", "col"</p>
     * <p>Also allows class attributes containing alphanumeric characters, spaces, commas, hyphens and underscores.</p>
     */
    MORE_COMPLEX_HTML(new HtmlPolicyBuilder().allowCommonBlockElements().allowCommonInlineFormattingElements()
            .allowStyling().allowAttributes("class").matching(Pattern.compile("[a-zA-Z0-9\\s,\\-_]+")).globally()
            .allowElements("table", "tbody", "tfoot", "thead", "tr", "td", "th", "caption", "colgroup", "col")
            .toFactory(), false),

    /**
     * In addition to {@link #SIMPLE_HTML} this sanitizer allows common text formatting elements
     * and preserves HTML entities.
     * <p>Additional permitted elements are the same as {@link #COMPLEX_HTML} but HTML entities
     * like &amp;, &lt;, etc. are preserved.</p>
     */
    COMPLEX_HTML_PRESERVE_ENTITIES(new HtmlPolicyBuilder().allowCommonBlockElements()
            .allowCommonInlineFormattingElements().allowStyling().toFactory(), true),

    /**
     * Passthrough sanitizer that does no sanitizing at all.
     * <p>
     * <strong>Warning:</strong> Use this option only if you have a different way of ensuring
     * that the given String is not harmful, such as when the content comes from a trusted source
     * or has been sanitized earlier in the process.
     * </p>
     */
    PASSTHROUGH(null, false);

    /** The ampersand entity. */
    private static final String AMP = "&amp;";

    /**
     * The corresponding policy factory from OWASP HTML Sanitizer.
     */
    private final PolicyFactory policyFactory;

    /**
     * Flag indicating whether HTML entities should be preserved during sanitization.
     */
    private final boolean preserveEntities;

    /**
     * Map of HTML entities to their character representations which must be converted
     * back to characters after sanitizing.
     */
    private final Map<String, String> specialChars = new MapBuilder<String, String>().put("&#8722;", "-")
            .put("&#43;", "+").put("&#34;", "\"").put("&#64;", "@").put("&#39;", "'").put(AMP, "&").put("&#61;", "=")
            .put("&lt;", "<").put("&gt;", ">").put("&#96;", "`").toImmutableMap();

    /**
     * Handles recursive HTML entity escaping.
     * <p>
     * It is possible to use '&amp;' as '&amp;amp;'. This means that entities can be
     * escaped recursively, creating constructs like "&amp;amp;amp;lt". The sanitizer
     * doesn't handle this case by default, so this method provides a workaround.
     * </p>
     *
     * @param input input string that may contain double-escaped entities
     * @return input where all '&amp;amp;' sequences are replaced with '&amp;'
     */
    private String catchDoubleEscape(final String input) {
        if (!input.contains(AMP)) {
            return input;
        }
        return catchDoubleEscape(input.replace(AMP, "&"));
    }

    /**
     * Replaces HTML entities with their character representation.
     * <p>
     * Some HTML entities should be replaced by their string representation for correct
     * sanitizing and to ensure consistent output.
     * </p>
     *
     * @param input input string that may contain HTML entities
     * @return string with entities replaced by their character representation
     */
    private String replaceEscaped(final String input) {
        var sanitizedValue = input;
        for (final Entry<String, String> e : specialChars.entrySet()) {
            sanitizedValue = sanitizedValue.replaceAll(e.getKey(), e.getValue());
        }
        return sanitizedValue;
    }

    /**
     * Sanitizes the given HTML string according to the policy defined by this enum constant.
     * <p>
     * This method implements the {@link UnaryOperator} functional interface, making
     * instances of this enum usable as function references.
     * </p>
     *
     * @param untrustedHtml The string to be sanitized, may be null or empty
     * @return the sanitized String or empty String if given String is null or empty
     */
    @Override
    public String apply(final String untrustedHtml) {
        if (null != untrustedHtml && !untrustedHtml.trim().isEmpty()) {
            if (null == policyFactory) {
                return untrustedHtml;
            }
            if (!preserveEntities) {
                return policyFactory.sanitize(untrustedHtml);
            }
            // catch constructs like '&amp;amp;lt'
            var sanitizedValue = catchDoubleEscape(untrustedHtml);

            // replace '&lt' with '<' and so on to catch scripts tags.
            sanitizedValue = replaceEscaped(sanitizedValue);

            sanitizedValue = policyFactory.sanitize(sanitizedValue);

            if (null == sanitizedValue) {
                return "";
            }

            return replaceEscaped(sanitizedValue);
        }
        return nullToEmpty(untrustedHtml);
    }
}
