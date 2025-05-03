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
package de.cuioss.jsf.bootstrap.layout.messages;

import de.cuioss.jsf.api.components.css.StyleClassProvider;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.tools.collect.MapBuilder;
import de.cuioss.tools.string.Joiner;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;
import jakarta.faces.render.Renderer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Default {@link Renderer} for {@link CuiMessageComponent}
 * </p>
 *
 * @author Matthias Walliczek
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.CUI_MESSAGE_COMPONENT_RENDERER)
public class CuiMessageRenderer extends BaseDecoratorRenderer<CuiMessageComponent> {

    private static final Map<Severity, StyleClassProvider> SEVERITIES = createSeverities();

    /**
     *
     */
    public CuiMessageRenderer() {
        super(true);
    }

    private static Map<Severity, StyleClassProvider> createSeverities() {
        var builder = new MapBuilder<Severity, StyleClassProvider>();
        builder.put(FacesMessage.SEVERITY_INFO, CssCuiBootstrap.CUI_MESSAGE_INFO);
        builder.put(FacesMessage.SEVERITY_WARN, CssCuiBootstrap.CUI_MESSAGE_WARN);
        builder.put(FacesMessage.SEVERITY_ERROR, CssCuiBootstrap.CUI_MESSAGE_ERROR);
        builder.put(FacesMessage.SEVERITY_FATAL, CssCuiBootstrap.CUI_MESSAGE_FATAL);
        return builder.toImmutableMap();
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<CuiMessageComponent> writer,
            final CuiMessageComponent component) throws IOException {

        writer.withStartElement(Node.DIV);
        writer.withClientIdIfNecessary();
        writer.withAttributeStyle(component.getStyle());
        writer.withPassThroughAttributes();
        writer.withStyleClass(CssBootstrap.CUI_MESSAGE.getStyleClassBuilder().append(component.getStyleClass()));
        writer.withAttribute(AttributeName.ARIA_LIVE, "polite");
    }

    /**
     * Renders the faces message. If tooltip is true, the message only contains the
     * summary, the detail is in the tooltip. If tooltip is false, the message
     * contains summary and detail, the tooltips contains the summary.
     *
     * @param facesMessage
     * @param cuiMessage
     * @param writer
     * @throws IOException
     */
    private static void writeFacesMessage(final FacesMessage facesMessage, final CuiMessageComponent cuiMessage,
            final DecoratingResponseWriter<CuiMessageComponent> writer) throws IOException {
        final var showDetail = cuiMessage.isShowDetail();

        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(SEVERITIES.get(facesMessage.getSeverity()));

        final var summary = cuiMessage.isShowSummary() ? facesMessage.getSummary() : null;
        final var detail = showDetail ? facesMessage.getDetail() : null;
        final var msg = showDetail ? Joiner.on(' ').skipNulls().join(summary, detail) : summary;

        if (!showDetail) {
            writer.withAttribute(AttributeName.TITLE, facesMessage.getDetail());
        }
        writer.withTextContent(msg, cuiMessage.isEscape());

        writer.withEndElement(Node.SPAN);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<CuiMessageComponent> writer,
            final CuiMessageComponent component) throws IOException {
        if (component.isRendered()) {
            var messages = component.readMessages();
            if (!messages.isEmpty()) {
                if (messages.size() == 1) {
                    writeFacesMessage(messages.get(0), component, writer);
                } else {
                    writeFacesMessages(messages, component, writer);
                }
            }
        }
        writer.withEndElement(Node.DIV);
    }

    private static void writeFacesMessages(List<FacesMessage> messages, final CuiMessageComponent component,
            final DecoratingResponseWriter<CuiMessageComponent> writer) throws IOException {

        writer.withStartElement(Node.UL);
        writer.withStyleClass(CssCuiBootstrap.CUI_MESSAGE_LIST);
        for (FacesMessage message : messages) {
            if (!message.isRendered()) {
                writer.withStartElement(Node.LI);
                writeFacesMessage(message, component, writer);
                writer.withEndElement(Node.LI);
                message.rendered();
            }
        }
        writer.withEndElement(Node.UL);
    }
}
