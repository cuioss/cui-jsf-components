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
package de.cuioss.jsf.api.components.model.resultContent;

import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.ERROR;
import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.INFO;
import static de.cuioss.jsf.api.common.logging.JsfApiLogMessages.WARN;

import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;

/**
 * Default Error-handler for dealing with {@link ResultObject}s
 */
public class ResultErrorHandler {

    // Removed in favor of JsfApiLogMessages.ERROR_HANDLED_SILENT

    /**
     * Handles a potential error.
     *
     * @param state           the state of the {@link ResultDetail}.
     * @param detail          the detail of the {@link ResultDetail}.
     * @param errorCode       currently not used
     * @param errorController an {@link ErrorController} to allow setting a
     *                        notification box or a GlobalFacesMessage.
     * @param log             a {@link CuiLogger} to log the cause.
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
