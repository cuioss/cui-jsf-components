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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import lombok.Getter;

/**
 * Used for filtering calls to the {@link ResponseWriter}. See
 * {@link ConditionalResponseWriter#ConditionalResponseWriter(ResponseWriter, String, boolean)}
 * for details. It is not reentrant.
 *
 * @author Oliver Wolff
 */
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class ConditionalResponseWriter extends ResponseWriterWrapper {

    @Getter
    private final ResponseWriter wrapped;

    private final String matchElement;

    @Getter
    private final boolean writeUntilElement;

    private boolean pass;

    /**
     * Constructor.
     *
     * @param wrapped           the wrapped {@link ResponseWriter}, must not be null
     * @param matchElement      Identifying the marker element on which the filter
     *                          will be switched. The element itself will never be
     *                          rendered, must not be null
     * @param writeUntilElement if it is <code>true</code> the writer will pass the
     *                          calls until the the element defined with elementName
     *                          is called. From that point on the calls will be
     *                          filtered. elementName will never be rendered. If it
     *                          is <code>false</code> it is the other way round.
     */
    public ConditionalResponseWriter(final ResponseWriter wrapped, final String matchElement,
            final boolean writeUntilElement) {
        super(wrapped);
        this.wrapped = requireNonNull(wrapped);
        this.matchElement = requireNonNull(matchElement);
        this.writeUntilElement = writeUntilElement;
        pass = writeUntilElement;

    }

    @Override
    public void startElement(final String name, final UIComponent component) throws IOException {
        if (matchElement.equals(name)) {
            pass = false;
        } else if (pass) {
            getWrapped().startElement(name, component);
        }
    }

    @Override
    public void writeAttribute(final String name, final Object value, final String property) throws IOException {
        if (pass) {
            getWrapped().writeAttribute(name, value, property);
        }
    }

    @Override
    public void endElement(final String name) throws IOException {
        if (matchElement.equals(name)) {
            pass = !writeUntilElement;
        } else if (pass) {
            getWrapped().endElement(name);
        }
    }
}
