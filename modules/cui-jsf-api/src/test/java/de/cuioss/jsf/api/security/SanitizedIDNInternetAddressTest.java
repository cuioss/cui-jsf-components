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
package de.cuioss.jsf.api.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

// Class is documented by @DisplayName
@DisplayName("Tests for SanitizedIDNInternetAddress")
class SanitizedIDNInternetAddressTest {

    @Nested
    @DisplayName("Decode Tests")
    class DecodeTests {

        @Test
        @DisplayName("Should properly decode and sanitize email addresses")
        void shouldDecodeAndSanitizeEmailAddresses() {
            // Arrange
            final String emailWithAmpersand = "abc&asdasd <asdasd@asdasd.de>";
            final String emailWithJavaScript = "abc <asdasd@asdasd.de>\" onclick=\"alert();";

            // Act & Assert
            assertEquals("abc&amp;asdasd <asdasd@asdasd.de>",
                    SanitizedIDNInternetAddress.decode(emailWithAmpersand),
                    "Should encode ampersands in email addresses");

            assertEquals("abc <asdasd@asdasd.de>&#34; onclick&#61;&#34;alert();",
                    SanitizedIDNInternetAddress.decode(emailWithJavaScript),
                    "Should encode JavaScript event handlers in email addresses");
        }
    }

    @Nested
    @DisplayName("Encode Tests")
    class EncodeTests {

        @Test
        @DisplayName("Should properly encode internationalized domain names")
        void shouldEncodeInternationalizedDomainNames() {
            // Arrange
            final String emailWithUmlaut = "Max Müller <max@müller.de>";
            final String expectedPunycode = "Max Müller <max@xn--mller-kva.de>";

            // Act & Assert
            assertEquals(expectedPunycode,
                    SanitizedIDNInternetAddress.encode(emailWithUmlaut),
                    "Should convert internationalized domain names to punycode");
        }
    }
}
