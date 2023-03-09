package com.icw.ehf.cui.components.bootstrap.layout;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

/**
 * {@link Renderer} for all variants of {@link AbstractLayoutComponent}
 *
 * @author Oliver Wolff
 */
@FacesRenderer(rendererType = BootstrapFamily.LAYOUT_RENDERER,
        componentFamily = BootstrapFamily.COMPONENT_FAMILY)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class LayoutComponentRenderer extends BaseDecoratorRenderer<AbstractLayoutComponent> {

    /**
     */
    public LayoutComponentRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<AbstractLayoutComponent> writer,
            final AbstractLayoutComponent component)
        throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(component.resolveStyleClass());
        writer.withAttributeStyle(component.getStyle());
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<AbstractLayoutComponent> writer,
            final AbstractLayoutComponent component)
        throws IOException {
        writer.withEndElement(Node.DIV);
    }
}
