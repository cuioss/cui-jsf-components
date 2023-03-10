package de.cuioss.jsf.bootstrap.notification;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.events.ModelPayloadEvent;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.api.components.util.ComponentUtility;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;

/**
 * <p>
 * Default {@link Renderer} for {@link NotificationBoxComponent}
 * </p>
 *
 * @author Matthias Walliczek
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY,
        rendererType = BootstrapFamily.NOTIFICATION_BOX_RENDERER)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class NotificationBoxRenderer extends BaseDecoratorRenderer<NotificationBoxComponent> {

    /**
     * default constructor.
     */
    public NotificationBoxRenderer() {
        super(false);
    }

    private static String getAlertClassForState(final ContextState state) {
        switch (state) {
            case SUCCESS:
                return "alert-success";
            case DANGER:
                return "alert-danger";
            case INFO:
                return "alert-info";
            case WARNING:
                return "alert-warning";
            // $CASES-OMITTED$
            default:
                return "";
        }
    }

    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<NotificationBoxComponent> writer,
            final NotificationBoxComponent component)
        throws IOException {
        writer.withStartElement(Node.DIV);
        if (component.isDismissible() && null != component.getDismissListener()) {
            writer.withClientId();
        } else {
            writer.withClientIdIfNecessary();
        }
        var styleClassBuilder = CssBootstrap.ALERT.getStyleClassBuilder().append(component)
                .append(getAlertClassForState(component.resolveContextState()));
        if (component.isDismissible()) {
            styleClassBuilder.append(CssBootstrap.ALERT_DISMISSIBLE);
        }
        writer.withStyleClass(styleClassBuilder);
        writer.withAttributeStyle(component.getStyle());
        writer.withAttribute(AttributeName.ROLE, "alert");
        writer.withPassThroughAttributes();
        if (component.isDismissible()) {
            writer.withStartElement(Node.BUTTON);
            writer.withAttribute(AttributeName.TYPE, "button");
            writer.withStyleClass("close");
            writer.withAttribute(AttributeName.DATA_DISMISS, "alert");
            if (null != component.getDismissListener()) {
                writer.withAttribute(AttributeName.DATA_DISMISS_LISTENER, "true");
                ComponentUtility.checkIsNestedInForm(component);
            }
            writer.withAttribute(AttributeName.ARIA_LABEL, component.resolveCloseButtonTitle());
            writer.withAttributeTitle(component.resolveCloseButtonTitle());
            writer.withStartElement(Node.SPAN);
            writer.withAttribute(AttributeName.ARIA_HIDDEN, "true");
            writer.withTextContent("&#215;", false);
            writer.withEndElement(Node.SPAN);
            writer.withEndElement(Node.BUTTON);
        }

        // write content if available
        writer.withTextContent(component.resolveContent(), component.getContentEscape());
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<NotificationBoxComponent> writer,
            final NotificationBoxComponent component)
        throws IOException {

        writer.withEndElement(Node.DIV);
    }

    @Override
    protected void doDecode(final FacesContext context,
            final ComponentWrapper<NotificationBoxComponent> componentWrapper) {
        if (!componentWrapper.getWrapped().isDismissible()) {
            return;
        }
        var notificationBoxComponent = componentWrapper.getWrapped();
        notificationBoxComponent.setDismissed(true);
        var payload = notificationBoxComponent.getModel();
        if (null == payload) {
            // Fallback to contentValue
            payload = notificationBoxComponent.getContentValue();
        }
        if (null == payload) {
            // Fallback to resolved content
            payload = notificationBoxComponent.resolveContent();
        }
        notificationBoxComponent.queueEvent(new ModelPayloadEvent(notificationBoxComponent, payload));
    }

}
