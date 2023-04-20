package de.cuioss.jsf.api.converter;

import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.security.CuiSanitizer;

/**
 * To be used when a text containing HTML elements for formatting needs to be displayed.
 */
@FacesConverter(value = "cui.converter.ComplexHtmlSanitizingConverter")
public class ComplexHtmlSanitizingConverter extends HtmlSanitizingConverter {

    public ComplexHtmlSanitizingConverter() {
        super();
        setSanitizer(CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES);
    }

}
