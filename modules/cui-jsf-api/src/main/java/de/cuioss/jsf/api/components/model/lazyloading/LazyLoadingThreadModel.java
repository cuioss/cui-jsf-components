package de.cuioss.jsf.api.components.model.lazyloading;

import de.cuioss.jsf.api.components.model.resultContent.ResultErrorHandler;
import de.cuioss.uimodel.result.ResultObject;

/**
 * Enrich a {@link LazyLoadingModel} with a type and a request id to be handled
 * in a asynchronous thread.
 */
public interface LazyLoadingThreadModel<T> extends LazyLoadingModel {

    /**
     * @return a unique request id.
     */
    long getRequestId();

    void resetNotificationBox();

    void handleRequestResult(ResultObject<T> result, ResultErrorHandler errorHandler);
}
