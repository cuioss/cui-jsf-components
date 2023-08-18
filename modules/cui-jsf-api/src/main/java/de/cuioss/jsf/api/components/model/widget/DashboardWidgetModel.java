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
 * Model interface for the DashboardWidgetRegistration to collect all widgets to
 * be rendered by the
 * {@link de.cuioss.jsf.bootstrap.dashboard.DashboardTagHandler}.
 */
public interface DashboardWidgetModel extends WidgetModel {

    /**
     * Link a {@link WidgetModel} to a composite component that should be used to
     * render the widget.
     *
     * @return the folder of the composite component inside of the resources
     *         directory and the name of the composite component file. (e.g.
     *         "cui-composite:listItemWidget" for a component named
     *         "listItemWidget.xhtml" in
     *         /src/main/resources/META-INF/resources/cui-composite).
     */
    String getCompositeComponentId();
}
