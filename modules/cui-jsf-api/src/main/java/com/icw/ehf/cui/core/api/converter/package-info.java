/**
 * <h2>Summary</h2>
 * <p>
 * Provides a base class for creating custom-
 * {@link javax.faces.convert.Converter}:
 * {@link com.icw.ehf.cui.core.api.converter.AbstractConverter} that should be
 * used for all our {@link javax.faces.convert.Converter} in order to reduce
 * boiler-plate code and implement contracts correctly. It relies on
 * {@link com.icw.ehf.cui.core.api.application.message.MessageProducer} and
 * {@link com.icw.ehf.cui.core.api.application.bundle.CuiResourceBundle} being
 * present.<br />
 * {@link com.icw.ehf.cui.core.api.converter.ObjectToStringConverter} and
 * {@link com.icw.ehf.cui.core.api.converter.StringIdentConverter} are some very
 * basic framework specific {@link javax.faces.convert.Converter}
 * </p>
 *
 * @author Oliver Wolff
 *
 */
package com.icw.ehf.cui.core.api.converter;
