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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author Oliver Wolff
 */

class CuiSanitizerTest {

    private static final String MAIL_ADDRESS = "abc@abc.de";

    public static final String BOLD_STYLE = "style=\"font-weight:bold\"";

    public static final String ELEMENT_CLASS_ATTRIBUTE = "class=\"some-class-name\"";

    public static final String PLAIN_TEXT = "Plain_Text";

    public static final String SIMPLE_HTML = "<div><p>" + PLAIN_TEXT + "</p></div>";

    public static final String COMPLEX_HTML = "<div " + BOLD_STYLE + "><p><span " + BOLD_STYLE + ">" + PLAIN_TEXT
            + "</span></p></div>";

    public static final String COMPLEX_HTML_WITH_TABLE = "<div " + BOLD_STYLE + ">" + "<p>" + "<span " + BOLD_STYLE
            + ">" + PLAIN_TEXT + "</span>" + "</p>" + "<table " + ELEMENT_CLASS_ATTRIBUTE + ">" + "<tbody>" + "<tr>"
            + "<th>Firstname</th>" + "</tr>" + "<tr>" + "<td>Jill</td>" + "</tr>" + "</tbody>" + "</table>" + "</div>";

    public static final String SCRIPT = "<script>alert('malicious')</script>";

    public static final String MALICIOUS_HTML = "<div " + BOLD_STYLE + "><p><span " + BOLD_STYLE + ">" + PLAIN_TEXT
            + "</span></p>" + SCRIPT + "</div>";

    @Test
    void shouldSanitizeToPlainText() {
        assertEquals(PLAIN_TEXT, CuiSanitizer.PLAIN_TEXT.apply(PLAIN_TEXT));
        assertEquals(PLAIN_TEXT, CuiSanitizer.PLAIN_TEXT.apply(SIMPLE_HTML));
        assertEquals(PLAIN_TEXT, CuiSanitizer.PLAIN_TEXT.apply(COMPLEX_HTML));
        assertEquals(PLAIN_TEXT, CuiSanitizer.PLAIN_TEXT.apply(MALICIOUS_HTML));
    }

    @Test
    void shouldSanitizeToSimpleHtml() {
        assertEquals(PLAIN_TEXT, CuiSanitizer.SIMPLE_HTML.apply(PLAIN_TEXT));
        assertEquals(SIMPLE_HTML, CuiSanitizer.SIMPLE_HTML.apply(SIMPLE_HTML));
        assertEquals(SIMPLE_HTML, CuiSanitizer.SIMPLE_HTML.apply(COMPLEX_HTML));
        assertEquals(SIMPLE_HTML, CuiSanitizer.SIMPLE_HTML.apply(MALICIOUS_HTML));
    }

    @Test
    void shouldSanitizeToComplexHtml() {
        assertEquals(PLAIN_TEXT, CuiSanitizer.COMPLEX_HTML.apply(PLAIN_TEXT));
        assertEquals(SIMPLE_HTML, CuiSanitizer.COMPLEX_HTML.apply(SIMPLE_HTML));
        assertEquals(COMPLEX_HTML, CuiSanitizer.COMPLEX_HTML.apply(COMPLEX_HTML));
        assertEquals(COMPLEX_HTML, CuiSanitizer.COMPLEX_HTML.apply(MALICIOUS_HTML));
    }

    @Test
    void shouldSanitizeToMoreComplexHtml() {
        assertEquals(PLAIN_TEXT, CuiSanitizer.MORE_COMPLEX_HTML.apply(PLAIN_TEXT));
        assertEquals(SIMPLE_HTML, CuiSanitizer.MORE_COMPLEX_HTML.apply(SIMPLE_HTML));
        assertEquals(COMPLEX_HTML, CuiSanitizer.MORE_COMPLEX_HTML.apply(COMPLEX_HTML));
        assertEquals(COMPLEX_HTML, CuiSanitizer.MORE_COMPLEX_HTML.apply(MALICIOUS_HTML));
        assertEquals(COMPLEX_HTML_WITH_TABLE, CuiSanitizer.MORE_COMPLEX_HTML.apply(COMPLEX_HTML_WITH_TABLE));
    }

    @Test
    void shouldPassthroughIfConfigured() {
        assertEquals(PLAIN_TEXT, CuiSanitizer.PASSTHROUGH.apply(PLAIN_TEXT));
        assertEquals(SIMPLE_HTML, CuiSanitizer.PASSTHROUGH.apply(SIMPLE_HTML));
        assertEquals(COMPLEX_HTML, CuiSanitizer.PASSTHROUGH.apply(COMPLEX_HTML));
    }

    @Test
    void htmlPreserveEntities() {
        assertEquals("", CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(""));
        assertEquals("abc", CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply("abc"));
        assertEquals("abc", CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply("abc<script>javascript</script>"));
        assertEquals("abc", CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply("&lt;script&gt;javascript</script>abc"));
        assertEquals("abc",
                CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply("a&amp;lt;script&amp;amp;gt;javascript</script>bc"));
    }

    @Test
    void entities() {
        assertEquals("abc&#64;abc.de", CuiSanitizer.COMPLEX_HTML.apply(MAIL_ADDRESS));
        assertEquals(MAIL_ADDRESS, CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(MAIL_ADDRESS));
        assertEquals(MAIL_ADDRESS, CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES.apply(MAIL_ADDRESS));
    }

    @Test
    void nullAndEmpty() {
        for (final CuiSanitizer sanitizer : CuiSanitizer.values()) {
            assertEquals("", sanitizer.apply(null),
                    "null must be converter to empty string, using sanitizer: " + sanitizer);

            assertEquals("", sanitizer.apply(""), "empty string must be returned, using sanitizer: " + sanitizer);

            assertEquals(" ", sanitizer.apply(" "), "whitespaces should not be trimmed, using sanitizer: " + sanitizer);
        }
    }
}
