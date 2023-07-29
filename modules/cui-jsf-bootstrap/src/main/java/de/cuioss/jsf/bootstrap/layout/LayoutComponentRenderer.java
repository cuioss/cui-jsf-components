package de.cuioss.jsf.bootstrap.layout;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;

/**
 * {@link Renderer} for all variants of {@link AbstractLayoutComponent}
 *
 * @author Oliver Wolff
 */
@FacesRenderer(rendererType = BootstrapFamily.LAYOUT_RENDERER, componentFamily = BootstrapFamily.COMPONENT_FAMILY)
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
            final DecoratingResponseWriter<AbstractLayoutComponent> writer, final AbstractLayoutComponent component)
            throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(component.resolveStyleClass());
        writer.withAttributeStyle(component.getStyle());
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<AbstractLayoutComponent> writer, final AbstractLayoutComponent component)
            throws IOException {
        writer.withEndElement(Node.DIV);
    }
}
