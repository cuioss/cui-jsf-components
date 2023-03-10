package de.cuioss.jsf.api.security;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import de.cuioss.tools.collect.MapBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Simple wrapper / utility around org.owasp.html.PolicyFactory providing some
 * defaults. The usage of this class is not mandatory because the fluent api of
 * {@link HtmlPolicyBuilder} is excellent, but it may be convenient.
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CuiSanitizer implements UnaryOperator<String> {

    /**
     * Results always in Plain-text
     */
    PLAIN_TEXT(new HtmlPolicyBuilder().toFactory(), false),

    /**
     * Results always in Plain-text and preserves entities
     */
    PLAIN_TEXT_PRESERVE_ENTITIES(new HtmlPolicyBuilder().toFactory(), true),

    /**
     * Allows simple Html-elements like "p", "div", "h1", "h2", "h3", "h4",
     * "h5", "h6", "ul", "ol", "li", "blockquote"
     */
    SIMPLE_HTML(new HtmlPolicyBuilder().allowCommonBlockElements().toFactory(), false),

    /**
     * In addition to {@link #SIMPLE_HTML} this sanitizer allows for the
     * elements "b", "i", "font", "s", "u", "o", "sup", "sub", "ins", "del",
     * "strong", "strike", "tt", "code", "big", "small", "br", "span"
     */
    COMPLEX_HTML(new HtmlPolicyBuilder().allowCommonBlockElements().allowCommonInlineFormattingElements().allowStyling()
            .toFactory(), false),

    /**
     * In addition to {@link #COMPLEX_HTML} this sanitizer allows for the table elements
     * elements !table", "tbody", "tfoot", "thead", "tr", "td", "th", "caption", "colgroup", "col"
     */
    MORE_COMPLEX_HTML(
            new HtmlPolicyBuilder()
                    .allowCommonBlockElements()
                    .allowCommonInlineFormattingElements()
                    .allowStyling()
                    .allowAttributes("class").matching(Pattern.compile("[a-zA-Z0-9\\s,\\-_]+")).globally()
                    .allowElements("table", "tbody", "tfoot", "thead", "tr", "td", "th", "caption", "colgroup", "col")
                    .toFactory(),
            false),

    /**
     * In addition to {@link #SIMPLE_HTML} this sanitizer allows for the
     * elements "b", "i", "font", "s", "u", "o", "sup", "sub", "ins", "del",
     * "strong", "strike", "tt", "code", "big", "small", "br", "span" and preserves entities
     */
    COMPLEX_HTML_PRESERVE_ENTITIES(
            new HtmlPolicyBuilder().allowCommonBlockElements().allowCommonInlineFormattingElements().allowStyling()
                    .toFactory(),
            true),

    /**
     * Passthrough sanitizer that actually does no sanitizing at all. Caution:
     * Use it only if you have a different way for ensuring that the given
     * String is not harmful.
     */
    PASSTHROUGH(null, false);

    /** . */
    private static final String AMP = "&amp;";

    /**
     * The corresponding policyFactory.
     */
    private final PolicyFactory policyFactory;

    /**
     * Preserves html-entities from being converted.
     */
    private final boolean preserveEntities;

    /**
     * Map of html-entities to chars which must be converted back to chars after sanitizing.
     */
    private final Map<String, String> specialChars = new MapBuilder<String, String>().put("&#8722;", "-")
            .put("&#43;", "+").put("&#34;", "\"").put("&#64;", "@").put("&#39;", "'").put(AMP, "&").put("&#61;", "=")
            .put("&lt;", "<").put("&gt;", ">").put("&#96;", "`").toImmutableMap();

    /**
     * It is possible to use '&' as '&amp;'. It means that it is possible to escape '&' in
     * html-entities also, i.e.: "&amp;lt;" represents "&lt;". It is recursive since it is possible
     * to make constructs like "&amp;amp;amp;lt". Sanitizer don't catch this case therefore there is
     * workaround implemented.
     *
     * @param input input string.
     * @return input where all '&amp;' replaced with '&'.
     */
    private String catchDoubleEscape(final String input) {
        if (!input.contains(AMP)) {
            return input;
        }
        return catchDoubleEscape(input.replace(AMP, "&"));
    }

    /**
     * Some HTML-entities should be replaced by it string representation for correct sanitizing.
     *
     * @param input input string
     * @return
     */
    private String replaceEscaped(final String input) {
        var sanitizedValue = input;
        for (final Entry<String, String> e : specialChars.entrySet()) {
            sanitizedValue = sanitizedValue.replaceAll(e.getKey(), e.getValue());
        }
        return sanitizedValue;
    }

    /**
     * @param untrustedHtml
     *            The string to be sanitized, may be null or empty
     * @return the sanitized String or empty String if given String is null or
     *         empty
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

            // replace back some chars since sanitizer corrupt it
            sanitizedValue = replaceEscaped(sanitizedValue);

            return sanitizedValue;
        }
        return nullToEmpty(untrustedHtml);
    }
}
