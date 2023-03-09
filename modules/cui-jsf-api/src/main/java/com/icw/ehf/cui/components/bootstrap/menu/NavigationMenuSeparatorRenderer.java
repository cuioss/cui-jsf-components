package com.icw.ehf.cui.components.bootstrap.menu;

import java.io.IOException;

import javax.faces.render.Renderer;

import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.components.bootstrap.menu.model.NavigationMenuItemSeparator;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

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
 * @author Sven Haag, Sven Haag
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
