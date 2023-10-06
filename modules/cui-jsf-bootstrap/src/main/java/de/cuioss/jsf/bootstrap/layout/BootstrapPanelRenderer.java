/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.bootstrap.layout;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.api.components.renderer.ResponseWriterBase;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.waitingindicator.WaitingIndicatorComponent;

/**
 * Bootstrap panel component
 *
 * @author Matthias Walliczek
 * @author Sven Haag
 */
@FacesRenderer(rendererType = BootstrapFamily.PANEL_RENDERER, componentFamily = BootstrapFamily.COMPONENT_FAMILY)
public class BootstrapPanelRenderer extends BaseDecoratorRenderer<BootstrapPanelComponent> {

    private static final String ID_SUFFIX_ISEXPANDED = "isexpanded";
    private static final String ID_SUFFIX_BODY = "body";
    private static final String ID_SUFFIX_FOOTER = "footer";
    private static final String ID_SUFFIX_TOGGLER = "toggler";
    private static final String ID_SUFFIX_ICON = "icon";
    private static final String ID_SUFFIX_HEADING = "heading";

    /***/
    public BootstrapPanelRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<BootstrapPanelComponent> writer, final BootstrapPanelComponent component)
            throws IOException {
        writePanelBegin(writer, component);
        writeStateHolder(writer, component);
        writeHeader(writer, component, context);
        writeBodyBegin(writer, component);
    }

    @Override
    protected void doEncodeChildren(final FacesContext context,
            final DecoratingResponseWriter<BootstrapPanelComponent> writer, final BootstrapPanelComponent component)
            throws IOException {
        if (component.shouldRenderSpinnerIcon()) {
            writeSpinnerIcon(context);
        } else {
            component.setChildrenLoaded(true);
            super.doEncodeChildren(context, writer, component);
        }
    }

    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<BootstrapPanelComponent> writer, final BootstrapPanelComponent component)
            throws IOException {
        writePanelBodyEnd(writer);
        writeFooter(writer, component, context);
        writePanelEnd(writer);
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    /**
     * Update collapse state from request.
     */
    @Override
    protected void doDecode(final FacesContext context,
            final ComponentWrapper<BootstrapPanelComponent> componentWrapper) {
        final var component = componentWrapper.getWrapped();
        final var map = context.getExternalContext().getRequestParameterMap();

        final var isExpandedValue = map.get(componentWrapper.getSuffixedClientId(ID_SUFFIX_ISEXPANDED));
        if (isExpandedValue != null) {
            var expandedValue = Boolean.parseBoolean(isExpandedValue);
            component.setCollapsedState(!expandedValue);
            if (expandedValue) {
                component.setChildrenLoaded(true);
            }
        }
    }

    private static String getPanelBodyId(final BootstrapPanelComponent component) {
        return component.getClientId() + "_" + ID_SUFFIX_BODY;
    }

    private static StyleClassProvider getBootstrapCollapseStateClass(final BootstrapPanelComponent component) {
        return component.resolveCollapsed() ? CssBootstrap.COLLAPSE : CssBootstrap.COLLAPSE_IN;
    }

    /**
     * Panel header / toggle element
     */
    private static void writeHeader(final DecoratingResponseWriter<BootstrapPanelComponent> writer,
            final BootstrapPanelComponent component, final FacesContext context) throws IOException {
        if (component.shouldRenderHeader()) {
            writer.withStartElement(Node.DIV);
            writer.withClientId(ID_SUFFIX_TOGGLER);
            writer.withAttribute(AttributeName.ARIA_EXPANDED, String.valueOf(!component.resolveCollapsed()));
            writer.withAttribute(AttributeName.ROLE, component.resolveTogglerRole());

            if (component.isCollapsible()) {
                writer.withAttribute(AttributeName.DATA_TOGGLE, CssBootstrap.COLLAPSE.getStyleClass());
                writer.withAttribute(AttributeName.DATA_TARGET,
                        "#" + DecoratingResponseWriter.escapeJavaScriptIdentifier(getPanelBodyId(component)));
                writer.withAttribute(AttributeName.ARIA_CONTROLS, getPanelBodyId(component));
                writer.withStyleClass(
                        CssBootstrap.PANEL_HEADING.getStyleClassBuilder().append(CssCuiBootstrap.CUI_COLLAPSIBLE));
            } else {
                writer.withStyleClass(CssBootstrap.PANEL_HEADING.getStyleClassBuilder());
            }

            if (component.getDataParent() != null) { // panel is in accordion
                writer.withAttribute(AttributeName.DATA_PARENT,
                        "#" + DecoratingResponseWriter.escapeJavaScriptIdentifier(component.getDataParent()));
            }

            writeHeading(writer, component, context);

            writer.withEndElement(Node.DIV);
        }
    }

    /**
     * Write heading for panel header. A header facet takes precedence over set
     * header values/keys.
     */
    private static void writeHeading(final DecoratingResponseWriter<BootstrapPanelComponent> writer,
            final BootstrapPanelComponent component, final FacesContext context) throws IOException {
        if (component.shouldRenderHeaderFacet()) {
            // Facet needs to take care about collapse icon.
            component.getHeaderFacet().encodeAll(context);
        } else if (component.hasHeaderTitleSet()) {
            writer.startElement(component.getHeaderTag(), component);
            writer.withClientId(ID_SUFFIX_HEADING);
            writer.withStyleClass(CssBootstrap.PANEL_TITLE);

            if (component.isCollapsible()) {
                // icon
                writer.withStartElement(Node.SPAN);
                writer.withStyleClass(CssCuiBootstrap.CUI_COLLAPSIBLE_ICON);
                writer.withClientId(ID_SUFFIX_ICON);
                writer.withEndElement(Node.SPAN);
            }

            writer.withTextContent(component.resolveHeader(), component.isHeaderEscape());

            writer.endElement(component.getHeaderTag());
        }
    }

    /**
     * Write footer if necessary. A footer facet takes precedence over set footer
     * values/keys.
     */
    private static void writeFooter(final DecoratingResponseWriter<BootstrapPanelComponent> writer,
            final BootstrapPanelComponent component, final FacesContext context) throws IOException {
        if (component.shouldRenderFooter()) {
            writer.withStartElement(Node.DIV);
            writer.withStyleClass(CssBootstrap.PANEL_FOOTER);
            writer.withClientId(ID_SUFFIX_FOOTER);
            if (component.shouldRenderFooterFacet()) {
                component.getFooterFacet().encodeAll(context);
            } else {
                writer.withTextContent(component.resolveFooter(), component.isFooterEscape());
            }
            writer.withEndElement(Node.DIV);
        }
    }

    /**
     * @param component {@link BootstrapPanelComponent} must not be {@code null}
     * @return CSS state style class with panel prefix
     */
    private static String getPanelClassForState(final BootstrapPanelComponent component) {
        return component.resolveContextState().getStyleClassWithPrefix("panel");
    }

    /**
     * Start panel HTML.
     */
    private static void writePanelBegin(final DecoratingResponseWriter<BootstrapPanelComponent> writer,
            final BootstrapPanelComponent component) throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withClientId();

        if (component.isAsyncUpdate()) {
            writer.withAttribute(AttributeName.DATA_ASYNCUPDATE, "true");
        }
        if (component.isDeferred()) {
            writer.withAttribute(AttributeName.DATA_DEFERRED, "true");
            if (!component.shouldRenderSpinnerIcon()) {
                writer.withAttribute(AttributeName.DATA_CONTENT_LOADED, "true");
            }
        }

        writer.withAttributeStyle(component.getStyle());
        writer.withPassThroughAttributes();
        writer.withAttribute(AttributeName.DATA_NOT_COLLAPSED, Boolean.toString(!component.resolveCollapsed()));

        writer.withStyleClass(CssBootstrap.PANEL.getStyleClassBuilder().append(getPanelClassForState(component))
                .append(CssCuiBootstrap.CUI_PANEL).append(component.getStyleClass()));

    }

    /**
     * Start panel HTML body.
     */
    private static void writeBodyBegin(final DecoratingResponseWriter<BootstrapPanelComponent> writer,
            final BootstrapPanelComponent component) throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withClientId(ID_SUFFIX_BODY);
        writer.withStyleClass(
                CssBootstrap.PANEL_COLLAPSE.getStyleClassBuilder().append(getBootstrapCollapseStateClass(component)));
        writer.withAttribute(AttributeName.ARIA_LABELLEDBY, component.getClientId() + "_" + ID_SUFFIX_TOGGLER);

        if (component.getDataParent() != null) {
            writer.withAttribute(AttributeName.ROLE, "tabpanel");
        }

        writer.withStartElement(Node.DIV);
        writer.withStyleClass(CssBootstrap.PANEL_BODY);
    }

    /**
     * Close panel HTML body.
     */
    private static void writePanelBodyEnd(final ResponseWriterBase writer) throws IOException {
        writer.withEndElement(Node.DIV);
        writer.withEndElement(Node.DIV);
    }

    /**
     * Close panel HTML
     */
    private static void writePanelEnd(final ResponseWriterBase writer) throws IOException {
        writer.withEndElement(Node.DIV);
    }

    /**
     * Write element to keep track of current collapse state - necessary for AJAX
     * request.
     */
    private static void writeStateHolder(final DecoratingResponseWriter<BootstrapPanelComponent> writer,
            final BootstrapPanelComponent component) throws IOException {
        writer.withStartElement(Node.INPUT);
        writer.withClientId(ID_SUFFIX_ISEXPANDED);
        writer.withAttribute(AttributeName.TYPE, AttributeValue.HIDDEN);
        writer.withAttribute(AttributeName.VALUE, String.valueOf(!component.resolveCollapsed()));
        writer.withEndElement(Node.INPUT);
    }

    /**
     * Write element to indicate ongoing deferred content retrieval.
     */
    private static void writeSpinnerIcon(final FacesContext facesContext) throws IOException {
        var indicator = WaitingIndicatorComponent.createComponent(facesContext);
        indicator.setSize("sm");
        indicator.encodeAll(facesContext);
    }
}
