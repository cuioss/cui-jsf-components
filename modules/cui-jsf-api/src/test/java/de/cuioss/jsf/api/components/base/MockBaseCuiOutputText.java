package de.cuioss.jsf.api.components.base;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;

@SuppressWarnings("javadoc")
public class MockBaseCuiOutputText extends BaseCuiOutputText {

    @Override
    public StyleClassBuilder getComponentSpecificStyleClasses() {
        return new StyleClassBuilderImpl("mock");
    }

}
