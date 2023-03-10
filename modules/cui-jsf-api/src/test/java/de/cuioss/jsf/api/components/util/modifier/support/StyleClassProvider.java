package de.cuioss.jsf.api.components.util.modifier.support;

import javax.faces.component.UIComponentBase;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
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
