package com.icw.ehf.cui.core.api.components.support;

import javax.faces.component.UIComponentBase;

import lombok.Getter;
import lombok.Setter;

/**
 * Simple dummy component for cases where a non-null component as parameter is
 * needed.
 *
 * @author Oliver Wolff
 *
 */
public class DummyComponent extends UIComponentBase {

    @Getter
    @Setter
    private String clientId = "dummyId";

    @Override
    public String getFamily() {
        return "dummy";
    }

}
