package de.cuioss.jsf.bootstrap.dashboard;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagHandler;

import de.cuioss.jsf.api.components.model.widget.DashboardWidgetModel;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.MoreStrings;

public class DashboardTagHandler extends TagHandler {

    private static final CuiLogger log = new CuiLogger(DashboardTagHandler.class);

    private final TagAttribute widgetsAttr;
    private final TagAttribute widgetStyleClassAttr;
    private final TagAttribute styleClassAttr;
    private final TagAttribute styleAttr;

    public DashboardTagHandler(final ComponentConfig config) {
        super(config);
        widgetsAttr = getAttribute("widgets");
        widgetStyleClassAttr = getAttribute("widgetStyleClass");
        styleClassAttr = getAttribute("styleClass");
        styleAttr = getAttribute("style");
    }

    @Override
    public void apply(final FaceletContext ctx, final UIComponent parent) throws IOException {
        if (null == widgetsAttr) {
            throw new IllegalArgumentException("Property widgets not set!");
        }

        String styleClass;
        String widgetStyleClass;
        final String style;

        if (null == styleClassAttr) {
            styleClass = null;
        } else {
            styleClass = (String) styleClassAttr.getObject(ctx);
        }
        if (MoreStrings.isEmpty(styleClass)) {
            styleClass = "dashboard-wrapper";
        }

        if (null == widgetStyleClassAttr) {
            widgetStyleClass = null;
        } else {
            widgetStyleClass = (String) widgetStyleClassAttr.getObject(ctx);
        }
        if (MoreStrings.isEmpty(widgetStyleClass)) {
            widgetStyleClass = "col-sm-12 col-md-6 col-lg-4";
        }

        if (null == styleAttr) {
            style = null;
        } else {
            style = (String) styleAttr.getObject(ctx);
        }

        final var widgetList = (List<DashboardWidgetModel>) widgetsAttr.getObject(ctx);
        final var f = new File(System.getProperty("java.io.tmpdir"), "dashboard" + widgetList.hashCode() + ".xhtml");
        if (!f.exists()) {
            final var writer = new PrintWriter(f, "UTF-8");
            final List<String> taglibRegistry = new ArrayList<>();
            writer.println(
                    "<ui:component xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"");
            for (final DashboardWidgetModel widget : widgetList) {
                final var currentComponentNS =
                    widget.getCompositeComponentId()
                            .substring(0, widget.getCompositeComponentId().lastIndexOf(':'));
                if (!taglibRegistry.contains(currentComponentNS)) {
                    taglibRegistry.add(currentComponentNS);
                    writer.println(
                            "xmlns:" + currentComponentNS + "=\"http://xmlns.jcp.org/jsf/composite/"
                                    + currentComponentNS
                                    + "\"");
                }
            }
            writer.println(">");
            writer.print("<div class=\"" + styleClass);
            if (!MoreStrings.isEmpty(style)) {
                writer.print("\" style=\"" + style);
            }
            writer.println("\">");
            var widgetIndex = 0;
            for (final DashboardWidgetModel widget : widgetList) {
                writer.println("<div class=\"" + widgetStyleClass + "\">");
                final var currentComponentNS =
                    widget.getCompositeComponentId()
                            .substring(0, widget.getCompositeComponentId().lastIndexOf(':'));
                final var componentName =
                    widget.getCompositeComponentId()
                            .substring(widget.getCompositeComponentId().lastIndexOf(':') + 1);
                final var beanValExpStr =
                    widgetsAttr.getValue().substring(0, widgetsAttr.getValue().lastIndexOf('}')) + ".get("
                            + widgetIndex
                            + ")}";
                widgetIndex++;
                writer.println(
                        "<" + currentComponentNS + ":" + componentName + " id=\"" + widget.getId() + "\" model=\""
                                + beanValExpStr + "\" />");
                writer.println("</div>");
            }

            writer.println("</div>");
            writer.println("</ui:component>");
            writer.close();
        }
        try {
            nextHandler.apply(ctx, parent);
            ctx.includeFacelet(parent, f.toURI().toURL());
        } catch (final IOException e) {
            log.warn("include facet failed: " + f.getAbsolutePath(), e);
        }
        f.deleteOnExit();
    }
}
