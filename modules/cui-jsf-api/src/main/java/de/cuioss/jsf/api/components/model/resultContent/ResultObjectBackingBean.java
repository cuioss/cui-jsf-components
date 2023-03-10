package de.cuioss.jsf.api.components.model.resultContent;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import de.cuioss.jsf.api.composite.AttributeAccessorImpl;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.result.ResultObject;

@FacesComponent("de.cuioss.jsf.api.components.model.resultContent.ResultObjectBackingBean")
public class ResultObjectBackingBean extends UINamingContainer {

    private static final CuiLogger log = new CuiLogger(ResultObjectBackingBean.class);

    private final AttributeAccessorImpl<ResultObject> modelAccessor =
        new AttributeAccessorImpl<>("model", ResultObject.class, false);

    private ResultContent resultContent;

    public ResultContent getResultContent() {
        if (null == resultContent) {
            resultContent = new ResultContent(modelAccessor.value(getAttributes()), log);
        }
        return resultContent;
    }
}
