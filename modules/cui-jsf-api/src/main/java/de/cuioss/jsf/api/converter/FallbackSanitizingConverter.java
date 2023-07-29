package de.cuioss.jsf.api.converter;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.application.projectstage.CuiProjectStageAccessor;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.tools.logging.CuiLogger;

/**
 * Converter utilized for ensuring proper escaping of output fields. Input
 * fields are ignored, only output fields with no escaping are handled.
 * <p>
 * To be configured as fallback converter for all String.
 */
@FacesConverter(value = "cui.converter.FallbackSanitizingConverter")
public class FallbackSanitizingConverter extends AbstractConverter<String> {

    private final CuiLogger log = new CuiLogger(FallbackSanitizingConverter.class);

    private static final CuiSanitizer sanitizer = CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES;

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return checkAndSanitize(component, value);
    }

    @Override
    protected String convertToObject(final FacesContext context, final UIComponent component, final String value)
            throws ConverterException {
        return checkAndSanitize(component, value);
    }

    private String checkAndSanitize(UIComponent component, String value) {
        if (component instanceof UIInput
                || component instanceof HtmlOutputText && ((HtmlOutputText) component).isEscape()) {
            return value;
        }

        if (new CuiProjectStageAccessor().getValue().isDevelopment()) {
            log.warn("CUI-101: Text not correct escaped or sanitized: '{}' in {} (component id {})", value,
                    component.toString(), component.getId());
        }

        var sanitizedValue = sanitizer.apply(value);
        if (null == sanitizedValue) {
            return "";
        }
        return sanitizedValue;
    }

}
