package de.cuioss.jsf.bootstrap.button;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.api.components.renderer.ElementReplacingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;

/**
 * <h2>Rendering</h2>
 * <p>
 * This {@link Renderer} uses the concrete implementation specific Renderer for
 * {@code h:commandButton} accessed by accessed by
 * {@link JsfHtmlComponent#BUTTON}. This is used for creating the start element
 * of the element including all attributes like onclick,.. . On the fly the
 * {@code input} element will be replaced by {@code button}. This is done by
 * passing an instance of {@link ElementReplacingResponseWriter}. The
 * {@link Renderer#decode(FacesContext, UIComponent)} will be passed to the
 * specific renderer as well.
 * </p>
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is btn</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.COMMAND_BUTTON_RENDERER)
public class CommandButtonRenderer extends BaseDecoratorRenderer<CommandButton> {

    /**
     *
     */
    public CommandButtonRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<CommandButton> writer,
            final CommandButton component) throws IOException {
        var wrapped = ElementReplacingResponseWriter.createWrappedReplacingResonseWriter(context, "input", "button",
                true);
        JsfHtmlComponent.COMMAND_BUTTON.renderer(context).encodeBegin(wrapped, component);

        if (component.isDisplayIconLeft()) {
            var icon = IconComponent.createComponent(context);
            icon.setIcon(component.getIcon());
            icon.encodeAll(context);
        }
        var label = component.resolveLabel();
        if (null != label) {
            var output = JsfHtmlComponent.SPAN.component(context);
            output.setValue(label);
            output.setEscape(component.isLabelEscape());
            output.setStyleClass(CssBootstrap.BUTTON_TEXT.getStyleClass());
            output.encodeAll(context);
        }
        if (component.isDisplayIconRight()) {
            var icon = IconComponent.createComponent(context);
            icon.setIcon(component.getIcon());
            icon.encodeAll(context);
        }
    }

    @Override
    public void decode(final FacesContext context, final UIComponent component) {
        JsfHtmlComponent.COMMAND_BUTTON.renderer(context).decode(context, component);
    }

    @Override
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<CommandButton> writer,
            final CommandButton component) throws IOException {
        writer.withEndElement(Node.BUTTON);
    }
}
