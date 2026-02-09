/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.layout.input;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.common.partial.ColumnCssResolver;
import de.cuioss.jsf.bootstrap.layout.LayoutMode;
import de.cuioss.jsf.bootstrap.layout.messages.CuiMessageComponent;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * <p>
 * Renderer implementation for {@link LabeledContainerComponent} that handles the complex rendering logic
 * for labeled form elements, supporting different layout modes and input types.
 * </p>
 * <p>
 * The renderer implements several key behaviors:
 * </p>
 * <ul>
 * <li>Layout rendering for column-based and plain modes</li>
 * <li>Special handling for checkbox/radio button inputs</li>
 * <li>Input group support with prepend/append elements</li>
 * <li>Integration with validation messages</li>
 * <li>Support for static content and complex output</li>
 * </ul>
 *
 * @author Matthias Walliczek
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.LABELED_CONTAINER_COMPONENT_RENDERER)
public class LabeledContainerRenderer extends BaseDecoratorRenderer<LabeledContainerComponent> {

    public LabeledContainerRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<LabeledContainerComponent> writer, final LabeledContainerComponent component)
            throws IOException {

        writeContainerStart(writer, component);

        if (component.containsCheckbox()) {
            if (component.shouldRenderAsColumn()) {
                writer.withStartElement(Node.DIV);
                writer.withStyleClass(new ColumnCssResolver(component.getContentSize(), component.getLabelSize(), true,
                        component.getContentStyleClass()).resolveColumnCss());
            }
            writer.withStartElement(Node.DIV);
            writer.withStyleClass(CssBootstrap.CHECKBOX);

            beginLabel(writer, component);
        } else {
            beginLabel(writer, component);

            if (component.containsUIInput()) {
                writer.withAttribute(AttributeName.FOR, component.getClientId(context)
                        + UINamingContainer.getSeparatorChar(context) + component.getForIdentifier());
            }
            if (component.isLabelFacetRendered()) {
                component.getLabelFacet().encodeAll(context);
            } else {
                writer.withTextContent(component.resolveLabel(), component.isLabelEscape());
            }
            writer.withEndElement(Node.LABEL);

            var contentCss = component.resolveContentCss(component.shouldRenderAsColumn()).getStyleClass();
            if (!MoreStrings.isEmpty(contentCss)) {
                // Open Column
                writer.withStartElement(Node.DIV);
                writer.withStyleClass(contentCss);
            }

            doEncodeInputBegin(context, writer, component);
        }
    }

    private static void writeContainerStart(final DecoratingResponseWriter<LabeledContainerComponent> writer,
            final LabeledContainerComponent component) throws IOException {
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
            final DecoratingResponseWriter<LabeledContainerComponent> writer, final LabeledContainerComponent component)
            throws IOException {
        if (!component.containsUIInput() && component.shouldNotRenderComplexOutput()) {
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

    private static void beginLabel(final DecoratingResponseWriter<LabeledContainerComponent> writer,
            final LabeledContainerComponent component) throws IOException {
        writer.withStartElement(Node.LABEL);
        StyleClassBuilder labelStyleClassBuilder = new StyleClassBuilderImpl();
        if (!component.containsCheckbox()) {
            labelStyleClassBuilder = component.resolveLabelCss(component.shouldRenderAsColumn());
            labelStyleClassBuilder.append(CssBootstrap.CONTROL_LABEL);
        }
        if (LayoutMode.LABEL_SR_ONLY.equals(component.resolveLayoutMode())) {
            labelStyleClassBuilder.append(CssBootstrap.SR_ONLY);
        }
        writer.withStyleClass(labelStyleClassBuilder);
        writer.withClientId("label");
    }

    /**
     * Overriding this method is necessary in order to render the correct order of
     * the dynamically added {@link CuiMessageComponent}
     */
    @Override
    protected void doEncodeChildren(final FacesContext context,
            final DecoratingResponseWriter<LabeledContainerComponent> writer, final LabeledContainerComponent component)
            throws IOException {
        for (final UIComponent child : component.getChildren()) {
            if (child.isRendered() && !(child instanceof CuiMessageComponent)) {
                child.encodeAll(context);
            }
        }
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<LabeledContainerComponent> writer, final LabeledContainerComponent component)
            throws IOException {

        if (component.containsCheckbox()) {
            handleEncodeEndCheckbox(context, writer, component);
        } else {
            if (!component.containsUIInput() && component.shouldNotRenderComplexOutput()) {
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
            if (!MoreStrings.isEmpty(component.resolveContentCss(component.shouldRenderAsColumn()).getStyleClass())) {
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
