package de.cuioss.jsf.jqplot.renderer;

import de.cuioss.test.valueobjects.junit5.contracts.ShouldBeSerializable;

class LabelRendererTest implements ShouldBeSerializable<LabelRenderer> {

    @Override
    public LabelRenderer getUnderTest() {
        return new LabelRenderer();
    }

}
