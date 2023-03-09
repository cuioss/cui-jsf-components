package com.icw.ehf.cui.components.bootstrap.icon;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.icon.support.CssMimeTypeIcon;
import com.icw.ehf.cui.components.bootstrap.icon.support.IconSize;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

/**
 * <p>
 * Default {@link Renderer} for {@link MimeTypeIconComponent}
 * </p>
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css classes are cui-icon-stack and cui-mime-type</li>
 * <li>Sizing: cui-icon-xl, cui-icon-lg,..</li>
 * <li>The actual rendering model is documented in the
 * <a href="https://wiki.icw.int/x/44HzAg">Wiki</a></li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY,
        rendererType = BootstrapFamily.MIME_TYPE_ICON_COMPONENT_RENDERER)
public class MimeTypeIconRenderer extends BaseDecoratorRenderer<MimeTypeIconComponent> {

    /**
     *
     */
    public MimeTypeIconRenderer() {
        super(true);
    }

    @Override
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    protected void doEncodeEnd(FacesContext context, DecoratingResponseWriter<MimeTypeIconComponent> writer,
            MimeTypeIconComponent component)
        throws IOException {

        // span wrapper
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(CssMimeTypeIcon.CUI_STACKED_ICON.getStyleClassBuilder().append(component)
                .append(IconSize.getForContextSize(component.resolveContextSize())));
        writer.withAttributeStyle(component.getStyle());
        writer.withAttributeTitle(component.resolveTitle());
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();

        // Layer 1
        writer.withStartElement(Node.ITALIC);
        writer.withStyleClass(CssMimeTypeIcon.CUI_STACKED_BASE_STRING.getStyleClassBuilder()
                .append(CssMimeTypeIcon.CUI_MIME_TYPE_FOLDER));
        writer.withEndElement(Node.ITALIC);

        // Layer 2
        writer.withStartElement(Node.ITALIC);
        writer.withStyleClass(
                CssMimeTypeIcon.CUI_STACKED_BASE_STRING.getStyleClassBuilder().append(component.getDecoratorClass()));
        writer.withEndElement(Node.ITALIC);

        var mimeTypeIcon = component.resolveMimeTypeIcon();
        // Layer 3
        writer.withStartElement(Node.ITALIC);
        writer.withStyleClass(CssMimeTypeIcon.CUI_STACKED_BASE_STRING.getStyleClassBuilder()
                .append(CssMimeTypeIcon.CUI_MIME_TYPE_PLACEHOLDER).append(mimeTypeIcon.getPlaceholder()));
        writer.withEndElement(Node.ITALIC);

        // Layer 4
        writer.withStartElement(Node.ITALIC);
        writer.withStyleClass(
                CssMimeTypeIcon.CUI_STACKED_BASE_STRING.getStyleClassBuilder().append(mimeTypeIcon.getIconClass()));
        writer.withEndElement(Node.ITALIC);

        // end span wrapper
        writer.withEndElement(Node.SPAN);
    }

}
