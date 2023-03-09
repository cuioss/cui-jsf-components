package com.icw.ehf.cui.components.bootstrap.taginput;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.application.ResourceDependency;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.tag.TagComponent;
import com.icw.ehf.cui.components.bootstrap.tag.support.TagHelper;
import com.icw.ehf.cui.core.api.application.locale.LocaleProducerAccessor;
import com.icw.ehf.cui.core.api.components.JsfHtmlComponent;
import com.icw.ehf.cui.core.api.components.css.ContextSize;
import com.icw.ehf.cui.core.api.components.css.ContextState;
import com.icw.ehf.cui.core.api.components.javascript.Selectize;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;
import com.icw.ehf.cui.core.api.components.util.ComponentWrapper;
import com.icw.ehf.cui.core.api.security.CuiSanitizer;

import de.cuioss.tools.string.MoreStrings;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;

/**
 * Render and decodes a selectize.js based TagInput.
 * <p>
 * All values are considered to be of type {@code Set<ConceptKeyType>}:
 * {@link TagInputComponent#getValue()} and
 * {@link TagInputComponent#setValue(Object)}
 * </p>
 * <h2>Rendering</h2>
 * <ul>
 * <li>Creates an {@code <input type="text">} with the clientId</li>
 * <li>For each value found it writes {@link ConceptKeyType#getIdentifier()} as a
 * colon separated list into the value attributes. The matching to labels will
 * done by the JavaScript</li>
 * <li>It uses pass through data attributes to configure selectize.js</li>
 * </ul>
 * <h2>Decoding</h2>
 * <ul>
 * <li>If no value is set for the clientId it calls
 * {@link TagInputComponent#setSubmittedValue(Object)} with <code>null</code>
 * </li>
 * <li>If there is a value available it splits them using "," as separator</li>
 * <li>Each element will be checked whether it is initially provided
 * {@link TagInputComponent#getSourceSet()} or
 * {@link TagInputComponent#getClientCreated()} If so the corresponding
 * {@link ConceptKeyType} will added to the resulting {@link Set}</li>
 * <li>In case the element can not be matched to the ones above it will checked
 * whether it starts with {@link Selectize#CLIENT_CREATED_SUFFIX}. If so a new
 * {@link ConceptKeyTypeImpl} will be created with the given key as
 * {@link ConceptKeyType#getIdentifier()} and the name part as
 * {@link ConceptKeyType#getResolved(Locale)}. The element will now be added to the
 * resulting {@link Set} and to {@link TagInputComponent#getClientCreated()}
 * </li>
 * <li>If none of the above the decode method will throw an
 * {@link IllegalArgumentException}</li>
 * <li>the resulting {@link Set} will finally set as submittedValue after conversion using
 * {@link ConceptKeyStringConverter}</li>
 * </ul>
 * <p>
 * It implicitly sanitizes all input and output using the
 * {@link CuiSanitizer#PLAIN_TEXT}
 * </p>
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
@ResourceDependency(library = "thirdparty.js", name = "selectize.js", target = "head")
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY,
        rendererType = BootstrapFamily.TAG_INPUT_COMPONENT_RENDERER)
public class TagInputRenderer extends BaseDecoratorRenderer<TagInputComponent> {

    private final LocaleProducerAccessor localeProducerAccessor = new LocaleProducerAccessor();

    /***/
    public TagInputRenderer() {
        super(false);
    }

    /**
     * Extracts submitted value from request parameters and use javax.faces.convert.Converter to
     * convert the value from
     * String to a ConceptKeyType collection. Sets the submitted value to TagInputComponent.
     *
     * @param context FacesContext for the request we are processing
     * @param componentWrapper type-safe component
     */
    @Override
    protected void doDecode(final FacesContext context, final ComponentWrapper<TagInputComponent> componentWrapper) {
        final var value = context.getExternalContext().getRequestParameterMap().get(componentWrapper.getClientId());
        final var tagInput = componentWrapper.getWrapped();

        if (tagInput.isDisabled()) {
            return;
        }
        if (MoreStrings.isEmpty(value)) {
            tagInput.setSubmittedValue("");
            return;
        }

        tagInput.setSubmittedValue(value);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<TagInputComponent> writer,
            final TagInputComponent component)
        throws IOException {

        if (component.isDisabled()) {
            encodeDisabled(context, writer, component);
        } else {
            encodeEnabled(context, component);
        }
    }

    private void encodeEnabled(final FacesContext context,
            final TagInputComponent component)
        throws IOException {
        JsfHtmlComponent.HTMLINPUT.renderer(context).encodeBegin(context, component);
        JsfHtmlComponent.HTMLINPUT.renderer(context).encodeEnd(context, component);
    }

    private void encodeDisabled(final FacesContext context,
            final DecoratingResponseWriter<TagInputComponent> writer,
            final TagInputComponent component)
        throws IOException {
        TagHelper.writeDisabled(context, writer, createTags(component.getValue()), component.getStyle(),
                component.getStyleClass());
    }

    private List<TagComponent> createTags(final Collection<ConceptKeyType> values) {
        return TagHelper.createFromConceptKeys(
                null != values ? new TreeSet<>(values) : Collections.emptySortedSet(),
                localeProducerAccessor.getValue().getLocale(),
                true,
                ContextSize.LG.name(),
                ContextState.DEFAULT.name());
    }
}
