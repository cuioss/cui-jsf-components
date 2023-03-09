package com.icw.ehf.cui.core.api.security;

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
     * @return the decoded and sanitized address in RFC822 format, does not need to be sanitized
     *         again
     */
    public static String decode(@NonNull final String completeAddress) {
        return IDNInternetAddress.decode(completeAddress, sanitizer);
    }

}
