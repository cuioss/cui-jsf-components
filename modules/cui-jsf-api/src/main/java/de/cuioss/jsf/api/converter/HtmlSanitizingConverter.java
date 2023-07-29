package de.cuioss.jsf.api.converter;

import static de.cuioss.tools.string.MoreStrings.isNotBlank;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.tools.logging.CuiLogger;
import lombok.AccessLevel;
import lombok.Setter;

/**
 * Converter utilized for ensuring proper escaping of input / output fields.
 *
 * @author Oliver Wolff
 */
@FacesConverter(value = "de.cuioss.jsf.components.converter.HtmlSanitizingConverter")
public class HtmlSanitizingConverter extends AbstractConverter<String> {

    private static final CuiLogger LOGGER = new CuiLogger(HtmlSanitizingConverter.class);

    /**
     * The concrete sanitizer to be utilized. Defaults to
     * {@link CuiSanitizer#PLAIN_TEXT_PRESERVE_ENTITIES}
     */
    @Setter(AccessLevel.PROTECTED)
    private CuiSanitizer sanitizer = CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES;

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        var sanitizedValue = sanitizer.apply(value);
        if (null == sanitizedValue) {
            return "";
        }
        return sanitizedValue;
    }

    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return sanitizer.apply(value);
    }

    /**
     * Sets the corresponding sanitizer.
     *
     * @param sanitizerIdentifier to set. One of "PLAIN_TEXT",
     *                            "PLAIN_TEXT_PRESERVE_ENTITIES", "SIMPLE_HTML",
     *                            "COMPLEX_HTML", "COMPLEX_HTML_PRESERVE_ENTITIES",
     *                            "PASSTHROUGH" expected.
     */
    public void setStrategy(final String sanitizerIdentifier) {
        if (isNotBlank(sanitizerIdentifier)) {
            LOGGER.debug("Configure to %s", sanitizerIdentifier);
            sanitizer = CuiSanitizer.valueOf(sanitizerIdentifier.toUpperCase());
        } else {
            LOGGER.debug("No identifier configured, using default");
        }
    }
}
