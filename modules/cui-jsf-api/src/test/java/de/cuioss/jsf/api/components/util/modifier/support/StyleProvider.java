package de.cuioss.jsf.api.components.util.modifier.support;

import javax.faces.component.UIComponentBase;

import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class StyleProvider extends UIComponentBase implements StyleAttributeProvider {

    @Getter
    @Setter
    private String style;

    @Override
    public String getFamily() {
        return "StyleProvider";
    }

}
