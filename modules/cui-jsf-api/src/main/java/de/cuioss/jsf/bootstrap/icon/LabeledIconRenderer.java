package de.cuioss.jsf.bootstrap.icon;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.css.AlignHolder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;

/**
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is cui-labeled-icon</li>
 * <li>The label class is cui-labeled-icon-text</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY,
        rendererType = BootstrapFamily.LABELED_ICON_COMPONENT_RENDERER)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class LabeledIconRenderer extends BaseDecoratorRenderer<LabeledIconComponent> {

    /**
     *
     */
    public LabeledIconRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<LabeledIconComponent> writer,
            final LabeledIconComponent component)
        throws IOException {
        // Write wrapper span
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(CssCuiBootstrap.LABELED_ICON_WRAPPER.getStyleClassBuilder().append(component));
        writer.withAttributeStyle(component.getStyle());
        writer.withClientIdIfNecessary();
        writer.withAttributeTitle(component.resolveTitle());
        writer.withPassThroughAttributes();

        if (AlignHolder.RIGHT.equals(component.resolveIconAlign())) {
            writeLabel(writer, component);
            writeIcon(writer, component);
        } else {
            writeIcon(writer, component);
            writeLabel(writer, component);
        }

        // End wrapper span
        writer.withEndElement(Node.SPAN);
    }

    /**
     * @param writer
     * @param component
     * @throws IOException
     */
    private static void writeIcon(final DecoratingResponseWriter<LabeledIconComponent> writer,
            final LabeledIconComponent component)
        throws IOException {
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(component.resolveIconCss());
        writer.withEndElement(Node.SPAN);
    }

    /**
     * @param writer
     * @param component
     * @throws IOException
     */
    private static void writeLabel(final DecoratingResponseWriter<LabeledIconComponent> writer,
            final LabeledIconComponent component)
        throws IOException {
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(CssCuiBootstrap.LABELED_ICON_TEXT);
        writer.withTextContent(component.resolveLabel(), component.isLabelEscape());
        writer.withEndElement(Node.SPAN);

    }
}
