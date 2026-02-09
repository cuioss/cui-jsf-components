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
package de.cuioss.jsf.bootstrap.layout;

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
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * <p>Renderer for the {@link BootstrapPanelComponent} that generates Bootstrap-compatible panel HTML.
 * This renderer handles the complete rendering lifecycle for panel components including:
 * header/footer rendering, collapse/expand states, contextual styling, and deferred content loading.</p>
 * 
 * <h2>Generated HTML Structure</h2>
 * <pre>
 * &lt;div class="panel panel-[state] cui-panel" id="[clientId]"&gt;
 *   &lt;!-- State holder for collapse state --&gt;
 *   &lt;input type="hidden" value="[expanded]" id="[clientId]_isexpanded" /&gt;
 *   
 *   &lt;!-- Header (if applicable) --&gt;
 *   &lt;div class="panel-heading [collapsible-class]" id="[clientId]_toggler"&gt;
 *     &lt;h4 class="panel-title"&gt;
 *       &lt;span class="cui-collapsible-icon" id="[clientId]_icon"&gt;&lt;/span&gt;
 *       Panel Title
 *     &lt;/h4&gt;
 *   &lt;/div&gt;
 *   
 *   &lt;!-- Panel body with collapse container --&gt;
 *   &lt;div class="panel-collapse collapse [in]" id="[clientId]_body"&gt;
 *     &lt;div class="panel-body"&gt;
 *       &lt;!-- Panel content or loading indicator --&gt;
 *     &lt;/div&gt;
 *   &lt;/div&gt;
 *   
 *   &lt;!-- Footer (if applicable) --&gt;
 *   &lt;div class="panel-footer" id="[clientId]_footer"&gt;
 *     Footer content
 *   &lt;/div&gt;
 * &lt;/div&gt;
 * </pre>
 * 
 * <h2>Rendering Features</h2>
 * <ul>
 *   <li><b>Header/Title Rendering:</b> Renders panel headers with or without facets</li>
 *   <li><b>Collapse/Expand:</b> Handles toggling visibility of panel content</li>
 *   <li><b>Contextual Styling:</b> Applies Bootstrap contextual classes based on state</li>
 *   <li><b>Deferred Content:</b> Shows loading indicators for deferred content</li>
 *   <li><b>AJAX Support:</b> Maintains component state during AJAX updates</li>
 *   <li><b>Accessibility:</b> Adds appropriate ARIA attributes for screen readers</li>
 * </ul>
 * 
 * <h2>JavaScript Integration</h2>
 * <p>The renderer uses data-* attributes to integrate with the Bootstrap 
 * JavaScript components and CUI's custom panel enablers.</p>
 *
 * @author Matthias Walliczek
 * @author Sven Haag
 * @see BootstrapPanelComponent
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

    /**
     * {@inheritDoc}
     * 
     * Renders the opening parts of the Bootstrap panel:
     * <ol>
     *   <li>The panel's outer container element with appropriate styling</li>
     *   <li>The hidden state holder input for tracking collapse state</li>
     *   <li>The panel header/title section if applicable</li>
     *   <li>The beginning of the panel body</li>
     * </ol>
     * 
     * @param context the current faces context
     * @param writer the response writer for generating HTML
     * @param component the panel component being rendered
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<BootstrapPanelComponent> writer, final BootstrapPanelComponent component)
            throws IOException {
        writePanelBegin(writer, component);
        writeStateHolder(writer, component);
        writeHeader(writer, component, context);
        writeBodyBegin(writer, component);
    }

    /**
     * {@inheritDoc}
     * 
     * Renders the panel's content based on its state:
     * <ul>
     *   <li>If deferred loading is active and content isn't loaded yet - renders a spinner</li>
     *   <li>Otherwise - renders all child components and marks content as loaded</li>
     * </ul>
     * 
     * @param context the current faces context
     * @param writer the response writer for generating HTML
     * @param component the panel component being rendered
     * @throws IOException if an I/O error occurs during writing
     */
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

    /**
     * {@inheritDoc}
     * 
     * Renders the closing parts of the Bootstrap panel:
     * <ol>
     *   <li>The closing tags for the panel body</li>
     *   <li>The panel footer if applicable</li>
     *   <li>The closing tag for the panel container</li>
     * </ol>
     * 
     * @param context the current faces context
     * @param writer the response writer for generating HTML
     * @param component the panel component being rendered
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    protected void doEncodeEnd(final FacesContext context,
            final DecoratingResponseWriter<BootstrapPanelComponent> writer, final BootstrapPanelComponent component)
            throws IOException {
        writePanelBodyEnd(writer);
        writeFooter(writer, component, context);
        writePanelEnd(writer);
    }

    /**
     * {@inheritDoc}
     * 
     * This renderer handles rendering of all children directly, 
     * rather than delegating to child renderers.
     * 
     * @return true, indicating this renderer renders its children
     */
    @Override
    public boolean getRendersChildren() {
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Decodes request parameters to update panel state:
     * <ul>
     *   <li>Checks for collapse/expand state changes from client</li>
     *   <li>Updates component state based on request parameters</li>
     *   <li>Marks content as loaded when expanded</li>
     * </ul>
     * 
     * @param context the current faces context
     * @param componentWrapper wrapper containing the panel component
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

    /**
     * Generates the client ID for the panel body element.
     * 
     * @param component the panel component
     * @return the client ID for the panel body
     */
    private static String getPanelBodyId(final BootstrapPanelComponent component) {
        return component.getClientId() + "_" + ID_SUFFIX_BODY;
    }

    /**
     * Determines the appropriate Bootstrap collapse CSS class based on panel state.
     * 
     * @param component the panel component
     * @return StyleClassProvider with either "collapse" or "collapse in" class
     */
    private static StyleClassProvider getBootstrapCollapseStateClass(final BootstrapPanelComponent component) {
        return component.resolveCollapsed() ? CssBootstrap.COLLAPSE : CssBootstrap.COLLAPSE_IN;
    }

    /**
     * Writes the panel header/toggle element if applicable.
     * Handles different cases:
     * <ul>
     *   <li>Header facet if provided</li>
     *   <li>Header title with collapse icon if collapsible</li>
     *   <li>Simple header title if not collapsible</li>
     * </ul>
     * 
     * @param writer the response writer
     * @param component the panel component
     * @param context the faces context
     * @throws IOException if an I/O error occurs during writing
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
     * Writes the heading content for the panel header.
     * Handles different heading types:
     * <ul>
     *   <li>Custom header facet</li>
     *   <li>Title text with optional collapse icon</li>
     * </ul>
     * 
     * @param writer the response writer
     * @param component the panel component
     * @param context the faces context
     * @throws IOException if an I/O error occurs during writing
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
     * Writes the footer element if applicable.
     * Handles different footer types:
     * <ul>
     *   <li>Custom footer facet</li>
     *   <li>Footer text</li>
     * </ul>
     * 
     * @param writer the response writer
     * @param component the panel component
     * @param context the faces context
     * @throws IOException if an I/O error occurs during writing
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
     * Determines the appropriate Bootstrap panel contextual class based on the component's state.
     * 
     * @param component the panel component
     * @return CSS class string for the panel's contextual state (panel-success, panel-warning, etc.)
     */
    private static String getPanelClassForState(final BootstrapPanelComponent component) {
        return component.resolveContextState().getStyleClassWithPrefix("panel");
    }

    /**
     * Writes the opening markup for the panel container.
     * Includes:
     * <ul>
     *   <li>The outer div with appropriate styling</li>
     *   <li>Data attributes for JavaScript functionality</li>
     *   <li>Accessibility attributes</li>
     * </ul>
     * 
     * @param writer the response writer
     * @param component the panel component
     * @throws IOException if an I/O error occurs during writing
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
     * Writes the opening markup for the panel body.
     * Creates the collapsible container and panel-body elements.
     * 
     * @param writer the response writer
     * @param component the panel component
     * @throws IOException if an I/O error occurs during writing
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
     * Writes the closing tags for the panel body elements.
     * Closes both the panel-body div and its containing collapse div.
     * 
     * @param writer the response writer
     * @throws IOException if an I/O error occurs during writing
     */
    private static void writePanelBodyEnd(final ResponseWriterBase writer) throws IOException {
        writer.withEndElement(Node.DIV);
        writer.withEndElement(Node.DIV);
    }

    /**
     * Writes the closing tag for the panel container.
     * 
     * @param writer the response writer
     * @throws IOException if an I/O error occurs during writing
     */
    private static void writePanelEnd(final ResponseWriterBase writer) throws IOException {
        writer.withEndElement(Node.DIV);
    }

    /**
     * Writes a hidden input element to track the panel's expand/collapse state.
     * This input is used during AJAX requests to maintain panel state.
     * 
     * @param writer the response writer
     * @param component the panel component
     * @throws IOException if an I/O error occurs during writing
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
     * Writes a loading spinner indicator for deferred content loading.
     * Used when a panel is configured for deferred loading and content
     * hasn't been loaded yet.
     * 
     * @param facesContext the faces context
     * @throws IOException if an I/O error occurs during writing
     */
    private static void writeSpinnerIcon(final FacesContext facesContext) throws IOException {
        var indicator = WaitingIndicatorComponent.createComponent(facesContext);
        indicator.setSize("sm");
        indicator.encodeAll(facesContext);
    }
}
