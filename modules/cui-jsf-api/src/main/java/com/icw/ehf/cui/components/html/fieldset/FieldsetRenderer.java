package com.icw.ehf.cui.components.html.fieldset;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.icw.ehf.cui.components.CuiFamily;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

/**
 * @author Oliver Wolff
 *
 */
@FacesRenderer(rendererType = CuiFamily.FIELDSET_RENDERER, componentFamily = CuiFamily.COMPONENT_FAMILY)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class FieldsetRenderer extends BaseDecoratorRenderer<FieldsetComponent> {

    /**
     *
     */
    public FieldsetRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<FieldsetComponent> writer,
            final FieldsetComponent component)
        throws IOException {

        writer.withStartElement(Node.FIELDSET);
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();
        writer.withAttributeStyle(component.getStyle());
        writer.withStyleClass(component.getStyleClass());

        var legend = component.resolveLegend();

        if (null != legend) {
            writer.withStartElement(Node.LEGEND).withTextContent(legend, component.isLegendEscape())
                    .withEndElement(Node.LEGEND);
        }
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<FieldsetComponent> writer,
            final FieldsetComponent component)
        throws IOException {
        writer.withEndElement(Node.FIELDSET);
    }
}
