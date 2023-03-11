package de.cuioss.jsf.bootstrap.menu;

import java.io.IOException;
import java.util.Map.Entry;

import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutcomeTargetLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.icon.IconComponent;
import de.cuioss.jsf.bootstrap.menu.model.NavigationMenuItemSingle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * {@link Renderer} utility for the {@link NavigationMenuItemSingle} model.
 *
 * <h2>HTML structure</h2>
 *
 * <pre>
 * &lt;li&gt;
 *   &lt;a href="outcome"&gt;
 *     &lt;span class="cui-icon cui-icon-tag"/&gt;
 *     &lt;span&gt;label&lt;/span&gt;
 *   &lt;/a&gt;
 * &lt;/li&gt;
 * </pre>
 *
 * @author Sven Haag
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class NavigationMenuSingleRenderer {

    /**
     * @param context
     * @param writer
     * @param model
     * @param component
     * @param idExtension
     * @throws IOException
     */
    static void render(final FacesContext context,
            final DecoratingResponseWriter<NavigationMenuComponent> writer,
            final NavigationMenuItemSingle model,
            final NavigationMenuComponent component,
            final String idExtension)
        throws IOException {
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

    private static void renderCmdLink(final FacesContext context,
            final NavigationMenuItemSingle model)
        throws IOException {
        final var application = context.getApplication();

        final var commandLink =
            (HtmlOutcomeTargetLink) application.createComponent(HtmlOutcomeTargetLink.COMPONENT_TYPE);
        commandLink.setOutcome(model.getOutcome());
        commandLink.setOnclick(model.getOnClickAction());// TODO raus
        commandLink.setDisabled(model.isDisabled());
        commandLink.setTarget(model.getTarget());

        // Output params
        for (final Entry<String, String> current : model.getOutcomeParameter().entrySet()) {
            final var param = new UIParameter();
            param.setName(current.getKey());
            param.setValue(current.getValue());
            commandLink.getChildren().add(param);
        }

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
