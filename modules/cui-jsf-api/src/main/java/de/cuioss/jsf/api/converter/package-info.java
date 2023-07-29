/**
 * <h2>Summary</h2>
 * <p>
 * Provides a base class for creating custom-
 * {@link javax.faces.convert.Converter}:
 * {@link de.cuioss.jsf.api.converter.AbstractConverter} that should be used for
 * all our {@link javax.faces.convert.Converter} in order to reduce boiler-plate
 * code and implement contracts correctly. It relies on
 * {@link de.cuioss.jsf.api.application.message.MessageProducer} and
 * {@link de.cuioss.jsf.api.application.bundle.CuiResourceBundle} being
 * present.<br>
 * {@link de.cuioss.jsf.api.converter.ObjectToStringConverter} and
 * {@link de.cuioss.jsf.api.converter.StringIdentConverter} are some very basic
 * framework specific {@link javax.faces.convert.Converter}
 * </p>
 *
 * @author Oliver Wolff
 *
 */
package de.cuioss.jsf.api.converter;
