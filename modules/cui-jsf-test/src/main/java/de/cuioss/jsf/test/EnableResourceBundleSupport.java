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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import org.jboss.weld.junit5.auto.AddBeanClasses;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * JUnit 5 annotation that enables resource bundle and internationalization support for JSF tests.
 * <p>
 * This annotation registers the necessary mock beans to provide localization and resource bundle
 * capabilities in a test environment. It's particularly useful for testing components that
 * rely on message bundles, localized content, or internationalization features.
 * </p>
 * <p>
 * The annotation registers the following mock beans:
 * </p>
 * <ul>
 * <li>{@link LocaleProducerMock}: Provides locale information for the test environment</li>
 * <li>{@link MirrorCuiRessourcBundle}: A mock resource bundle that simplifies message key resolution</li>
 * <li>{@link MessageProducerMock}: Produces messages from resource bundles for testing</li>
 * </ul>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * {@code
 * @EnableJSFCDIEnvironment
 * @EnableResourceBundleSupport
 * class MyLocalizedComponentTest {
 *     
 *     @Inject
 *     private MessageProducer messageProducer;
 *     
 *     @Test
 *     void shouldRenderLocalizedMessages() {
 *         // Test with injected message producer
 *     }
 * }
 * }
 * </pre>
 * <p>
 * This annotation is thread-safe and is typically used in conjunction with
 * {@link EnableJSFCDIEnvironment} to provide a complete testing environment.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@AddBeanClasses({MirrorCuiRessourcBundle.class, LocaleProducerMock.class, MessageProducerMock.class})
public @interface EnableResourceBundleSupport {
}
