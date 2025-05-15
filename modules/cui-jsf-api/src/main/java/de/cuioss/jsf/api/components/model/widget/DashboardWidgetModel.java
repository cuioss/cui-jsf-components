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
package de.cuioss.jsf.api.components.model.widget;

/**
 * Model interface for widgets that can be rendered within a dashboard layout.
 * <p>
 * This interface extends {@link WidgetModel} to add dashboard-specific functionality,
 * particularly the ability to identify which composite component should be used to
 * render the widget. This allows for a flexible widget registry system where widgets
 * can be dynamically collected and rendered by the dashboard component.
 * </p>
 * <p>
 * Widgets implementing this interface can be registered with the dashboard system
 * and will be rendered using their specified composite component templates.
 * </p>
 * <p>
 * The dashboard widget infrastructure enables the creation of modular, reusable
 * widgets that can be arranged and displayed within dashboard layouts.
 * </p>
 * <p>
 * Implementation classes should consider thread safety for their specific use cases.
 * </p>
 * 
 * @author Matthias Walliczek
 * @since 1.0
 * @see WidgetModel
 * @see BaseDeferredLoadingWidget
 */
public interface DashboardWidgetModel extends WidgetModel {

    /**
     * Returns the composite component identifier for rendering this widget.
     * <p>
     * This method provides the link between a widget model and its visual representation
     * by identifying which composite component should be used to render the widget.
     * The dashboard system uses this identifier to dynamically load and render the 
     * appropriate component for each widget.
     * </p>
     * <p>
     * The format of the identifier follows the JSF composite component namespace pattern,
     * consisting of a library name and component name separated by a colon.
     * </p>
     *
     * @return the composite component identifier in the format "library:componentName",
     *         for example "cui-composite:listItemWidget" for a component named
     *         "listItemWidget.xhtml" located in
     *         /src/main/resources/META-INF/resources/cui-composite
     */
    String getCompositeComponentId();
}
