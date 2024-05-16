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
package de.cuioss.jsf.bootstrap.common;

import java.io.IOException;

import jakarta.faces.component.UIComponent;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import lombok.experimental.UtilityClass;

/**
 * @author Oliver Wolff
 *
 */
@UtilityClass
public final class HtmlSnippetRenderer {

    /**
     * Renders a simple close button in the bootstrap conform css at the current
     * position of the response writer
     *
     * @param writer
     * @param dataDismissAttribute
     * @throws IOException
     */
    public static void renderCloseButton(final DecoratingResponseWriter<? extends UIComponent> writer,
            final String dataDismissAttribute) throws IOException {
        writer.withStartElement(Node.BUTTON);
        writer.withAttribute(AttributeName.ARIA_LABEL, AttributeValue.ARIA_CLOSE);
        writer.withAttribute(AttributeName.DATA_DISMISS, dataDismissAttribute);
        writer.withAttribute(AttributeName.TYPE, AttributeValue.INPUT_BUTTON);
        writer.withStyleClass(CssBootstrap.BUTTON_CLOSE);

        // Containing span
        writer.withStartElement(Node.SPAN);
        writer.withAttribute(AttributeName.ARIA_HIDDEN, AttributeValue.TRUE);
        writer.withTextContent("&#xD7;", false);
        writer.withEndElement(Node.SPAN);

        // End Button
        writer.withEndElement(Node.BUTTON);
    }
}
