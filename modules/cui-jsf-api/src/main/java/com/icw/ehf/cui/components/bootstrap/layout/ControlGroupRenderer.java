package com.icw.ehf.cui.components.bootstrap.layout;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

/**
 * Default renderer for {@link ControlGroupComponent}
 *
 * @author Oliver Wolff
 *
 */
@FacesRenderer(rendererType = BootstrapFamily.LAYOUT_CONTROL_GROUP_RENDERER,
        componentFamily = BootstrapFamily.COMPONENT_FAMILY)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class ControlGroupRenderer extends BaseDecoratorRenderer<ControlGroupComponent> {

    /**
     *
     */
    public ControlGroupRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(FacesContext context, DecoratingResponseWriter<ControlGroupComponent> writer,
            ControlGroupComponent component)
        throws IOException {
        // Open Form group wrapper
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(CssBootstrap.FORM_GROUP.getStyleClassBuilder().append(component.getStyleClass()));
        writer.withAttributeStyle(component.getStyle());
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();

        // Open Column
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(component.resolveColumnCss());
    }

    @Override
    protected void doEncodeEnd(FacesContext context, DecoratingResponseWriter<ControlGroupComponent> writer,
            ControlGroupComponent component)
        throws IOException {
        // End Column
        writer.withEndElement(Node.DIV);
        // End Form group wrapper
        writer.withEndElement(Node.DIV);
    }
}
