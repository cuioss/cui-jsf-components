package de.cuioss.jsf.bootstrap.layout.input.support;

import javax.faces.component.html.HtmlInputText;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class MockUIInput extends HtmlInputText {

    @Getter
    @Setter
    private boolean resetValueCalled = false;

    @Override
    public void resetValue() {
        resetValueCalled = true;
        super.resetValue();
    }
}
