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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.faces.application.FacesMessage;

import de.cuioss.jsf.api.application.message.DisplayNameProviderMessageProducer;
import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.portal.common.cdi.PortalBeanManager;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model for requestResultContent.xhtml to display a {@link ResultObject} and
 * handle its errors.
 * <p>
 * Should be part of the view (page bean) and instantiated with the
 * {@link ResultObject} during init view action.
 */
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ResultContent implements ErrorController, Serializable {

    private static final long serialVersionUID = -602764736457587401L;

    @Getter
    private final List<NotificationBoxMessage> notificationBoxMessages = new ArrayList<>();

    @Getter
    @Setter
    private boolean renderContent = true;

    /**
     * Default constructor using {@link ResultErrorHandler}.
     *
     * @param resultObject the {@link ResultObject} to display.
     * @param log          the logger of the page bean.
     */
    public ResultContent(ResultObject<?> resultObject, CuiLogger log) {
        this(resultObject, new ResultErrorHandler(), log);
    }

    /**
     * Constructor with given {@link ResultErrorHandler} implementation.
     *
     * @param resultObject the {@link ResultObject} to display.
     * @param errorHandler a {@link ResultErrorHandler} that will be called when a
     *                     {@link ResultDetail} is present.
     * @param log          the logger of the page bean.
     */
    public ResultContent(ResultObject<?> resultObject, ResultErrorHandler errorHandler, CuiLogger log) {
        var resultDetail = resultObject.getResultDetail();
        if (resultDetail.isPresent()) {
            errorHandler.handleResultDetail(resultObject.getState(), resultDetail.get(),
                    resultObject.getErrorCode().orElse(null), this, log);
        }
    }

    /**
     * Handle (and display) a additional {@link ResultDetail} with default
     * {@link ResultErrorHandler}.
     *
     * @param resultObject a {@link ResultObject} that may contain a
     *                     {@link ResultDetail}. If a does not contain a
     *                     {@link ResultDetail}, nothing will happen.
     * @param log          the logger of the page bean.
     */
    public void handleAdditionalResult(ResultObject<?> resultObject, CuiLogger log) {
        handleAdditionalResult(resultObject, new ResultErrorHandler(), log);
    }

    /**
     * Handle (and display) a additional {@link ResultDetail} with given
     * {@link ResultErrorHandler}.
     *
     * @param resultObject a {@link ResultObject} that may contain a
     *                     {@link ResultDetail}. If a does not contain a
     *                     {@link ResultDetail}, nothing will happen.
     * @param errorHandler a {@link ResultErrorHandler} that will be called when a
     *                     {@link ResultDetail} is present.
     * @param log          the logger of the page bean.
     */
    public void handleAdditionalResult(ResultObject<?> resultObject, ResultErrorHandler errorHandler, CuiLogger log) {
        var resultDetail = resultObject.getResultDetail();
        if (resultDetail.isPresent()) {
            errorHandler.handleResultDetail(resultObject.getState(), resultDetail.get(),
                    resultObject.getErrorCode().orElse(null), this, log);
        }
    }

    @Override
    public void addNotificationBox(IDisplayNameProvider<?> value, ContextState state) {
        notificationBoxMessages.add(new NotificationBoxMessage(value, state));
    }

    public void addNotificationBox(Optional<ResultDetail> resultDetailOptional, ContextState state) {
        resultDetailOptional.ifPresent(resultDetail -> addNotificationBox(resultDetail.getDetail(), state));
    }

    @Override
    public void addGlobalFacesMessage(IDisplayNameProvider<?> value, FacesMessage.Severity severity) {

        PortalBeanManager.resolveRequiredBean(DisplayNameProviderMessageProducer.class).showAsGlobalMessage(value,
                severity);
    }

    @Override
    public void addStickyMessage(IDisplayNameProvider<?> value, ContextState state) {
        //
    }
}
