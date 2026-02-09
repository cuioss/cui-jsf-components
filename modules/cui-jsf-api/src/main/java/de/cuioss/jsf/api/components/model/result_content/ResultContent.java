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
package de.cuioss.jsf.api.components.model.result_content;

import de.cuioss.jsf.api.application.message.DisplayNameMessageProducer;
import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.portal.common.cdi.PortalBeanManager;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.result.ResultObject;
import jakarta.faces.application.FacesMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for displaying and handling {@link ResultObject}s in JSF views.
 * <p>
 * This class implements the {@link ErrorController} interface to provide a standard
 * mechanism for displaying notification messages and controlling content visibility
 * in response-to-operation results.
 * It is designed to be used with the composite
 * component "requestResultContent.xhtml" to render the appropriate UI elements.
 * </p>
 * <p>
 * Key features:
 * </p>
 * <ul>
 *   <li>Processing of {@link ResultObject}s and their result details</li>
 *   <li>Management of notification messages for display in the UI</li>
 *   <li>Control over content rendering based on result status</li>
 *   <li>Support for global faces messages</li>
 *   <li>Integration with standard logging</li>
 * </ul>
 * <p>
 * Usage pattern:
 * </p>
 * <pre>
 * &#64;Named
 * &#64;ViewScoped
 * public class PatientEditBean implements Serializable {
 *     
 *     private ResultContent result_content;
 *     
 *     &#64;PostConstruct
 *     public void init() {
 *         var result = patientService.loadPatient(patientId);
 *         result_content = new ResultContent(result, log);
 *         
 *         if (result.isValid()) {
 *             patient = result.getResult();
 *         }
 *     }
 *     
 *     public void save() {
 *         var saveResult = patientService.savePatient(patient);
 *         result_content.handleAdditionalResult(saveResult, log);
 *     }
 *     
 *     public ResultContent getResultContent() {
 *         return result_content;
 *     }
 * }
 * </pre>
 * <p>
 * In the corresponding view file:
 * </p>
 * <pre>
 * &lt;cui:requestResultContent model="#{patientEditBean.result_content}"&gt;
 *     &lt;!-- Content to display when there are no blocking errors --&gt;
 * &lt;/cui:requestResultContent&gt;
 * </pre>
 * <p>
 * This class is not thread-safe and should be used in appropriate scopes
 * (typically view or request scope).
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ResultObject
 * @see ResultErrorHandler
 * @see NotificationBoxMessage
 */
@SuppressWarnings("java:S1874") // ResultObject from external dependency; migration out of scope
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ResultContent implements ErrorController, Serializable {

    @Serial
    private static final long serialVersionUID = -602764736457587401L;

    /**
     * Collection of notification messages to be displayed.
     * <p>
     * Each notification message consists of a display name provider for the message
     * content and a context state determining its visual styling.
     * </p>
     */
    @Getter
    private final List<NotificationBoxMessage> notificationBoxMessages = new ArrayList<>();

    /**
     * Flag controlling whether the main content should be rendered.
     * <p>
     * When set to true, the content within the "requestResultContent" component will
     * be displayed. When false, only notification messages will be shown.
     * </p>
     * <p>
     * Default is true, but certain result states (like ERROR) may set this to false.
     * </p>
     */
    @Getter
    @Setter
    private boolean renderContent = true;

    /**
     * Creates a ResultContent instance that processes the given result object using
     * the default error handler.
     * <p>
     * This constructor immediately processes the result object, extracting any result
     * details and applying appropriate notification messages and rendering decisions.
     * </p>
     *
     * @param resultObject the result object to process
     * @param log the logger to use for logging any errors or warnings
     */
    public ResultContent(ResultObject<?> resultObject, CuiLogger log) {
        this(resultObject, new ResultErrorHandler(), log);
    }

    /**
     * Creates a ResultContent instance that processes the given result object using
     * the specified custom error handler.
     * <p>
     * This constructor immediately processes the result object, extracting any result
     * details and applying appropriate notification messages and rendering decisions
     * based on the provided error handler's logic.
     * </p>
     *
     * @param resultObject the result object to process
     * @param errorHandler the custom error handler to use for processing result details
     * @param log the logger to use for logging any errors or warnings
     */
    public ResultContent(ResultObject<?> resultObject, ResultErrorHandler errorHandler, CuiLogger log) {
        var resultDetail = resultObject.getResultDetail();
        resultDetail.ifPresent(detail -> errorHandler.handleResultDetail(resultObject.getState(), detail,
                resultObject.getErrorCode().orElse(null), this, log));
    }

    /**
     * Processes an additional result object using the default error handler.
     * <p>
     * This method allows handling additional result objects after the initial creation
     * of the ResultContent instance, for example, when multiple operations are performed
     * sequentially and their results need to be accumulated.
     * </p>
     * <p>
     * If the result object contains a result detail, it will be processed and may
     * add notification messages or affect content rendering.
     * </p>
     *
     * @param resultObject the additional result object to process
     * @param log the logger to use for logging any errors or warnings
     */
    public void handleAdditionalResult(ResultObject<?> resultObject, CuiLogger log) {
        handleAdditionalResult(resultObject, new ResultErrorHandler(), log);
    }

    /**
     * Processes an additional result object using the specified custom error handler.
     * <p>
     * This method allows handling additional result objects with custom error handling
     * logic, for operations performed after the initial creation of the ResultContent instance.
     * </p>
     * <p>
     * If the result object contains a result detail, it will be processed according
     * to the error handler's logic and may add notification messages or affect content rendering.
     * </p>
     *
     * @param resultObject the additional result object to process
     * @param errorHandler the custom error handler to use for processing result details
     * @param log the logger to use for logging any errors or warnings
     */
    public void handleAdditionalResult(ResultObject<?> resultObject, ResultErrorHandler errorHandler, CuiLogger log) {
        var resultDetail = resultObject.getResultDetail();
        resultDetail.ifPresent(detail -> errorHandler.handleResultDetail(resultObject.getState(), detail,
                resultObject.getErrorCode().orElse(null), this, log));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementation adds a notification box message to the collection of messages
     * to be displayed in the UI.
     * </p>
     *
     * @param value the message content to display
     * @param state the contextual state determining the visual styling
     */
    @Override
    public void addNotificationBox(IDisplayNameProvider<?> value, ContextState state) {
        notificationBoxMessages.add(new NotificationBoxMessage(value, state));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementation delegates to the {@link DisplayNameMessageProducer} to add
     * a global faces message that will be displayed at the standard messages location.
     * </p>
     *
     * @param value the message content to display
     * @param severity the JSF severity level for the message
     */
    @Override
    public void addGlobalFacesMessage(IDisplayNameProvider<?> value, FacesMessage.Severity severity) {
        PortalBeanManager.resolveRequiredBean(DisplayNameMessageProducer.class).showAsGlobalMessage(value, severity);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Current implementation does nothing. Future implementations may provide
     * sticky message functionality.
     * </p>
     *
     * @param value the message content to display (ignored in current implementation)
     * @param state the contextual state (ignored in current implementation)
     */
    @Override
    public void addStickyMessage(IDisplayNameProvider<?> value, ContextState state) {
        // No implementation yet
    }
}
