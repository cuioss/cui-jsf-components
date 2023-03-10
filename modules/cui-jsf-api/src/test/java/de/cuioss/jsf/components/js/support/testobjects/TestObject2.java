package de.cuioss.jsf.components.js.support.testobjects;

import de.cuioss.jsf.components.js.support.JsObject;
import de.cuioss.jsf.components.js.types.JsString;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Example implementation
 *
 * @author i000576
 */
@ToString
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("javadoc")
public class TestObject2 extends JsObject {

    /** serial Version UID */
    private static final long serialVersionUID = -2458207725778585937L;

    public TestObject2() {
        super("testObject2");
    }

    private JsString someStringProperty;

    public TestObject2 setSomeStringProperty(final String value) {
        this.someStringProperty = new JsString(value);
        return this;
    }

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("someStringProperty", someStringProperty);
        return this.createAsJSON();
    }

}
