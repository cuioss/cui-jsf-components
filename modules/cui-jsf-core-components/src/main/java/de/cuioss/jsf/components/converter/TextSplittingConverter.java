/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
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
 * <p>JSF converter that formats long text strings by inserting line breaks at specified
 * intervals and optionally abridging (truncating) text that exceeds a maximum length.
 * This converter is a thin wrapper around the {@link TextSplitter} utility class
 * which provides the actual text formatting functionality.</p>
 * 
 * <p>The converter provides two configurable parameters:</p>
 * <ul>
 *   <li>{@code forceLengthBreakCount} - The number of characters after which a line break
 *       will be inserted (defaults to 15)</li>
 *   <li>{@code abridgedLengthCount} - The maximum number of characters allowed before
 *       the text is truncated/abridged (defaults to 4096)</li>
 * </ul>
 * 
 * <p>This converter is particularly useful for displaying large text blocks in a more
 * readable format, preventing horizontal scrolling, and limiting the display size of
 * extremely large text content.</p>
 * 
 * <p>Note that this is a formatting-only converter that only supports the object-to-string
 * conversion direction. It does not support parsing (string-to-object conversion).</p>
 * 
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * &lt;h:outputText value="#{bean.longTextContent}"&gt;
 *     &lt;f:converter converterId="de.cuioss.jsf.components.converter.TextSplittingConverter" /&gt;
 *     &lt;f:attribute name="forceLengthBreakCount" value="20" /&gt;
 *     &lt;f:attribute name="abridgedLengthCount" value="1000" /&gt;
 * &lt;/h:outputText&gt;
 * </pre>
 * 
 * <p>This converter is thread-safe when its configuration attributes are not modified
 * during use.</p>
 *
 * @author Oliver Wolff
 * @see TextSplitter The underlying utility class that performs the actual text formatting
 * @see AbstractConverter The parent class providing converter infrastructure
 * @since 1.0
 */
@FacesConverter(TextSplittingConverter.CONVERTER_ID)
public class TextSplittingConverter extends AbstractConverter<String> {

    /** The converter identifier for registration with the JSF framework */
    public static final String CONVERTER_ID = "de.cuioss.jsf.components.converter.TextSplittingConverter";

    /**
     * <p>The number of characters after which a line break will be forcibly inserted
     * when formatting the text. This helps maintain readable line lengths in the output.</p>
     * 
     * <p>Defaults to 15 characters.</p>
     */
    @Getter
    @Setter
    private int forceLengthBreakCount = 15;

    /**
     * <p>The maximum number of characters allowed before the text is truncated/abridged.
     * If the text exceeds this length, it will be shortened and an ellipsis will be
     * added to indicate truncation.</p>
     * 
     * <p>Defaults to 4096 characters.</p>
     */
    @Getter
    @Setter
    private int abridgedLengthCount = 4096;

    /**
     * <p>Converts a string to its formatted version by applying line breaks and optional
     * truncation. The conversion process follows these steps:</p>
     * <ol>
     *   <li>Apply forced line breaks at the interval specified by {@link #forceLengthBreakCount}</li>
     *   <li>If the resulting text is longer than {@link #abridgedLengthCount}, abridge
     *       (truncate) it to the maximum length with an ellipsis</li>
     * </ol>
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The string to be formatted, may be null
     * @return The formatted string with inserted line breaks and optional truncation,
     *         or null if the input is null
     * @throws ConverterException If the conversion fails
     */
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
