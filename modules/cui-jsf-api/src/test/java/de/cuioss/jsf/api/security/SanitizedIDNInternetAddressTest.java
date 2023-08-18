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

class SanitizedIDNInternetAddressTest {

    @Test
    void testSanitize() {
        assertEquals("abc&amp;asdasd <asdasd@asdasd.de>",
                SanitizedIDNInternetAddress.decode("abc&asdasd <asdasd@asdasd.de>"));
        assertEquals("abc <asdasd@asdasd.de>&#34; onclick&#61;&#34;alert();",
                SanitizedIDNInternetAddress.decode("abc <asdasd@asdasd.de>\" onclick=\"alert();"));
        assertEquals("Max Müller <max@xn--mller-kva.de>",
                SanitizedIDNInternetAddress.encode("Max Müller <max@müller.de>"));
    }
}
