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

import de.cuioss.jsf.api.composite.AttributeAccessorImpl;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.result.ResultObject;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UINamingContainer;

/**
 * <h2>Backing bean for ResultObject handling in composite components</h2>
 * <p>
 * This component serves as a bridge between the JSF component model and the
 * {@link ResultObject} model used throughout the application. It provides
 * a consistent way to expose a {@link ResultContent} instance that wraps
 * the original {@link ResultObject} for use within JSF composite components.
 * </p>
 * <p>
 * Key features:
 * </p>
 * <ul>
 * <li>Implements lazy initialization of the ResultContent wrapper</li>
 * <li>Provides proper logging for result object handling</li>
 * <li>Simplifies interaction with the result model in composite components</li>
 * <li>Extracts the model from component attributes</li>
 * </ul>
 * <p>
 * Typical usage in a composite component:
 * </p>
 * <pre>
 * &lt;cc:implementation&gt;
 *   &lt;ui:component&gt;
 *     &lt;h:panelGroup rendered="#{cc.result_content.hasErrorMessages}"&gt;
 *       &lt;!-- Error message display --&gt;
 *     &lt;/h:panelGroup&gt;
 *   &lt;/ui:component&gt;
 * &lt;/cc:implementation&gt;
 * </pre>
 * 
 * @author Oliver Wolff
 */
@SuppressWarnings({"java:S1874", "java:S3740"})
// ResultObject from external dependency; raw type in generic framework utilities by design
@FacesComponent("de.cuioss.jsf.api.components.model.resultContent.ResultObjectBackingBean")
public class ResultObjectBackingBean extends UINamingContainer {

    private static final CuiLogger LOGGER = new CuiLogger(ResultObjectBackingBean.class);

    /**
     * The attribute accessor used to retrieve the ResultObject model from the component's attributes.
     * The model is mandatory for this component to function properly.
     */
    private final AttributeAccessorImpl<ResultObject> modelAccessor = new AttributeAccessorImpl<>("model",
            ResultObject.class, false);

    /**
     * Cached instance of ResultContent to avoid unnecessary recreation.
     */
    private ResultContent resultContent;

    /**
     * <p>Retrieves the wrapped ResultContent instance for the current ResultObject model.</p>
     * <p>This method implements lazy initialization, creating the ResultContent wrapper
     * only when needed and then caching it for subsequent calls.</p>
     * <p>The ResultContent provides convenient access to the result state, messages,
     * and other information relevant for UI display purposes.</p>
     *
     * @return The ResultContent instance wrapping the current ResultObject model.
     *         Never returns null as long as the required model attribute is provided.
     */
    public ResultContent getResultContent() {
        if (null == resultContent) {
            resultContent = new ResultContent(modelAccessor.value(getAttributes()), LOGGER);
        }
        return resultContent;
    }
}
