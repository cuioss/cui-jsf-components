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
package de.cuioss.jsf.bootstrap.dashboard;

import de.cuioss.jsf.api.components.model.widget.DashboardWidgetModel;
import de.cuioss.jsf.bootstrap.common.logging.BootstrapLogMessages;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.UIComponent;
import jakarta.faces.view.facelets.ComponentConfig;
import jakarta.faces.view.facelets.FaceletContext;
import jakarta.faces.view.facelets.TagAttribute;
import jakarta.faces.view.facelets.TagHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * TagHandler for rendering a dashboard with multiple widgets.
 * Dynamically creates and includes a facelet file with the widget components.
 * 
 * @author Oliver Wolff
 */
public class DashboardTagHandler extends TagHandler {

    private static final CuiLogger LOGGER = new CuiLogger(DashboardTagHandler.class);

    private final TagAttribute widgetsAttr;
    private final TagAttribute widgetStyleClassAttr;
    private final TagAttribute styleClassAttr;
    private final TagAttribute styleAttr;

    /**
     * Constructor storing the TagHandler configuration and attributes.
     * 
     * @param config The component configuration
     */
    public DashboardTagHandler(final ComponentConfig config) {
        super(config);
        widgetsAttr = getAttribute("widgets");
        widgetStyleClassAttr = getAttribute("widgetStyleClass");
        styleClassAttr = getAttribute("styleClass");
        styleAttr = getAttribute("style");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void apply(final FaceletContext ctx, final UIComponent parent) throws IOException {
        if (null == widgetsAttr) {
            throw new IllegalArgumentException("Property widgets not set!");
        }

        var styleClass = resolveAttribute(styleClassAttr, ctx, "dashboard-wrapper");
        var widgetStyleClass = resolveAttribute(widgetStyleClassAttr, ctx, "col-sm-12 col-md-6 col-lg-4");
        var style = resolveAttribute(styleAttr, ctx, null);

        final var widgetList = (List<DashboardWidgetModel>) widgetsAttr.getObject(ctx);
        final var f = new File(System.getProperty("java.io.tmpdir"), "dashboard" + widgetList.hashCode() + ".xhtml");
        if (!f.exists()) {
            try (var writer = new PrintWriter(f, StandardCharsets.UTF_8)) {
                writer.println(
                        "<ui:component xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"");
                writeNamespaceDeclarations(writer, widgetList);
                writer.println(">");
                writer.print("<div class=\"" + styleClass);
                if (!MoreStrings.isEmpty(style)) {
                    writer.print("\" style=\"" + style);
                }
                writer.println("\">");
                writeWidgetElements(writer, widgetList, widgetStyleClass);
                writer.println("</div>");
                writer.println("</ui:component>");
            }
        }
        try {
            nextHandler.apply(ctx, parent);
            ctx.includeFacelet(parent, f.toURI().toURL());
        } catch (final IOException e) {
            LOGGER.warn(e, BootstrapLogMessages.WARN.FACELET_INCLUDE_FAILED, f.getAbsolutePath());
        }
        f.deleteOnExit();
    }

    private static String resolveAttribute(final TagAttribute attribute, final FaceletContext ctx,
            final String defaultValue) {
        if (null == attribute) {
            return defaultValue;
        }
        var value = (String) attribute.getObject(ctx);
        if (MoreStrings.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    private static void writeNamespaceDeclarations(final PrintWriter writer,
            final List<DashboardWidgetModel> widgetList) {
        final List<String> taglibRegistry = new ArrayList<>();
        for (final DashboardWidgetModel widget : widgetList) {
            final var currentComponentNS = widget.getCompositeComponentId().substring(0,
                    widget.getCompositeComponentId().lastIndexOf(':'));
            if (!taglibRegistry.contains(currentComponentNS)) {
                taglibRegistry.add(currentComponentNS);
                writer.println("xmlns:" + currentComponentNS + "=\"http://xmlns.jcp.org/jsf/composite/"
                        + currentComponentNS + "\"");
            }
        }
    }

    private void writeWidgetElements(final PrintWriter writer, final List<DashboardWidgetModel> widgetList,
            final String widgetStyleClass) {
        var widgetIndex = 0;
        for (final DashboardWidgetModel widget : widgetList) {
            writer.println("<div class=\"" + widgetStyleClass + "\">");
            final var currentComponentNS = widget.getCompositeComponentId().substring(0,
                    widget.getCompositeComponentId().lastIndexOf(':'));
            final var componentName = widget.getCompositeComponentId()
                    .substring(widget.getCompositeComponentId().lastIndexOf(':') + 1);
            final var beanValExpStr = widgetsAttr.getValue().substring(0, widgetsAttr.getValue().lastIndexOf('}'))
                    + ".get(" + widgetIndex + ")}";
            widgetIndex++;
            writer.println("<" + currentComponentNS + ":" + componentName + " id=\"" + widget.getId()
                    + "\" model=\"" + beanValExpStr + "\" />");
            writer.println("</div>");
        }
    }
}
