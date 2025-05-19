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
package de.cuioss.jsf.api.components.model.result_content;

import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.ERROR;
import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.INFO;
import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.WARN;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;

/**
 * Default error handler for processing {@link ResultObject}s and displaying appropriate notifications.
 * <p>
 * This class provides standardized handling of errors and other result states from
 * {@link ResultObject}s, ensuring consistent logging, notification display, and content
 * rendering decisions across components. It translates between the result states
 * defined in {@link ResultState} and the contextual states used for UI display in
 * {@link ContextState}.
 * </p>
 * <p>
 * Key features:
 * </p>
 * <ul>
 *   <li>Standardized handling of different result states (ERROR, WARNING, INFO, VALID)</li>
 *   <li>Consistent logging of exceptions with appropriate log levels</li>
 *   <li>Setting of notification messages with appropriate styling</li>
 *   <li>Control over content rendering based on result state</li>
 * </ul>
 * <p>
 * This class is typically used by components that need to process results from
 * service calls or background operations and display appropriate feedback to users.
 * </p>
 * <p>
 * This class is stateless and thread-safe.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 1.0
 * @see ResultObject
 * @see ErrorController
 * @see ResultState
 */
public class ResultErrorHandler {

    /**
     * Handles a result detail by translating it into appropriate UI notifications
     * and logging actions.
     * <p>
     * This method processes a {@link ResultDetail} based on its {@link ResultState}
     * and applies appropriate actions through the provided {@link ErrorController}:
     * </p>
     * <ul>
     *   <li>{@link ResultState#ERROR}: Logs at ERROR level, displays DANGER notification,
     *       prevents content rendering</li>
     *   <li>{@link ResultState#WARNING}: Logs at WARN level, displays WARNING notification,
     *       allows content rendering</li>
     *   <li>{@link ResultState#INFO}: Logs at INFO level, displays INFO notification,
     *       allows content rendering</li>
     *   <li>{@link ResultState#VALID}: Displays SUCCESS notification, allows content rendering</li>
     * </ul>
     * <p>
     * For all states except VALID, if a cause (exception) is present, it will be included
     * in the log message.
     * </p>
     *
     * @param state the result state to process
     * @param detail the result detail containing the message and optional cause
     * @param errorCode an optional error code enum (currently not used)
     * @param errorController the controller to set notification messages and rendering decisions
     * @param log the logger to use for logging error details
     * @throws UnsupportedOperationException if an unsupported result state is provided
     */
    public void handleResultDetail(ResultState state, ResultDetail detail, Enum<?> errorCode,
            ErrorController errorController, CuiLogger log) {
        ContextState contextState;
        errorController.setRenderContent(true);
        var cause = detail.getCause();
        switch (state) {
            case ERROR:
                log.error(cause.orElse(null), ERROR.ERROR_HANDLED_SILENT::format);
                contextState = ContextState.DANGER;
                errorController.setRenderContent(false);
                break;
            case WARNING:
                cause.ifPresent(throwable -> log.warn(throwable, WARN.ERROR_HANDLED_SILENT::format));
                contextState = ContextState.WARNING;
                break;
            case INFO:
                cause.ifPresent(throwable -> log.info(throwable, INFO.ERROR_HANDLED_SILENT::format));
                contextState = ContextState.INFO;
                break;
            case VALID:
                contextState = ContextState.SUCCESS;
                break;
            default:
                throw new UnsupportedOperationException("No resolving defined for -> " + state, cause.orElse(null));
        }

        errorController.addNotificationBox(detail.getDetail(), contextState);
    }
}
