package de.cuioss.jsf.api.converter;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import lombok.Getter;
import lombok.Setter;

/**
 * Converts a plain text to a sanitized html text with valid line breaks.
 *
 * @author Matthias Walliczek
 */
@FacesConverter("cui.core.lineBreakConverter")
public class LineBreakConverter extends AbstractConverter<String> {

    private static final CuiLogger log = new CuiLogger(LineBreakConverter.class);

    /**
     * The concrete sanitizer to be utilized. Defaults to
     * {@link CuiSanitizer#PLAIN_TEXT}
     */
    @Getter
    private CuiSanitizer sanitizer = CuiSanitizer.PLAIN_TEXT_PRESERVE_ENTITIES;

    /**
     * Sets the corresponding sanitizer.
     *
     * @param sanitizerIdentifier to set. See {@link CuiSanitizer}.
     */
    public void setSanitizingStrategy(final String sanitizerIdentifier) {
        if (!isEmpty(sanitizerIdentifier)) {
            sanitizer = CuiSanitizer.valueOf(sanitizerIdentifier.toUpperCase());
        }
    }

    @Getter
    @Setter
    private String delimiter = "<br>";

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final String value) {

        var lines = new ArrayList<String>();

        try (var bufferedReader =
            new BufferedReader(new StringReader(sanitizer.apply(value)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.debug("splitting failed", e);
        }
        return Joiner.on(delimiter).join(lines);
    }
}
