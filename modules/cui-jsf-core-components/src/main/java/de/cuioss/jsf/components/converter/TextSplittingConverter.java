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

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.tools.string.TextSplitter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import lombok.Getter;
import lombok.Setter;

/**
 * Simple wrapping converter for {@link TextSplitter}. It is a formatting only
 * converter.
 *
 * @author Oliver Wolff
 */
@FacesConverter(TextSplittingConverter.CONVERTER_ID)
public class TextSplittingConverter extends AbstractConverter<String> {

    /** */
    public static final String CONVERTER_ID = "de.cuioss.jsf.components.converter.TextSplittingConverter";

    /** Count of characters when a text break will forced, defaults to 15 */
    @Getter
    @Setter
    private int forceLengthBreakCount = 15;

    /**
     * Count of characters until the complete text will be abridged, defaults to
     * 4096.
     */
    @Getter
    @Setter
    private int abridgedLengthCount = 4096;

    @Override
    protected String convertToString(FacesContext context, UIComponent component, String value)
            throws ConverterException {
        var splittedText = new TextSplitter(value, forceLengthBreakCount, abridgedLengthCount)
                .getTextWithEnforcedLineBreaks();
        if (null != splittedText && splittedText.length() > abridgedLengthCount) {
            splittedText = new TextSplitter(splittedText, forceLengthBreakCount, abridgedLengthCount).getAbridgedText();
        }
        return splittedText;
    }

}
