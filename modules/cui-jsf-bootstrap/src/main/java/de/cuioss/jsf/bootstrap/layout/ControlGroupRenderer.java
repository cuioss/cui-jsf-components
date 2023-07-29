package de.cuioss.jsf.bootstrap.layout;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;

/**
 * Default renderer for {@link ControlGroupComponent}
 *
 * @author Oliver Wolff
 *
 */
@FacesRenderer(rendererType = BootstrapFamily.LAYOUT_CONTROL_GROUP_RENDERER, componentFamily = BootstrapFamily.COMPONENT_FAMILY)
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
            ControlGroupComponent component) throws IOException {
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
            ControlGroupComponent component) throws IOException {
        // End Column
        writer.withEndElement(Node.DIV);
        // End Form group wrapper
        writer.withEndElement(Node.DIV);
    }
}
