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
package de.cuioss.jsf.bootstrap.common;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import jakarta.faces.component.UIComponent;
import lombok.experimental.UtilityClass;

import java.io.IOException;

/**
 * <p>
 * Utility class that provides helper methods for rendering common Bootstrap HTML snippets
 * consistently throughout the application. This class enables standardized rendering of
 * commonly used UI elements without duplicating code.
 * </p>
 * 
 * <p>
 * The rendered HTML snippets follow Bootstrap's design patterns and accessibility guidelines,
 * ensuring consistent appearance and behavior across the application.
 * </p>
 *
 * @author Oliver Wolff
 *
 */
@UtilityClass
public final class HtmlSnippetRenderer {

    /**
     * <p>
     * Renders a Bootstrap-styled close button (×) at the current position of the response writer.
     * This close button follows Bootstrap's standard design pattern and includes appropriate
     * ARIA attributes for accessibility.
     * </p>
     * 
     * <p>The rendered HTML structure is:</p>
     * <pre>
     * &lt;button aria-label="Close" data-dismiss="[dataDismissAttribute]" type="button" class="close"&gt;
     *   &lt;span aria-hidden="true"&gt;&amp;#xD7;&lt;/span&gt;
     * &lt;/button&gt;
     * </pre>
     *
     * <p>Typically used in alerts, modals, and other dismissible components.</p>
     *
     * @param writer The {@link DecoratingResponseWriter} to write the HTML output
     * @param dataDismissAttribute The value for the data-dismiss attribute (e.g., "modal", "alert")
     * @throws IOException If an I/O error occurs during rendering
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
