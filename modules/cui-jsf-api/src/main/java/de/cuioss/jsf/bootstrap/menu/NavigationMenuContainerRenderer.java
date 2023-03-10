package de.cuioss.jsf.bootstrap.menu;

import java.io.IOException;

import javax.faces.component.html.HtmlOutcomeTargetLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import de.cuioss.jsf.bootstrap.menu.model.NavigationMenuItemContainer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * {@link Renderer} utility for the {@link NavigationMenuItemContainer} model.
 *
 * Renders a bootstrap conform navigation menu structure.
 *
 * <h2>HTML structure</h2>
 *
 * <pre>
 * &lt;li class="dropdown"&gt;
 *   &lt;a href="" class="dropdown-toggle" data-toggle="dropdown"&gt;
 *     &lt;span class="cui-icon cui-icon-tag"/&gt;
 *     &lt;span&gt;label&lt;/span&gt;
 *     &lt;span class="cui-icon cui-icon-triangle_s"/&gt;
 *   &lt;/a&gt;
 *   &lt;ul class="dropdown-menu"&gt;
 *     ...childs...
 *   &lt;/ul&gt;
 * &lt;/li&gt;
 * </pre>
 *
 * @author Sven Haag, Sven Haag
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class NavigationMenuContainerRenderer {

    private static final String ICON_TOP_MENU = "cui-icon-triangle_s";
    private static final String ICON_SUB_MENU = "cui-icon-triangle_e";

    /**
     * Write beginning tags.
     *
     * @param context
     * @param writer
     * @param model
     * @param component
     * @param parentIsContainer
     * @param idExtension
     * @throws IOException
     */
    static void renderBegin(final FacesContext context,
            final DecoratingResponseWriter<NavigationMenuComponent> writer,
            final NavigationMenuItemContainer model,
            final NavigationMenuComponent component,
            final boolean parentIsContainer,
            final String idExtension)
        throws IOException {
        if (!model.isRendered()) {
            return;
        }

        writer.withStartElement(Node.LI);
        writer.withClientId(idExtension);
        writer.withPassThroughAttributes();
        writer.withAttributeStyle(component.getStyle());
        writer.withStyleClass(component.getStyleClassBuilder()
                .append(CssBootstrap.LIST_DROPDOWN)
                .appendIfTrue(CssBootstrap.LIST_DROP_DOWN_SUBMENU, parentIsContainer));

        if (model.isActive()) {
            writer.withAttribute(AttributeName.DATA_ITEM_ACTIVE, Boolean.TRUE.toString());
        }

        renderCmdLink(context, model, parentIsContainer);

        writer.withStartElement(Node.UL);
        writer.withStyleClass(CssBootstrap.LIST_DROP_DOWN_MENU);
    }

    /**
     * Write closing tags.
     *
     * @param writer
     * @param model
     * @throws IOException
     */
    static void renderEnd(final DecoratingResponseWriter<NavigationMenuComponent> writer,
            final NavigationMenuItemContainer model)
        throws IOException {
        if (!model.isRendered()) {
            return;
        }
        writer.withEndElement(Node.UL);
        writer.withEndElement(Node.LI);
    }

    private static void renderCmdLink(final FacesContext context,
            final NavigationMenuItemContainer model,
            final boolean parentIsContainer)
        throws IOException {

        final var application = context.getApplication();

        final var commandLink =
            (HtmlOutcomeTargetLink) application.createComponent(HtmlOutcomeTargetLink.COMPONENT_TYPE);
        commandLink.setDisabled(model.isDisabled());
        commandLink.setStyleClass(CssBootstrap.LIST_DROP_DOWN_TOGGLE.getStyleClass());
        commandLink.getPassThroughAttributes().put("data-toggle", "dropdown");

        // Title
        if (model.isTitleAvailable()) {
            commandLink.setTitle(model.getResolvedTitle());
        }

        // Item Icon
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

        // Collapse Icon
        final var caret =
            (IconComponent) context.getApplication().createComponent(BootstrapFamily.ICON_COMPONENT);
        if (parentIsContainer) {
            caret.setIcon(ICON_SUB_MENU);
        } else {
            caret.setIcon(ICON_TOP_MENU);
        }
        commandLink.getChildren().add(caret);

        commandLink.encodeAll(context);
    }
}
