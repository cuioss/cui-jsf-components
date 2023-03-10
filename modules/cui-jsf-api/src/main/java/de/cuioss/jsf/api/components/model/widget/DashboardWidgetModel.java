package de.cuioss.jsf.api.components.model.widget;

/**
 * Model interface for the com.icw.ehf.cui.portal.application.dashboard.DashboardWidgetRegistration
 * to collect all widgets to be rendered by the
 * {@link de.cuioss.jsf.bootstrap.dashboard.DashboardTagHandler}.
 */
public interface DashboardWidgetModel extends WidgetModel {

    /**
     * Link a {@link WidgetModel} to a composite component that should be used to render the widget.
     *
     * @return the folder of the composite component inside of the resources directory and the name
     *         of the
     *         composite component file. (e.g. "cui-composite:listItemWidget" for a component named
     *         "listItemWidget.xhtml" in
     *         /src/main/resources/META-INF/resources/cui-composite).
     */
    String getCompositeComponentId();
}
