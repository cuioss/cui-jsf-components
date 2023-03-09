package com.icw.ehf.cui.core.api.components.util.modifier.support;

import javax.faces.component.UIComponentBase;

import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.partial.ComponentStyleClassProvider;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class StyleClassProvider extends UIComponentBase implements ComponentStyleClassProvider {

    @Getter
    @Setter
    private String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return null;
    }

    @Override
    public String getFamily() {
        return "StyleClassProvider";
    }

}
