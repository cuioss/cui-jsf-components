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
package de.cuioss.jsf.bootstrap.modal;

import static de.cuioss.jsf.api.components.html.AttributeName.*;
import static de.cuioss.jsf.api.components.html.AttributeValue.ROLE_DIALOG;
import static de.cuioss.jsf.api.components.html.Node.*;
import static de.cuioss.jsf.bootstrap.CssBootstrap.*;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.common.HtmlSnippetRenderer;
import de.cuioss.jsf.bootstrap.modal.support.ModalDialogSize;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * <p>This renderer generates the HTML markup for the {@link ModalDialogComponent}
 * according to Bootstrap's modal dialog specifications. It handles the rendering of
 * the modal container, dialog header, body, footer and all associated CSS classes and attributes.</p>
 *
 * <h2>Generated HTML Structure</h2>
 * <pre>
 * &lt;div class="modal" tabindex="-1" role="dialog" data-modal-id="dialogId"&gt;
 *   &lt;div class="modal-dialog" role="document"&gt;
 *     &lt;div class="modal-content"&gt;
 *       &lt;div class="modal-header"&gt;
 *         &lt;button type="button" class="close" data-dismiss="modal"&gt;&amp;times;&lt;/button&gt;
 *         &lt;h4 class="modal-title"&gt;Dialog Title&lt;/h4&gt;
 *       &lt;/div&gt;
 *       &lt;div class="modal-body"&gt;
 *         &lt;!-- dialog content --&gt;
 *       &lt;/div&gt;
 *       &lt;div class="modal-footer"&gt;
 *         &lt;!-- footer content --&gt;
 *       &lt;/div&gt;
 *     &lt;/div&gt;
 *   &lt;/div&gt;
 * &lt;/div&gt;
 * </pre>
 * 
 * <h2>Opening and Closing</h2>
 * <p>The renderer adds the attribute {@link AttributeName#DATA_MODAL_ID} with the
 * dialog ID to enable JavaScript interaction. This ID can be used by {@link ModalControl}
 * to open the dialog or with JavaScript directly:</p>
 * <pre>
 * // To open the dialog:
 * $('#dialogId').modal('show');
 * 
 * // To close the dialog:
 * $('#dialogId').modal('hide');
 * </pre>
 * 
 * <h2>Rendering Features</h2>
 * <ul>
 *   <li>Supports responsive sizing through {@link ModalDialogSize} constants</li>
 *   <li>Conditional rendering of header based on component configuration</li>
 *   <li>Optional close button in header when component is closable</li>
 *   <li>Conditional rendering of footer based on component configuration</li>
 *   <li>Support for header and footer facets for custom content</li>
 *   <li>Static backdrop mode when dialog is not closable (prevents closing on outside click)</li>
 * </ul>
 * 
 * <h2>Implementation Note</h2>
 * <p>This renderer utilizes {@link DecoratingResponseWriter} for convenient HTML generation and
 * proper handling of component attributes. It follows Bootstrap's modal dialog structure while
 * supporting the customization options provided by {@link ModalDialogComponent}.</p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * 
 * @see ModalDialogComponent
 * @see ModalControl
 * @see ModalDialogSize
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.MODAL_DIALOG_COMPONENT_RENDERER)
public class ModalDialogRenderer extends BaseDecoratorRenderer<ModalDialogComponent> {

    /**
     * Constructor setting the renderer as not a partial renderer.
     */
    public ModalDialogRenderer() {
        super(false);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Renders the modal dialog structure including outer container, dialog wrapper,
     * content container, header and beginning of body section.
     * </p>
     */
    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<ModalDialogComponent> writer, final ModalDialogComponent component)
            throws IOException {
        // wrapper element
        writer.withStartElement(DIV).withClientIdIfNecessary().withStyleClass(component.getStyleClass())
                .withAttributeStyle(component.getStyle()).withPassThroughAttributes()
                .withAttribute(DATA_MODAL_ID, component.resolveDialogId()).withAttribute(TABINDEX, "-1")
                .withAttribute(ROLE, ROLE_DIALOG);

        // if dialog should not be closable data-backdrop="static" must be available
        if (!component.isClosable()) {
            writer.withAttribute(DATA_BACKDROP, "static");
        }
        // Inner Wrapper
        writer.withStartElement(DIV).withStyleClass(CssBootstrap.MODAL_DIALOG.getStyleClassBuilder()
                .append(ModalDialogSize.getFromString(component.getSize()))).withAttribute(ROLE, "document");
        // Modal Content
        writer.withStartElement(DIV).withStyleClass(CssBootstrap.MODAL_CONTENT);

        writeHeader(context, writer, component);

        // Start Body
        writer.withStartElement(DIV).withStyleClass(MODAL_DIALOG_BODY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Closes the body section, renders the footer (if configured), and closes
     * all remaining container elements.
     * </p>
     */
    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<ModalDialogComponent> writer,
            final ModalDialogComponent component) throws IOException {
        // Body End
        writer.withEndElement(DIV);
        writeFooter(context, writer, component);
        // End Modal Content
        writer.withEndElement(DIV);
        // End Inner wrapper
        writer.withEndElement(DIV);
        // End wrapper
        writer.withEndElement(DIV);
    }

    /**
     * Renders the modal dialog header including:
     * <ul>
     *   <li>Close button (if component is closable)</li>
     *   <li>Header content (either from headerValue or headerFacet)</li>
     * </ul>
     * 
     * @param context the current FacesContext
     * @param writer the response writer for rendering content
     * @param component the modal dialog component being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    private static void writeHeader(final FacesContext context,
            final DecoratingResponseWriter<ModalDialogComponent> writer, final ModalDialogComponent component)
            throws IOException {
        writer.withStartElement(DIV).withStyleClass(MODAL_DIALOG_HEADER);
        if (component.isClosable()) {
            HtmlSnippetRenderer.renderCloseButton(writer, "modal");
        }

        if (component.shouldRenderHeader()) {
            if (component.shouldRenderHeaderFacet()) {
                component.getHeaderFacet().encodeAll(context);
            } else {
                writer.withStartElement(H4).withStyleClass(MODAL_DIALOG_TITLE)
                        .withTextContent(component.resolveHeader(), component.isHeaderEscape()).withEndElement(H4);
            }
        }
        writer.withEndElement(DIV);
    }

    /**
     * Renders the modal dialog footer if it should be displayed according to component 
     * configuration. The content can be either:
     * <ul>
     *   <li>Custom content from the footer facet</li>
     *   <li>Text from the footerValue attribute</li>
     * </ul>
     * 
     * @param context the current FacesContext
     * @param writer the response writer for rendering content
     * @param component the modal dialog component being rendered
     * @throws IOException if an error occurs during writing to the response
     */
    private static void writeFooter(final FacesContext context,
            final DecoratingResponseWriter<ModalDialogComponent> writer, final ModalDialogComponent component)
            throws IOException {
        if (!component.shouldRenderFooter()) {
            return;
        }
        writer.withStartElement(DIV).withStyleClass(MODAL_DIALOG_FOOTER);
        if (component.shouldRenderFooterFacet()) {
            component.getFooterFacet().encodeAll(context);
        } else {
            writer.withStartElement(SPAN).withStyleClass(MODAL_DIALOG_FOOTER_TEXT)
                    .withTextContent(component.resolveFooter(), component.isFooterEscape()).withEndElement(SPAN);
        }
        writer.withEndElement(DIV);
    }
}
