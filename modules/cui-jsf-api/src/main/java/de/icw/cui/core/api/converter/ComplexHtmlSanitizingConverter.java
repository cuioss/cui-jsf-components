package de.icw.cui.core.api.converter;

import javax.faces.convert.FacesConverter;

import com.icw.ehf.cui.core.api.converter.HtmlSanitizingConverter;
import com.icw.ehf.cui.core.api.security.CuiSanitizer;

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
