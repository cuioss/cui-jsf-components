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

import de.cuioss.tools.net.IDNInternetAddress;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Utility class for safely handling Internationalized Domain Names (IDN) in email addresses.
 * <p>
 * This class provides methods to encode and decode email addresses with internationalized
 * domain names, while applying security sanitization to prevent injection attacks.
 * It wraps the functionality provided by {@link de.cuioss.tools.net.IDNInternetAddress}
 * and applies {@link CuiSanitizer#PLAIN_TEXT} sanitization to all operations.
 * </p>
 * <p>
 * Internationalized Domain Names allow for non-ASCII characters in domain names,
 * such as those used in many non-Latin writing systems. This class supports processing
 * these domain names in accordance with the IDN standards (RFC 3490) with additional
 * security measures.
 * </p>
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * // Encode an email address with IDN domain
 * String encoded = SanitizedIDNInternetAddress.encode("user@例子.com");
 * 
 * // Decode an encoded email address back to its Unicode form
 * String decoded = SanitizedIDNInternetAddress.decode("user@xn--fsqu00a.com");
 * </pre>
 * 
 * @author Oliver Wolff
 */
@UtilityClass
public class SanitizedIDNInternetAddress {

    /**
     * The sanitizer strategy used for all operations in this class.
     * Plain text sanitizing is chosen to ensure maximum security by removing any HTML markup.
     */
    private static final CuiSanitizer sanitizer = CuiSanitizer.PLAIN_TEXT;

    /**
     * Encodes the domain part of an email address from Unicode to ASCII compatible encoding (Punycode).
     * <p>
     * This method converts internationalized domain names (IDN) in an email address to
     * the ASCII-compatible form (Punycode) for use in systems that don't support Unicode
     * domain names directly. The domain part is encoded while the local part (before @) 
     * remains unchanged. The entire address is also sanitized using {@link CuiSanitizer#PLAIN_TEXT}.
     * </p>
     *
     * @param completeAddress the email address to encode in RFC822 format, must not be null
     * @return the encoded and sanitized address in RFC822 format with the domain in Punycode form
     * @throws NullPointerException if completeAddress is null
     */
    public static String encode(@NonNull final String completeAddress) {
        return IDNInternetAddress.encode(completeAddress, sanitizer);
    }

    /**
     * Decodes the domain part of an email address from ASCII compatible encoding (Punycode) to Unicode.
     * <p>
     * This method converts Punycode-encoded domain names in an email address back to
     * their Unicode representation. The domain part is decoded while the local part (before @) 
     * remains unchanged. The entire address is also sanitized using {@link CuiSanitizer#PLAIN_TEXT}.
     * </p>
     *
     * @param completeAddress the email address to decode in RFC822 format with Punycode domain, must not be null
     * @return the decoded and sanitized address in RFC822 format with the domain in Unicode form
     * @throws NullPointerException if completeAddress is null
     */
    public static String decode(@NonNull final String completeAddress) {
        return IDNInternetAddress.decode(completeAddress, sanitizer);
    }
}
