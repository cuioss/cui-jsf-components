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
package de.cuioss.jsf.api.components.model.lazyloading;

import de.cuioss.jsf.api.components.model.result_content.ResultErrorHandler;
import de.cuioss.uimodel.result.ResultObject;

/**
 * Extension of {@link LazyLoadingModel} that supports asynchronous content loading in a separate thread.
 * <p>
 * This interface adds functionality for managing asynchronous loading operations by providing:
 * </p>
 * <ul>
 *   <li>Request identification for tracking asynchronous operations</li>
 *   <li>Result handling for both successful and error outcomes</li>
 *   <li>Notification box management for displaying messages</li>
 * </ul>
 * <p>
 * The asynchronous loading pattern typically involves:
 * </p>
 * <ol>
 *   <li>A component initiates a loading request with a unique ID</li>
 *   <li>The loading operation runs in a background thread</li>
 *   <li>When complete, the result is passed back to this model via {@link #handleRequestResult}</li>
 *   <li>The component displays either the loaded content or an error message</li>
 * </ol>
 * <p>
 * This model helps manage the complexities of asynchronous loading while providing
 * hooks for both success and error scenarios.
 * </p>
 * <p>
 * Implementation note: Classes implementing this interface must ensure thread safety
 * since they will be accessed from multiple threads.
 * </p>
 *
 * @param <T> the type of content being loaded asynchronously
 * @author Oliver Wolff
 * @since 1.0
 * @see LazyLoadingModel
 * @see ResultObject
 */
public interface LazyLoadingThreadModel<T> extends LazyLoadingModel {

    /**
     * Returns a unique identifier for the current loading request.
     * <p>
     * This ID is used to correlate asynchronous loading operations with their results.
     * When a background thread completes loading, it uses this ID to identify which
     * model instance should receive the result.
     * </p>
     * <p>
     * Implementation note: The request ID should be unique within the scope of 
     * concurrent loading operations to ensure correct result routing.
     * </p>
     *
     * @return a unique identifier string for the current loading request
     */
    String getRequestId();

    /**
     * Clears any notification messages currently displayed by the model.
     * <p>
     * This method should reset the notification box state, typically removing any
     * error or status messages being displayed. It is usually called before initiating
     * a new loading operation to ensure a clean state.
     * </p>
     */
    void resetNotificationBox();

    /**
     * Processes the result of an asynchronous loading operation.
     * <p>
     * This method is called when a background loading operation completes, either
     * successfully or with an error. It provides both the result object (which may
     * contain the requested data or an error) and an error handler to assist with
     * processing failure cases.
     * </p>
     * <p>
     * Implementations should:
     * </p>
     * <ol>
     *   <li>Check if the result was successful using {@link ResultObject#isValid()}</li>
     *   <li>For successful results, process and store the result data</li>
     *   <li>For errors, use the error handler to set appropriate notification messages</li>
     *   <li>Update the model's state to reflect completion (e.g., setting initialized to true)</li>
     * </ol>
     *
     * @param result the result object containing either the loaded data or error information
     * @param errorHandler a helper object for processing and displaying errors
     */
    void handleRequestResult(ResultObject<T> result, ResultErrorHandler errorHandler);
}
