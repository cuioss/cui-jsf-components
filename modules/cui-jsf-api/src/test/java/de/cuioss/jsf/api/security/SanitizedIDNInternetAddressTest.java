package de.cuioss.jsf.api.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SanitizedIDNInternetAddressTest {

    @Test
    void testSanitize() {
        Assertions.assertEquals("abc&amp;asdasd <asdasd@asdasd.de>",
                SanitizedIDNInternetAddress.decode("abc&asdasd <asdasd@asdasd.de>"));
        Assertions.assertEquals("abc <asdasd@asdasd.de>&#34; onclick&#61;&#34;alert();",
                SanitizedIDNInternetAddress.decode("abc <asdasd@asdasd.de>\" onclick=\"alert();"));
        Assertions.assertEquals("Max Müller <max@xn--mller-kva.de>",
                SanitizedIDNInternetAddress.encode("Max Müller <max@müller.de>"));
    }
}
