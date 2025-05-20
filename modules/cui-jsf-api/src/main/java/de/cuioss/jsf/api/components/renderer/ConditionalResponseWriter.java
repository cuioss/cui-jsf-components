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
package de.cuioss.jsf.api.components.renderer;

import static java.util.Objects.requireNonNull;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.ResponseWriter;
import jakarta.faces.context.ResponseWriterWrapper;
import lombok.Getter;

import java.io.IOException;

/**
 * A specialized {@link ResponseWriterWrapper} that conditionally filters calls to the wrapped
 * {@link ResponseWriter} based on a specified marker element.
 * <p>
 * This class is particularly useful when implementing complex renderers that need to selectively
 * write portions of HTML based on conditional logic. It works by either passing or blocking calls
 * to the underlying response writer depending on whether a specified marker element has been 
 * encountered.
 * </p>
 * <p>
 * The writer can operate in two modes:
 * </p>
 * <ul>
 *   <li>Write-until mode (writeUntilElement=true): Passes all calls to the wrapped writer until
 *       the marker element is encountered, then blocks all subsequent calls.</li>
 *   <li>Skip-until mode (writeUntilElement=false): Blocks all calls to the wrapped writer until
 *       the marker element is encountered, then passes all subsequent calls.</li>
 * </ul>
 * <p>
 * In both modes, the marker element itself is never written to the response.
 * </p>
 * <p>
 * This class works well with {@link de.cuioss.jsf.api.components.html.HtmlTreeBuilder}
 * when a node child breakpoint has been set in the HTML tree being built.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * // Create a conditional writer in write-until mode
 * ResponseWriter original = context.getResponseWriter();
 * String markerElement = "cui:childBreakpoint";
 * ConditionalResponseWriter writer = new ConditionalResponseWriter(original, markerElement, true);
 * 
 * // Use the writer
 * writer.startElement("div", component);
 * writer.writeAttribute("class", "container", null);
 * 
 * // This element marks where filtering begins/ends and won't be written
 * writer.startElement(markerElement, null);
 * writer.endElement(markerElement);
 * 
 * // This won't be written in write-until mode
 * writer.startElement("span", component);
 * writer.writeText("This text is filtered", null);
 * writer.endElement("span");
 * </pre>
 * <p>
 * Note: This class is not reentrant and should not be shared between threads.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ResponseWriterWrapper
 * @see de.cuioss.jsf.api.components.html.HtmlTreeBuilder#withNodeChildBreakpoint()
 */
public class ConditionalResponseWriter extends ResponseWriterWrapper {

    /**
     * The wrapped response writer that this conditional writer delegates to.
     */
    @Getter
    private final ResponseWriter wrapped;

    /**
     * The name of the element that triggers the conditional behavior.
     * When this element is encountered, the writer switches its passing behavior.
     */
    private final String matchElement;

    /**
     * Determines the mode of operation for this writer.
     * <p>
     * If true, the writer passes calls until the marker element is encountered,
     * then blocks all subsequent calls.
     * </p>
     * <p>
     * If false, the writer blocks calls until the marker element is encountered,
     * then passes all subsequent calls.
     * </p>
     */
    @Getter
    private final boolean writeUntilElement;

    /**
     * Internal state flag that determines whether calls should currently be passed 
     * to the wrapped writer.
     */
    private boolean pass;

    /**
     * Creates a new ConditionalResponseWriter with specified behavior.
     * <p>
     * The created writer will conditionally pass or block calls to the wrapped
     * response writer based on encounters with the specified marker element.
     * </p>
     *
     * @param wrapped the wrapped {@link ResponseWriter} to delegate to, must not be null
     * @param matchElement identifying the marker element that triggers conditional behavior.
     *                   This element itself will never be rendered, must not be null
     * @param writeUntilElement if true, the writer passes calls until the marker element is
     *                        encountered, then blocks subsequent calls.
     *                        If false, the writer blocks calls until the marker element is
     *                        encountered, then passes subsequent calls.
     * @throws NullPointerException if wrapped or matchElement is null
     */
    public ConditionalResponseWriter(final ResponseWriter wrapped, final String matchElement,
            final boolean writeUntilElement) {
        super(wrapped);
        this.wrapped = requireNonNull(wrapped);
        this.matchElement = requireNonNull(matchElement);
        this.writeUntilElement = writeUntilElement;
        pass = writeUntilElement;
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the element name matches the marker element, this method triggers the switch
     * in passing behavior. Otherwise, it conditionally delegates to the wrapped writer
     * based on the current passing state.
     * </p>
     * <p>
     * The marker element itself is never written to the output.
     * </p>
     */
    @Override
    public void startElement(final String name, final UIComponent component) throws IOException {
        if (matchElement.equals(name)) {
            pass = false;
        } else if (pass) {
            getWrapped().startElement(name, component);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Conditionally delegates to the wrapped writer based on the current passing state.
     * </p>
     */
    @Override
    public void writeAttribute(final String name, final Object value, final String property) throws IOException {
        if (pass) {
            getWrapped().writeAttribute(name, value, property);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the element name matches the marker element, this method triggers the switch
     * in passing behavior. Otherwise, it conditionally delegates to the wrapped writer
     * based on the current passing state.
     * </p>
     * <p>
     * The marker element itself is never written to the output.
     * </p>
     */
    @Override
    public void endElement(final String name) throws IOException {
        if (matchElement.equals(name)) {
            pass = !writeUntilElement;
        } else if (pass) {
            getWrapped().endElement(name);
        }
    }
}
