package com.icw.ehf.cui.core.api.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.tools.string.TextSplitter;
import lombok.Getter;
import lombok.Setter;

/**
 * Simple wrapping converter for {@link TextSplitter}. It is a formatting only converter.
 *
 * @author Oliver Wolff
 */
@FacesConverter(TextSplittingConverter.CONVERTER_ID)
public class TextSplittingConverter extends AbstractConverter<String> {

    /** */
    public static final String CONVERTER_ID = "com.icw.ehf.cui.components.converter.TextSplittingConverter";

    /** Count of characters when a text break will forced, defaults to 15 */
    @Getter
    @Setter
    private int forceLengthBreakCount = 15;

    /**
     * Count of characters until the complete text will be abridged, defaults to 4096.
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
            splittedText = new TextSplitter(splittedText, forceLengthBreakCount,
                    abridgedLengthCount).getAbridgedText();
        }
        return splittedText;
    }

}
