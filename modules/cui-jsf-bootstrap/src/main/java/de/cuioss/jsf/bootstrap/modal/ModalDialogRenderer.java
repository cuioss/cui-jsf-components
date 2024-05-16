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

import static de.cuioss.jsf.api.components.html.AttributeName.DATA_BACKDROP;
import static de.cuioss.jsf.api.components.html.AttributeName.DATA_MODAL_ID;
import static de.cuioss.jsf.api.components.html.AttributeName.ROLE;
import static de.cuioss.jsf.api.components.html.AttributeName.TABINDEX;
import static de.cuioss.jsf.api.components.html.AttributeValue.ROLE_DIALOG;
import static de.cuioss.jsf.api.components.html.Node.DIV;
import static de.cuioss.jsf.api.components.html.Node.H4;
import static de.cuioss.jsf.api.components.html.Node.SPAN;
import static de.cuioss.jsf.bootstrap.CssBootstrap.MODAL_DIALOG_BODY;
import static de.cuioss.jsf.bootstrap.CssBootstrap.MODAL_DIALOG_FOOTER;
import static de.cuioss.jsf.bootstrap.CssBootstrap.MODAL_DIALOG_FOOTER_TEXT;
import static de.cuioss.jsf.bootstrap.CssBootstrap.MODAL_DIALOG_HEADER;
import static de.cuioss.jsf.bootstrap.CssBootstrap.MODAL_DIALOG_TITLE;

import java.io.IOException;

import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.common.HtmlSnippetRenderer;
import de.cuioss.jsf.bootstrap.modal.support.ModalDialogSize;

/**
 * Renders a bootstrap-conform modal dialog.
 * <h2>Opening and Closing</h2>
 * <p>
 * The Dialog renders the attribute {@link AttributeName#DATA_MODAL_ID} with the
 * dialog-id
 * </p>
 *
 * @author Oliver Wolff
 *
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.MODAL_DIALOG_COMPONENT_RENDERER)
public class ModalDialogRenderer extends BaseDecoratorRenderer<ModalDialogComponent> {

    public ModalDialogRenderer() {
        super(false);
    }

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
