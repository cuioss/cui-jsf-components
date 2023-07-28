package de.cuioss.jsf.bootstrap.menu;

import java.io.IOException;

import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemExternalSingle;
import de.cuioss.jsf.api.components.model.menu.NavigationMenuItemSingle;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import lombok.experimental.UtilityClass;

/**
 * {@link Renderer} utility for the {@link NavigationMenuItemSingle} model.
 *
 * <h2>HTML structure</h2>
 *
 * <pre>
 * &lt;li&gt;
 *   &lt;a href="href"&gt;
 *     &lt;span class="cui-icon cui-icon-tag"/&gt;
 *     &lt;span&gt;label&lt;/span&gt;
 *   &lt;/a&gt;
 * &lt;/li&gt;
 * </pre>
 *
 * @author Matthias Walliczek
 */
@UtilityClass
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class NavigationMenuExternalSingleRenderer {

    /**
     * @param context
     * @param writer
     * @param model
     * @param component
     * @param idExtension
     * @throws IOException
     */
    static void render(final FacesContext context, final DecoratingResponseWriter<NavigationMenuComponent> writer,
            final NavigationMenuItemExternalSingle model, final NavigationMenuComponent component,
            final String idExtension) throws IOException {
        if (!model.isRendered()) {
            return;
        }

        writer.withStartElement(Node.LI);
        writer.withClientId(idExtension);
        writer.withPassThroughAttributes();
        writer.withAttributeStyle(component.getStyle());
        writer.withStyleClass(component.getStyleClassBuilder());
        if (model.isActive()) {
            writer.withAttribute(AttributeName.DATA_ITEM_ACTIVE, Boolean.TRUE.toString());
        }

        renderCmdLink(context, model);

        writer.withEndElement(Node.LI);
    }

    private static void renderCmdLink(final FacesContext context, final NavigationMenuItemExternalSingle model)
            throws IOException {
        final var application = context.getApplication();

        final var commandLink = (HtmlOutputLink) application.createComponent(HtmlOutputLink.COMPONENT_TYPE);
        commandLink.setValue(model.getHRef());
        commandLink.setTarget(model.getTarget());

        // Title
        if (model.isTitleAvailable()) {
            commandLink.setTitle(model.getResolvedTitle());
        }

        // Icon
        if (null != model.getIconStyleClass()) {
            final var icon = (IconComponent) application.createComponent(BootstrapFamily.ICON_COMPONENT);
            icon.setIcon(model.getIconStyleClass());
            commandLink.getChildren().add(icon);
        }

        // Label
        final var outputText = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        outputText.setStyleClass(CssCuiBootstrap.CUI_NAVIGATION_MENU_TEXT.getStyleClass());
        outputText.setValue(model.getResolvedLabel());
        commandLink.getChildren().add(outputText);

        commandLink.encodeAll(context);
    }
}
