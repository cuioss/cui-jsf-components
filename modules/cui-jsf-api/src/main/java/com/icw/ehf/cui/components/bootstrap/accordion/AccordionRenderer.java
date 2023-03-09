package com.icw.ehf.cui.components.bootstrap.accordion;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.components.bootstrap.layout.BootstrapPanelComponent;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.AttributeValue;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

/**
 * Renderer for {@linkplain AccordionComponent}.
 *
 * @author Matthias Walliczek
 * @author Sven Haag, Sven Haag
 */
@FacesRenderer(rendererType = BootstrapFamily.ACCORDION_RENDERER,
        componentFamily = BootstrapFamily.COMPONENT_FAMILY)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class AccordionRenderer extends BaseDecoratorRenderer<AccordionComponent> {

    /***/
    public AccordionRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<AccordionComponent> writer,
            final AccordionComponent component)
        throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withStyleClass(CssBootstrap.PANEL_GROUP.getStyleClassBuilder().append(component.getStyleClass()));
        writer.withAttributeStyle(component.getStyle());
        writer.withClientId();
        writer.withPassThroughAttributes();
        writer.withAttribute(AttributeName.ROLE, AttributeValue.TABLIST);
        writer.withAttribute(AttributeName.ARIA_MULTISELECTABLE, String.valueOf(component.isMultiselectable()));
    }

    @Override
    protected void doEncodeChildren(final FacesContext context,
            final DecoratingResponseWriter<AccordionComponent> writer, final AccordionComponent component)
        throws IOException {

        final var activeIndexes = component.resolveActiveIndexes();

        var i = 0;
        for (UIComponent currentChild : component.getChildren()) {
            if (currentChild instanceof BootstrapPanelComponent) {
                var panel = (BootstrapPanelComponent) currentChild;
                if (!component.isMultiselectable()) {
                    // TODO deliver own JS that collects the toggle state/s and updates with a
                    // single AJAX request.
                    panel.setAsyncUpdate(false);
                    panel.setDeferred(false);
                    panel.setDataParent(component.getClientId());
                }

                // take precedence over other collapse attributes
                panel.setCollapsible(true);
                panel.setCollapsed(!activeIndexes.contains(i));
                i++;

                panel.encodeAll(context);
            }
        }
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<AccordionComponent> writer,
            final AccordionComponent component)
        throws IOException {
        writer.withEndElement(Node.DIV);
    }
}
