package com.icw.ehf.cui.components.bootstrap.layout.input;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.components.bootstrap.CssBootstrap;
import com.icw.ehf.cui.components.bootstrap.common.partial.ColumnCssResolver;
import com.icw.ehf.cui.components.bootstrap.layout.LayoutMode;
import com.icw.ehf.cui.components.bootstrap.layout.messages.CuiMessageComponent;
import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.StyleClassProvider;
import com.icw.ehf.cui.core.api.components.css.impl.StyleClassBuilderImpl;
import com.icw.ehf.cui.core.api.components.html.AttributeName;
import com.icw.ehf.cui.core.api.components.html.Node;
import com.icw.ehf.cui.core.api.components.renderer.BaseDecoratorRenderer;
import com.icw.ehf.cui.core.api.components.renderer.DecoratingResponseWriter;

import de.cuioss.tools.string.MoreStrings;

/**
 * <p>
 * Default {@link Renderer} for {@link LabeledContainerComponent}
 * </p>
 *
 * @author Matthias Walliczek
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY,
        rendererType = BootstrapFamily.LABELED_CONTAINER_COMPONENT_RENDERER)
@SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                              // controlled by JSF
public class LabeledContainerRenderer extends BaseDecoratorRenderer<LabeledContainerComponent> {

    /**
     *
     */
    public LabeledContainerRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<LabeledContainerComponent> writer,
            final LabeledContainerComponent component)
        throws IOException {

        writeContainerStart(writer, component);

        if (component.containsCheckbox()) {
            if (component.shouldRenderAsColumn()) {
                writer.withStartElement(Node.DIV);
                writer.withStyleClass(new ColumnCssResolver(component.getContentSize(),
                        component.getLabelSize(), true,
                        component.getContentStyleClass()).resolveColumnCss());
            }
            writer.withStartElement(Node.DIV);
            writer.withStyleClass(CssBootstrap.CHECKBOX);

            beginLabel(writer, component);
        } else {
            beginLabel(writer, component);

            if (component.containsUIInput()) {
                writer.withAttribute(AttributeName.FOR,
                        component.getClientId(context)
                                + UINamingContainer.getSeparatorChar(context)
                                + component.getForIdentifier());
            }
            if (component.isLabelFacetRendered()) {
                component.getLabelFacet().encodeAll(context);
            } else {
                writer.withTextContent(component.resolveLabel(), component.isLabelEscape());
            }
            writer.withEndElement(Node.LABEL);

            var contentCss = component
                    .resolveContentCss(component.shouldRenderAsColumn())
                    .getStyleClass();
            if (!MoreStrings.isEmpty(contentCss)) {
                // Open Column
                writer.withStartElement(Node.DIV);
                writer.withStyleClass(contentCss);
            }

            doEncodeInputBegin(context, writer, component);
        }
    }

    private static void writeContainerStart(final DecoratingResponseWriter<LabeledContainerComponent> writer,
            final LabeledContainerComponent component)
        throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withAttributeTitle(component.resolveTitle());
        if (component.shouldWriteFormGroup()) {
            writer.withStyleClass(CssBootstrap.FORM_GROUP.getStyleClassBuilder().append(component.getStyleClass()));
        } else {
            writer.withStyleClass(component.getStyleClass());

        }
        writer.withAttributeStyle(component.getStyle());
        writer.withPassThroughAttributes();
        writer.withClientIdIfNecessary();
    }

    private static void doEncodeInputBegin(final FacesContext context,
            final DecoratingResponseWriter<LabeledContainerComponent> writer,
            final LabeledContainerComponent component)
        throws IOException {
        if (!component.containsUIInput() && !component.shouldRenderComplexOutput()) {
            writer.withStartElement(Node.P);
            writer.withStyleClass(CssBootstrap.FORM_CONTROL_STATIC);
            writer.withTextContent(component.resolveContent(), component.getContentEscape());
        } else if (component.shouldRenderInputGroup()) {
            writer.withStartElement(Node.DIV);
            writer.withStyleClass(CssBootstrap.INPUT_GROUP);
            if (component.isPrependFacetRendered()) {
                writer.withStartElement(Node.DIV);
                writer.withStyleClass(determineInputGroupCSS(component.getPrependAsButton()));
                component.getPrependFacet().encodeAll(context);
                writer.withEndElement(Node.DIV);
            }
        }
    }

    private static void beginLabel(
            final DecoratingResponseWriter<LabeledContainerComponent> writer,
            final LabeledContainerComponent component)
        throws IOException {
        writer.withStartElement(Node.LABEL);
        StyleClassBuilder labelStyleClassBuilder = new StyleClassBuilderImpl();
        if (!component.containsCheckbox()) {
            labelStyleClassBuilder = component.resolveLabelCss(
                    component.shouldRenderAsColumn());
            labelStyleClassBuilder.append(CssBootstrap.CONTROL_LABEL);
        }
        if (LayoutMode.LABEL_SR_ONLY.equals(component.resolveLayoutMode())) {
            labelStyleClassBuilder.append(CssBootstrap.SR_ONLY);
        }
        writer.withStyleClass(labelStyleClassBuilder);
        writer.withClientId("label");
    }

    /**
     * Overriding this method is necessary in order to render the correct order of the dynamically
     * added {@link CuiMessageComponent}
     */
    @Override
    protected void doEncodeChildren(final FacesContext context,
            final DecoratingResponseWriter<LabeledContainerComponent> writer,
            final LabeledContainerComponent component)
        throws IOException {
        for (final UIComponent child : component.getChildren()) {
            if (child.isRendered() && !(child instanceof CuiMessageComponent)) {
                child.encodeAll(context);
            }
        }
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<LabeledContainerComponent> writer,
            final LabeledContainerComponent component)
        throws IOException {

        if (component.containsCheckbox()) {
            handleEncodeEndCheckbox(context, writer, component);
        } else {
            if (!component.containsUIInput() && !component.shouldRenderComplexOutput()) {
                writer.withEndElement(Node.P);
            } else if (component.shouldRenderInputGroup()) {
                if (component.isAppendFacetRendered()) {
                    writer.withStartElement(Node.DIV);
                    writer.withStyleClass(determineInputGroupCSS(component.getAppendAsButton()));
                    component.getAppendFacet().encodeAll(context);
                    writer.withEndElement(Node.DIV);
                }
                writer.withEndElement(Node.DIV);
            }
            renderCuiMessage(context, component);
            var additionalHelpText = component.getAdditionalHelpText();
            if (additionalHelpText.isPresent()) {
                additionalHelpText.get().encodeAll(context);
            }
            if (!MoreStrings.isEmpty(component.resolveContentCss(component.shouldRenderAsColumn())
                    .getStyleClass())) {
                writer.withEndElement(Node.DIV);
            }
        }

        // Container end
        writer.withEndElement(Node.DIV);
    }

    private static void handleEncodeEndCheckbox(final FacesContext context,
            final DecoratingResponseWriter<LabeledContainerComponent> writer, final LabeledContainerComponent component)
        throws IOException {
        if (component.isLabelFacetRendered()) {
            component.getLabelFacet().encodeAll(context);
        } else {
            writer.withTextContent(component.resolveLabel(), component.isLabelEscape());
        }
        writer.withEndElement(Node.LABEL);
        writer.withEndElement(Node.DIV);
        renderCuiMessage(context, component);
        var additionalHelpText = component.getAdditionalHelpText();
        if (additionalHelpText.isPresent()) {
            additionalHelpText.get().encodeAll(context);
        }

        if (component.shouldRenderAsColumn()) {
            writer.withEndElement(Node.DIV);
        }
    }

    private static void renderCuiMessage(FacesContext context, LabeledContainerComponent component) throws IOException {
        if (component.getRenderMessage() && null != component.getCuiMessage()) {
            component.getCuiMessage().encodeAll(context);
        }
    }

    private static StyleClassProvider determineInputGroupCSS(final boolean renderAsButton) {
        if (renderAsButton) {
            return CssBootstrap.INPUT_GROUP_BUTTON;
        }
        return CssBootstrap.INPUT_GROUP_ADDON;
    }
}
