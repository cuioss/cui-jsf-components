package de.icw.cui.core.api.components.model.lazyloading;

import de.cuioss.uimodel.result.ResultObject;
import de.icw.cui.core.api.components.model.resultContent.ResultErrorHandler;

/**
 * Enrich a {@link LazyLoadingModel} with a type and a request id to be handled in a asynchronous
 * thread.
 */
public interface LazyLoadingThreadModel<T> extends LazyLoadingModel {

    /**
     * @return a unique request id.
     */
    long getRequestId();

    void resetNotificationBox();

    void handleRequestResult(ResultObject<T> result, ResultErrorHandler errorHandler);
}
