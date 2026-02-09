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
package de.cuioss.jsf.test;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import de.cuioss.portal.common.locale.LocaleChangeEvent;
import de.cuioss.portal.common.locale.PortalLocale;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * A mock implementation that produces {@link Locale} objects for test environments.
 * <p>
 * This class serves as a test replacement for production {@link PortalLocale} providers,
 * making it possible to control and verify locale-related functionality in unit tests.
 * It provides configurable locale settings and simulates locale change events.
 * </p>
 * <p>
 * By default, the mock provides {@link Locale#ENGLISH} as the current locale and
 * supports both {@link Locale#GERMAN} and {@link Locale#ENGLISH} as available locales.
 * These defaults can be modified for test-specific scenarios.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * {@code
 * @EnableJSFCDIEnvironment
 * @EnableResourceBundleSupport
 * class LocalizedComponentTest {
 *
 *     @Inject
 *     private LocaleProducerMock localeProducer;
 *
 *     @Test
 *     void shouldRenderGermanContent() {
 *         // Switch to German locale for testing
 *         localeProducer.setLocale(Locale.GERMAN);
 *
 *         // Test locale-specific behavior
 *     }
 * }
 * }
 * </pre>
 * <p>
 * This class is conditionally thread-safe. Thread safety depends on clients
 * properly synchronizing access when modifying locale properties.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@ApplicationScoped
@EqualsAndHashCode
@ToString
public class LocaleProducerMock implements Serializable {

    @Serial
    private static final long serialVersionUID = 901932913924354093L;

    /**
     * The current locale used for testing.
     * <p>
     * Defaults to {@link Locale#ENGLISH}. Can be changed during tests to
     * verify behavior with different locales.
     * </p>
     */
    @Getter
    @Setter
    @Produces
    @Dependent
    @PortalLocale
    private Locale locale = Locale.ENGLISH;

    /**
     * The list of available locales for the test environment.
     * <p>
     * Defaults to German and English locales. Can be modified for testing
     * application behavior with different locale configurations.
     * </p>
     */
    @Getter
    @Setter
    private List<Locale> availableLocales = immutableList(Locale.GERMAN, Locale.ENGLISH);

    /**
     * Event notifier for locale changes.
     * <p>
     * Can be used in tests to verify that locale change events are properly
     * handled by application components.
     * </p>
     */
    @Inject
    @LocaleChangeEvent
    Event<Locale> localeChangeEvent;

}
