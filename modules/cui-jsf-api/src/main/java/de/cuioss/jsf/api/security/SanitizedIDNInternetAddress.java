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

import de.cuioss.tools.net.IDNInternetAddress;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SanitizedIDNInternetAddress {

    private static final CuiSanitizer sanitizer = CuiSanitizer.PLAIN_TEXT;

    /**
     * Encode the domain part of an email address
     *
     * @param completeAddress the address to encode in RFC822 format
     * @return the encoded address in RFC822 format
     */
    public static String encode(@NonNull final String completeAddress) {
        return IDNInternetAddress.encode(completeAddress, sanitizer);
    }

    /**
     * Decode the domain part of an email address
     *
     * @param completeAddress the address to decode in RFC822 format
     * @return the decoded and sanitized address in RFC822 format, does not need to
     *         be sanitized again
     */
    public static String decode(@NonNull final String completeAddress) {
        return IDNInternetAddress.decode(completeAddress, sanitizer);
    }

}
