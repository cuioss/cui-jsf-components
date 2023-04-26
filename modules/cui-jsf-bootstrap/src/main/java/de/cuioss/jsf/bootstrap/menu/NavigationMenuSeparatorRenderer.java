package de.cuioss.jsf.bootstrap.menu;

import java.io.IOException;

import javax.faces.render.Renderer;

import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.menu.model.NavigationMenuItemSeparator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * {@link Renderer} utility for the {@link NavigationMenuItemSeparator} model.
 *
 * <h2>HTML structure</h2>
 *
 * <pre>
 * &lt;li class="divider"&gt;
 * </pre>
 *
 * @author Sven Haag
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NavigationMenuSeparatorRenderer {

    /**
     * @param writer
     * @param model
     * @param component
     * @param idExtension
     * @throws IOException
     */
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    static void render(DecoratingResponseWriter<NavigationMenuComponent> writer,
            NavigationMenuItemSeparator model,
            NavigationMenuComponent component,
            final String idExtension)
        throws IOException {
        if (!model.isRendered()) {
            return;
        }

        writer.withStartElement(Node.LI);
        writer.withClientId(idExtension);
        writer.withPassThroughAttributes();
        writer.withAttributeStyle(component.getStyle());
        writer.withStyleClass(component.getStyleClassBuilder().append(CssBootstrap.LIST_DIVIDER));

        writer.withEndElement(Node.LI);
    }
}
