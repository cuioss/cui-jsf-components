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
package de.cuioss.jsf.dev.ui.components;

import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.tools.string.Splitter;

/**
 * @author Oliver Wolff
 */
@FacesRenderer(componentFamily = SourceCodeComponent.COMPONENT_FAMILY_FIELD, rendererType = SourceCodeComponent.RENDERER_TYPE)
public class SourceCodeComponentRenderer extends BaseDecoratorRenderer<SourceCodeComponent> {

    private static final CuiLogger log = new CuiLogger(SourceCodeComponentRenderer.class);

    private static final String LINE_SEPARATOR = "\n";
    static final String COPY_BUTTON_LABEL = "Copy";
    static final String COPY_BUTTON_TITLE = "Copy content to clipboard";
    static final String ID_INVISIBLE_TEXT_AREA_SUFFIX = "invisibleTextArea";
    static final String ID_PRE_WRAPPER = "preWrapper";
    static final String COPY_BTN_IDENTIFIER = "copyBtn";
    static final String COPY_BTN_CLASS = "cui-btn-copy";
    static final String COPY_BTN_WRAPPER_CLASS = "cui-btn-copy-wrapper";

    static final String COPY_ON_CLICK_HANDLER = "Cui.Utilities.copyToClipboard('%s', '%s');return false;";

    static final String PRE_STYLE_CLASS = "prettyprint linenums";

    /**
     *
     */
    public SourceCodeComponentRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<SourceCodeComponent> writer,
                               final SourceCodeComponent component) throws IOException {
        if (!isEmpty(component.getDescription())) {
            writer.withStartElement(Node.P);
            writer.withTextContent(component.getDescription().formatted(), true);
            writer.withEndElement(Node.P);
        }
        final var resolvedSource = component.resolveSource().replace("%", "%%").replace("%n",
            LINE_SEPARATOR);
        if (component.isEnableClipboard()) {
            renderCopyElements(writer, resolvedSource);
        }
        writer.withStartElement(Node.PRE).withClientId(ID_PRE_WRAPPER)
            .withStyleClass(component.getStyleClassBuilder().append(PRE_STYLE_CLASS)).withPassThroughAttributes()
            .withAttributeStyle(component.getStyle());

        writer.withStartElement(Node.CODE).withStyleClass(component.getType()).withTextContent(
            replaceHtmlEntities(limitLineSize(sanitizeLineBreaks(resolvedSource), component.getMaxLineLength())),
            false);

        writer.withEndElement(Node.CODE).withEndElement(Node.PRE);
    }

    private String replaceHtmlEntities(String limitLineSize) {
        var result = nullToEmpty(limitLineSize);
        result = result.replace("&", "&amp;");
        result = result.replace("\"", "&quot;");
        result = result.replace("\\", "&#39;");
        result = result.replace("<", "&lt;");
        return result.replace(">", "&gt;");
    }

    static String limitLineSize(final String resolvedSource, final int rowLength) {
        if (resolvedSource.length() < rowLength) {
            return resolvedSource;
        }
        final var lines = Splitter.on(System.lineSeparator()).splitToList(resolvedSource);
        final List<String> result = new ArrayList<>();
        for (final String line : lines) {
            if (line.length() < rowLength) {
                result.add(line);
            } else {
                handleLineSplit(result, line, rowLength);
            }
        }
        return Joiner.on(System.lineSeparator()).join(result);

    }

    static void handleLineSplit(final List<String> result, final String line, final int rowLength) {
        final var indent = computeIndent(line);
        final var splitted = Splitter.on(' ').trimResults().splitToList(line.trim());
        if (splitted.size() < 2) {
            log.debug("No Whitespace found to split on line {}", line);
            result.add(line);
            return;
        }
        var indentString = "";
        if (indent > 0) {
            indentString = MoreStrings.leftPad(" ", indent);
        }

        final var indentSecond = MoreStrings.leftPad(" ", indent + 2);
        var newLineBuilder = new StringBuilder(indentString);
        var firstLine = true;
        for (final String element : splitted) {
            if (newLineBuilder.length() + element.length() >= rowLength + 1) {
                final var builtString = newLineBuilder.toString();
                if (!builtString.trim().isEmpty()) {
                    result.add(MoreStrings.stripEnd(builtString, " "));
                }
                if (firstLine) {
                    newLineBuilder = new StringBuilder(indentString);
                } else {
                    newLineBuilder = new StringBuilder(indentSecond);
                }
                firstLine = false;
            }
            newLineBuilder.append(element).append(' ');
        }
        // Fetch the last line
        final var lastLine = newLineBuilder.toString();
        if (!lastLine.trim().isEmpty()) {
            result.add(MoreStrings.stripEnd(lastLine, " "));
        }
    }

    private static int computeIndent(final String line) {
        var indent = 0;
        for (final char character : line.toCharArray()) {
            if (!Character.isWhitespace(character)) {
                break;
            }
            indent++;
        }
        return indent;
    }

    /**
     * Renders the copy button and the corresponding (invisible) textarea
     *
     * @param writer to be written to
     * @param resolvedSource to be written
     * @throws IOException pass through
     */
    private static void renderCopyElements(final DecoratingResponseWriter<SourceCodeComponent> writer,
                                           final String resolvedSource) throws IOException {
        final var copyButtonId = writer.getComponentWrapper().getSuffixedClientId(COPY_BTN_IDENTIFIER);

        final var invisibleTextArea = writer.getComponentWrapper().getSuffixedClientId(ID_INVISIBLE_TEXT_AREA_SUFFIX);

        writer.withStartElement(Node.DIV).withStyleClass(COPY_BTN_WRAPPER_CLASS);
        writer.withStartElement(Node.BUTTON).withClientId(COPY_BTN_IDENTIFIER).withStyleClass(COPY_BTN_CLASS)
            .withAttributeTitle(COPY_BUTTON_TITLE).withAttribute(AttributeName.DATA_TOGGLE, AttributeValue.TOOLTIP);
        writer.withAttribute(AttributeName.JS_ON_CLICK,
            COPY_ON_CLICK_HANDLER.formatted(invisibleTextArea, copyButtonId));

        writer.withStartElement(Node.SPAN).withTextContent(COPY_BUTTON_LABEL, true);
        writer.withEndElement(Node.SPAN).withEndElement(Node.BUTTON).withEndElement(Node.DIV);

        writer.withStartElement(Node.TEXT_AREA).withClientId(ID_INVISIBLE_TEXT_AREA_SUFFIX)
            .withAttributeStyle(AttributeValue.STYLE_DISPLAY_NONE.getContent());

        writer.withTextContent(resolvedSource, true);
        writer.withEndElement(Node.TEXT_AREA);
    }

    private String sanitizeLineBreaks(String resolvedSource) {
        return resolvedSource.replace("(\r\n|\n|\r)/gm", LINE_SEPARATOR);
    }
}
