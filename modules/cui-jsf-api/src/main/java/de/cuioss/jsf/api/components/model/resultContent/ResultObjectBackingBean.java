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

import de.cuioss.jsf.api.composite.AttributeAccessorImpl;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.result.ResultObject;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UINamingContainer;

@FacesComponent("de.cuioss.jsf.api.components.model.resultContent.ResultObjectBackingBean")
public class ResultObjectBackingBean extends UINamingContainer {

    private static final CuiLogger log = new CuiLogger(ResultObjectBackingBean.class);

    private final AttributeAccessorImpl<ResultObject> modelAccessor = new AttributeAccessorImpl<>("model",
            ResultObject.class, false);

    private ResultContent resultContent;

    public ResultContent getResultContent() {
        if (null == resultContent) {
            resultContent = new ResultContent(modelAccessor.value(getAttributes()), log);
        }
        return resultContent;
    }
}
