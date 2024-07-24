/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.components.converter;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;

import de.cuioss.jsf.api.converter.AbstractConverter;
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
    protected String convertToString(final FacesContext context, final UIComponent component, final String value) {

        var lines = new ArrayList<String>();

        try (var bufferedReader = new BufferedReader(new StringReader(sanitizer.apply(value)))) {
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
