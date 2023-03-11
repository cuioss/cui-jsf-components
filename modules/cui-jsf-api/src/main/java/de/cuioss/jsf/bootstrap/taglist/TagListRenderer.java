package de.cuioss.jsf.bootstrap.taglist;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.faces.view.facelets.Tag;

import de.cuioss.jsf.api.application.locale.LocaleProducerAccessor;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.tag.TagComponent;
import de.cuioss.jsf.bootstrap.tag.support.TagHelper;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;

/**
 * <p>
 * Default {@link Renderer} for {@link TagListComponent}
 * </p>
 * <h2>Summary</h2>
 * <p>
 * Renders a number of {@link Tag}s as a a list. The tags are created from the the given
 * {@link Collection} of {@link ConceptKeyType}, see attributes #value. If you need fine grained
 * control
 * use {@link TagComponent} directly. The attributes #state, #size and #contentEscape will be passed
 * through to the used {@link TagComponent}. More information and examples can be found in the
 * <a href=
 * "https://cuioss.de/cui-reference-documentation/faces/pages/documentation/cui_components/demo/tag.jsf"
 * >Reference Documentation</a>
 * </p>
 * <h2>Rendering</h2>
 * <p>
 * The tags will be created as list in a {@code
 *
<ul class="list-inline">
 * }
 * <h2>Styling</h2>
 * <ul>
 * <li>The marker css class is "list-inline" for the surrounding ul</li>
 * <li>Sizing: Will be passed through to {@link TagComponent}</li>
 * <li>State: Will be passed through to {@link TagComponent}</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY,
        rendererType = BootstrapFamily.TAG_LIST_COMPONENT_RENDERER)
public class TagListRenderer extends BaseDecoratorRenderer<TagListComponent> {

    /**
     *
     */
    public TagListRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<TagListComponent> writer,
            final TagListComponent component)
        throws IOException {
        TagHelper.writeDisabled(context, writer, createTagChildren(component), component.getStyle(),
                component.getStyleClass());
    }

    /**
     * @param component
     * @return
     */
    private static List<TagComponent> createTagChildren(final TagListComponent component) {
        final var contextSize = component.getSize();
        final var contextState = component.getState();
        final var locale = new LocaleProducerAccessor().getValue().getLocale();
        final var contentEscape = component.getContentEscape();

        return TagHelper.createFromConceptKeys(TagHelper.getValueAsSet(component.getValue()), locale, contentEscape,
                contextSize,
                contextState);
    }
}
