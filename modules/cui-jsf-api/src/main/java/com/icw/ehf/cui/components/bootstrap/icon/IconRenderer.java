package com.icw.ehf.cui.components.bootstrap.icon;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.icon.support.IconSize;
import com.icw.ehf.cui.components.bootstrap.icon.support.IconState;
import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.impl.StyleClassBuilderImpl;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.partial.IconProvider;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

/**
 * <p>
 * Default {@link Renderer} for an {@link IconComponent}. In case of
 * {@link IconComponent#getIcon()} id <code>null</code> or does not define a
 * valid icon, regarding cui-icon contract it default to the icon
 * {@link IconProvider#FALLBACK_ICON_STRING}
 * </p>
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is cui-icon</li>
 * <li>Sizing: cui-icon-xl, cui-icon-lg,..</li>
 * <li>State: cui-icon-state-info, cui-icon-state-error,..</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY,
        rendererType = BootstrapFamily.ICON_COMPONENT_RENDERER)
public class IconRenderer extends BaseDecoratorRenderer<IconComponent> {

    /**
     *
     */
    public IconRenderer() {
        super(true);
    }

    @Override
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    protected void doEncodeEnd(FacesContext context, DecoratingResponseWriter<IconComponent> writer,
            IconComponent component)
        throws IOException {

        // Write element
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(computeStyleClass(component));
        writer.withClientIdIfNecessary();
        writer.withPassThroughAttributes();

        // write title if available
        writer.withAttributeTitle(component.resolveTitle());

        // write style attribute if available
        writer.withAttributeStyle(component.getStyle());

        writer.withEndElement(Node.SPAN);
    }

    private static StyleClassBuilder computeStyleClass(IconComponent component) {

        // Create style-class
        StyleClassBuilder styleClassBuilder = new StyleClassBuilderImpl(component.resolveIconCss());
        styleClassBuilder.append(component);

        // Consider State and size
        var iconState = IconState.getForContextState(component.resolveContextState());
        var iconSize = IconSize.getForContextSize(component.resolveContextSize());

        styleClassBuilder.append(iconState).append(iconSize);

        return styleClassBuilder;
    }

}
