package com.icw.ehf.cui.core.api.components.renderer;

import java.io.IOException;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.core.api.components.html.Node;

@SuppressWarnings({ "javadoc", "resource" })
public class MockDecoratorRenderer extends BaseDecoratorRenderer<HtmlInputText> {

    public MockDecoratorRenderer(final boolean renderChildren) {
        super(renderChildren);
    }

    public MockDecoratorRenderer() {
        this(true);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<HtmlInputText> writer, final HtmlInputText component)
        throws IOException {
        writer.withStartElement(Node.DIV);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<HtmlInputText> writer, final HtmlInputText component)
        throws IOException {
        writer.withEndElement(Node.DIV);
    }

}
