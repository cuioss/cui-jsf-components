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
package de.cuioss.jsf.api.components.model.widget;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.faces.event.ActionEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Synchronized;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract implementation of the {@link WidgetModel} supporting deferred
 * loading of the content and/or error message.
 * <p>
 * This class extends {@link BaseWidget} to provide lazy-loading capabilities for widget content.
 * It implements the {@link DashboardWidgetModel} interface, making it suitable for use in
 * dashboard-style layouts where multiple widgets need to load their content efficiently.
 * </p>
 * <p>
 * Key features:
 * </p>
 * <ul>
 *   <li>Generic content type parameter for type-safe content handling</li>
 *   <li>Initialization state tracking</li>
 *   <li>Error reporting through notification box</li>
 *   <li>Thread-safe initialization process through synchronized methods</li>
 * </ul>
 * <p>
 * The initialization process is triggered by the {@link #processAction(ActionEvent)} method,
 * which calls the abstract {@link #doInit()} method that subclasses must implement
 * to populate the widget's content or set error messages.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * public class PatientSummaryWidget extends BaseDeferredLoadingWidget&lt;PatientSummary&gt; {
 *     
 *     &#64;Override
 *     protected void doInit() {
 *         try {
 *             content = patientService.loadSummary(patientId);
 *         } catch (Exception e) {
 *             errorString = new SimpleDisplayNameProvider("Error loading patient summary");
 *         }
 *     }
 *     
 *     &#64;Override
 *     public IDisplayNameProvider&lt;?&gt; getTitle() {
 *         return new SimpleDisplayNameProvider("Patient Summary");
 *     }
 *     
 *     &#64;Override
 *     public String getId() {
 *         return "patientSummaryWidget";
 *     }
 * }
 * </pre>
 * <p>
 * This class uses synchronization to ensure thread safety during initialization.
 * </p>
 *
 * @param <T> type of the content stored and displayed by this widget
 * @author Matthias Walliczek
 * @since 1.0
 * @see BaseWidget
 * @see DashboardWidgetModel
 * @see WidgetModel
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDeferredLoadingWidget<T extends Serializable> extends BaseWidget
        implements DashboardWidgetModel {

    @Serial
    private static final long serialVersionUID = -8914809364978152106L;

    /**
     * The content loaded and displayed by this widget.
     * <p>
     * This field is populated during initialization and can be accessed
     * by subclasses to render the widget content.
     * </p>
     */
    @Getter
    protected T content;

    /**
     * Flag indicating whether the widget has been initialized.
     * <p>
     * When false, the widget is in its initial state and content hasn't been loaded yet.
     * When true, the widget has been initialized (successfully or with errors).
     * </p>
     */
    @Getter
    private boolean initialized;

    /**
     * Error message to be displayed in the notification box if initialization fails.
     * <p>
     * This field should be set by the {@link #doInit()} method when an error occurs
     * during content loading. When non-null, it indicates an error state.
     * </p>
     */
    protected IDisplayNameProvider<?> errorString;

    /**
     * {@inheritDoc}
     * <p>
     * Returns the error message if initialization failed, or null if the widget
     * initialized successfully or hasn't been initialized yet.
     * </p>
     * 
     * @return the error message provider or null
     */
    @Override
    public IDisplayNameProvider<?> getNotificationBoxValue() {
        return errorString;
    }

    /**
     * Initializes the widget content.
     * <p>
     * This method is called during the initialization process to load the widget's
     * content or set error messages. Implementations should:
     * </p>
     * <ul>
     *   <li>Attempt to load/initialize the required content</li>
     *   <li>Set the {@code content} field with the loaded data on success</li>
     *   <li>Set the {@code errorString} field with an appropriate message on failure</li>
     * </ul>
     * <p>
     * This method may perform potentially time-consuming operations since it's only
     * called once per widget lifetime.
     * </p>
     */
    protected abstract void doInit();

    /**
     * {@inheritDoc}
     * <p>
     * Handles the action event by initializing the widget content if not already initialized.
     * This implementation uses synchronization to ensure thread safety during initialization.
     * </p>
     * 
     * @param actionEvent the action event that triggered the processing
     */
    @Override
    @Synchronized
    public void processAction(ActionEvent actionEvent) {
        if (!isInitialized()) {
            doInit();
            initialized = true;
        }
    }
}
