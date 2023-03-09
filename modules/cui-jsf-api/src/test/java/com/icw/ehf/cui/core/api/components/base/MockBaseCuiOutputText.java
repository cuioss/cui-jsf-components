package com.icw.ehf.cui.core.api.components.base;

import com.icw.ehf.cui.core.api.components.css.StyleClassBuilder;
import com.icw.ehf.cui.core.api.components.css.impl.StyleClassBuilderImpl;

@SuppressWarnings("javadoc")
public class MockBaseCuiOutputText extends BaseCuiOutputText {

    @Override
    public StyleClassBuilder getComponentSpecificStyleClasses() {
        return new StyleClassBuilderImpl("mock");
    }

}
