/**
 * <p>
 * Provides classes for configuring the cui defined theming.
 * </p>
 * <h2>Theming with Cui</h2>
 * <p>
 * The theming is implemented by loading different css files that represent an actual theme.
 * </p>
 * <p>
 * The actual themes are created by the cui-styling project. They are named
 * application-blue.css, application-green.css, .. and are to be found in the library
 * "de.cuioss.portal.css" {@link de.cuioss.jsf.api.application.theme.ThemeResourceHandler}
 * </p>
 * <p>
 * The classes / interfaces within this package:
 * <ul>
 * <li>{@link de.cuioss.jsf.api.application.theme.ThemeResourceHandler}: Is the actual
 * {@link javax.faces.application.ResourceHandler} delivering the css to the client. In order to do
 * so it needs instances of {@link de.cuioss.jsf.api.application.theme.ThemeConfiguration}
 * and
 * {@link de.cuioss.jsf.api.application.theme.ThemeNameProducer} to be present. This both
 * classes
 * do decide which theme to load and create the actual name of the corresponding css to be
 * loaded.<br>
 * The resourceHandler needs to be registered in the application element of the faces-config:
 *
 * <pre>
 * {@code <resource-handler>de.cuioss.jsf.api.application.theme.ThemeResourceHandler</resource-handler>}
 * </pre>
 *
 * In order to work instances of
 * {@link de.cuioss.jsf.api.application.theme.ThemeConfiguration} and
 * {@link de.cuioss.jsf.api.application.theme.ThemeNameProducer} are to be registered as
 * managed-beans
 * using the corresponding names</li>
 * <li>{@link de.cuioss.jsf.api.application.theme.ThemeConfiguration}: Defines what themes
 * are
 * available, what is the default theme and some technical aspects like names and paths. The default
 * implementation {@link de.cuioss.jsf.api.application.theme.impl.ThemeConfigurationImpl}
 * suffices for
 * most needs within our cui / portal environment, and must be configured as a managed bean within
 * the concrete project. Within the portal this is already done.</li>
 * <li>{@link de.cuioss.jsf.api.application.theme.ThemeNameProducer}: Simple abstraction over
 * defining
 * the actual theme at runtime. The default implementation
 * {@link de.cuioss.jsf.api.application.theme.impl.ThemeNameProducerImpl} is for testing
 * purposes only
 * and must be replaced by a proper implementation. It will always return the derived defaultTheme
 * by {@link de.cuioss.jsf.api.application.theme.ThemeConfiguration}</li>
 * </ul>
 * </p>
 *
 * @author Oliver Wolff (Oliver Wolff)
 */
package de.cuioss.jsf.api.application.theme;
