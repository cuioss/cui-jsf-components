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
package de.cuioss.jsf.dev.ui.components;

import static de.cuioss.tools.string.MoreStrings.isEmpty;
import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.tools.string.Splitter;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Renderer implementation for {@link SourceCodeComponent} that handles the rendering
 * of source code with syntax highlighting and copy-to-clipboard functionality.
 * </p>
 * 
 * <p>
 * The renderer produces an HTML structure that:
 * </p>
 * <ol>
 *   <li>Optionally renders a description paragraph if provided</li>
 *   <li>Optionally renders a "Copy" button that copies the code to the clipboard</li>
 *   <li>Renders a {@code <pre>} element with the appropriate CSS classes for syntax highlighting</li>
 *   <li>Renders the source code within a {@code <code>} element with the appropriate language class</li>
 * </ol>
 * 
 * <p>
 * The rendering process includes:
 * </p>
 * <ul>
 *   <li>HTML entity escaping to prevent XSS vulnerabilities</li>
 *   <li>Line length limiting to improve readability</li>
 *   <li>Line break normalization</li>
 *   <li>Proper indentation preservation when wrapping long lines</li>
 * </ul>
 * 
 * <p>Example rendered HTML structure:</p>
 * <pre>
 * &lt;p&gt;Optional description text&lt;/p&gt;
 * 
 * &lt;!-- Copy button elements (if enabled) --&gt;
 * &lt;div class="cui-btn-copy-wrapper"&gt;
 *   &lt;button id="j_id_1:copyBtn" class="cui-btn-copy" 
 *           title="Copy content to clipboard" data-toggle="tooltip" 
 *           onclick="Cui.Utilities.copyToClipboard('j_id_1:invisibleTextArea', 'j_id_1:copyBtn');return false;"&gt;
 *     &lt;span&gt;Copy&lt;/span&gt;
 *   &lt;/button&gt;
 * &lt;/div&gt;
 * &lt;textarea id="j_id_1:invisibleTextArea" style="display:none;"&gt;Source code content&lt;/textarea&gt;
 * 
 * &lt;!-- Source code display --&gt;
 * &lt;pre id="preWrapper" class="prettyprint linenums"&gt;
 *   &lt;code class="lang-java"&gt;
 *     public class Example {
 *         // Source code content with HTML entities escaped
 *     }
 *   &lt;/code&gt;
 * &lt;/pre&gt;
 * </pre>
 * 
 * <p>
 * This renderer relies on the prettyprint.js library for syntax highlighting,
 * which must be included in the page for proper highlighting.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
@FacesRenderer(componentFamily = SourceCodeComponent.COMPONENT_FAMILY_FIELD, rendererType = SourceCodeComponent.RENDERER_TYPE)
public class SourceCodeComponentRenderer extends BaseDecoratorRenderer<SourceCodeComponent> {

    private static final CuiLogger LOGGER = new CuiLogger(SourceCodeComponentRenderer.class);

    /**
     * Line separator used for rendering the source code
     */
    private static final String LINE_SEPARATOR = "\n";

    /**
     * Label displayed on the copy-to-clipboard button
     */
    static final String COPY_BUTTON_LABEL = "Copy";

    /**
     * Tooltip title for the copy-to-clipboard button
     */
    static final String COPY_BUTTON_TITLE = "Copy content to clipboard";

    /**
     * Client ID suffix for the invisible textarea that holds the content for copying
     */
    static final String ID_INVISIBLE_TEXT_AREA_SUFFIX = "invisibleTextArea";

    /**
     * Client ID for the pre element that wraps the source code
     */
    static final String ID_PRE_WRAPPER = "preWrapper";

    /**
     * Identifier for the copy button element
     */
    static final String COPY_BTN_IDENTIFIER = "copyBtn";

    /**
     * CSS class for the copy button
     */
    static final String COPY_BTN_CLASS = "cui-btn-copy";

    /**
     * CSS class for the div that wraps the copy button
     */
    static final String COPY_BTN_WRAPPER_CLASS = "cui-btn-copy-wrapper";

    /**
     * JavaScript onclick handler format string for the copy button.
     * Expects two parameters:
     * <ol>
     *   <li>The ID of the invisible textarea containing the content to copy</li>
     *   <li>The ID of the copy button itself</li>
     * </ol>
     */
    static final String COPY_ON_CLICK_HANDLER = "Cui.Utilities.copyToClipboard('%s', '%s');return false;";

    /**
     * CSS class for the pre element that contains the source code.
     * prettyprint enables the syntax highlighting, linenums enables line numbering.
     */
    static final String PRE_STYLE_CLASS = "prettyprint linenums";

    /**
     * <p>
     * Default constructor that enables passthrough attributes by calling the
     * parent constructor with {@code true}.
     * </p>
     * 
     * <p>
     * This allows HTML attributes not explicitly defined by the component to be
     * passed through to the rendered output.
     * </p>
     */
    public SourceCodeComponentRenderer() {
        super(true);
    }

    /**
     * <p>
     * Renders the source code component with the following structure:
     * </p>
     * <ol>
     *   <li>Optional description paragraph</li>
     *   <li>Optional copy-to-clipboard button and hidden textarea</li>
     *   <li>Pre element with code element inside for the formatted source code</li>
     * </ol>
     * 
     * <p>
     * The source code is processed to:
     * </p>
     * <ul>
     *   <li>Handle line breaks consistently</li>
     *   <li>Limit line length for better readability</li>
     *   <li>Escape HTML entities to prevent XSS vulnerabilities</li>
     * </ul>
     * 
     * @param context The FacesContext for the current request
     * @param writer The DecoratingResponseWriter to write to
     * @param component The SourceCodeComponent being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<SourceCodeComponent> writer,
            final SourceCodeComponent component) throws IOException {
        if (!isEmpty(component.getDescription())) {
            writer.withStartElement(Node.P);
            writer.withTextContent(component.getDescription().formatted(), true);
            writer.withEndElement(Node.P);
        }
        final var resolvedSource = component.resolveSource().replace("%n", LINE_SEPARATOR);
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

    /**
     * <p>
     * Replaces HTML special characters with their corresponding entity references
     * to prevent XSS vulnerabilities and ensure proper display.
     * </p>
     * 
     * <p>
     * The following characters are replaced:
     * </p>
     * <ul>
     *   <li>&amp; → &amp;amp;</li>
     *   <li>&quot; → &amp;quot;</li>
     *   <li>\ → &amp;#39;</li>
     *   <li>&lt; → &amp;lt;</li>
     *   <li>&gt; → &amp;gt;</li>
     * </ul>
     * 
     * @param text The text to escape
     * @return The escaped text with HTML entities
     */
    private String replaceHtmlEntities(String text) {
        var result = nullToEmpty(text);
        result = result.replace("&", "&amp;");
        result = result.replace("\"", "&quot;");
        result = result.replace("\\", "&#39;");
        result = result.replace("<", "&lt;");
        return result.replace(">", "&gt;");
    }

    /**
     * <p>
     * Limits the length of each line in the source code to improve readability.
     * </p>
     * 
     * <p>
     * If a line exceeds the specified maximum length, it is split into multiple
     * lines while preserving indentation and maintaining word boundaries where
     * possible.
     * </p>
     * 
     * <p>
     * The algorithm:
     * </p>
     * <ol>
     *   <li>Splits the source into lines</li>
     *   <li>For each line shorter than the limit, keeps it as is</li>
     *   <li>For longer lines, calls {@link #handleLineSplit} to split them</li>
     *   <li>Rejoins all lines with the system line separator</li>
     * </ol>
     * 
     * @param resolvedSource The source code to process
     * @param rowLength The maximum characters per line
     * @return The processed source code with line length limits applied
     */
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

    /**
     * <p>
     * Splits a long line of code into multiple lines while preserving indentation
     * and maintaining word boundaries.
     * </p>
     * 
     * <p>
     * The algorithm:
     * </p>
     * <ol>
     *   <li>Computes the indentation level of the original line</li>
     *   <li>Splits the line on space characters</li>
     *   <li>Adds words to a new line until it would exceed the maximum length</li>
     *   <li>Starts a new line with increased indentation for continuation lines</li>
     *   <li>Continues until all words are processed</li>
     * </ol>
     * 
     * <p>
     * Continuation lines are indented with 2 additional spaces beyond the
     * original line's indentation to visually indicate they are part of the
     * same logical line.
     * </p>
     * 
     * @param result The list to add the resulting split lines to
     * @param line The line to split
     * @param rowLength The maximum characters per line
     */
    static void handleLineSplit(final List<String> result, final String line, final int rowLength) {
        final var indent = computeIndent(line);
        final var splitted = Splitter.on(' ').trimResults().splitToList(line.trim());
        if (splitted.size() < 2) {
            LOGGER.debug("No Whitespace found to split on line %s", line);
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

    /**
     * <p>
     * Computes the indentation level (number of leading whitespace characters)
     * for a line of code.
     * </p>
     * 
     * <p>
     * This is used to preserve the original indentation when splitting long lines.
     * </p>
     * 
     * @param line The line to analyze
     * @return The number of leading whitespace characters
     */
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
     * <p>
     * Renders the copy-to-clipboard button and the corresponding invisible textarea
     * that holds the content to be copied.
     * </p>
     * 
     * <p>
     * The copy functionality requires:
     * </p>
     * <ol>
     *   <li>A button with an onclick handler that calls the copy function</li>
     *   <li>An invisible textarea containing the text to be copied</li>
     * </ol>
     * 
     * <p>
     * The copy function is provided by the Cui.Utilities JavaScript library
     * and copies the content from the invisible textarea to the clipboard.
     * </p>
     * 
     * @param writer The DecoratingResponseWriter to write to
     * @param resolvedSource The source code content to be copied
     * @throws IOException if an error occurs during writing to the response
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

    /**
     * <p>
     * Normalizes line breaks in the source code to ensure consistent rendering
     * across different platforms.
     * </p>
     * 
     * <p>
     * This replaces various line break sequences (CRLF, LF, CR) with a
     * standardized line separator.
     * </p>
     * 
     * @param resolvedSource The source code to process
     * @return The source code with normalized line breaks
     */
    private String sanitizeLineBreaks(String resolvedSource) {
        return resolvedSource.replace("(\r\n|\n|\r)/gm", LINE_SEPARATOR);
    }
}
