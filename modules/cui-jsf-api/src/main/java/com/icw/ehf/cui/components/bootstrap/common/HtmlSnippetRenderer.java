package com.icw.ehf.cui.components.bootstrap.common;

import java.io.IOException;

import javax.faces.component.UIComponent;

import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

import lombok.experimental.UtilityClass;

/**
 * @author Oliver Wolff
 *
 */
@UtilityClass
public final class HtmlSnippetRenderer {

    /**
     * Renders a simple close button in the bootstrap conform css at the current position of the
     * response writer
     *
     * @param writer
     * @param dataDismissAttribute
     * @throws IOException
     */
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    public static void renderCloseButton(final DecoratingResponseWriter<? extends UIComponent> writer,
            final String dataDismissAttribute)
        throws IOException {
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
