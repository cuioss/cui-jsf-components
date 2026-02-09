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

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;
import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import org.junit.jupiter.api.DisplayName;

@EnableJsfEnvironment
@DisplayName("Tests for ComplexHtmlSanitizingConverter")
class ComplexHtmlSanitizingConverterTest extends AbstractConverterTest<ComplexHtmlSanitizingConverter, String> {

    // Safe HTML content that should pass through unchanged
    static final String SIMPLE_HTML = "<div><p>Test</p></div>";

    // HTML with malicious script that should be sanitized
    static final String MALICIOUS_HTML = "<div><p>Test<script>alert('Hallo');</script></p></div>";

    @Override
    @DisplayName("Configure test cases for HTML sanitization")
    public void populate(final TestItems<String> testItems) {
        // Test that safe HTML passes through unchanged
        testItems.addValidObjectWithStringResult(SIMPLE_HTML, SIMPLE_HTML)
                // Test that malicious HTML is sanitized (script tag removed)
                .addValidStringWithObjectResult(MALICIOUS_HTML, SIMPLE_HTML);
    }
}
