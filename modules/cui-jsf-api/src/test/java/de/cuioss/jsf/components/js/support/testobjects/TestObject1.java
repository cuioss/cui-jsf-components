package de.cuioss.jsf.components.js.support.testobjects;

import de.cuioss.jsf.components.js.support.JsObject;
import de.cuioss.jsf.components.js.types.JsString;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * Example implementation
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("javadoc")
public class TestObject1 extends JsObject {

    /** serial Version UID */
    private static final long serialVersionUID = -6791338760451480884L;

    public TestObject1() {
        super("testObject1");
    }

    private JsString someStringProperty;

    public TestObject1 setSomeStringProperty(final String value) {
        this.someStringProperty = new JsString(value);
        return this;
    }

    @Setter
    private TestObject2 testObject2;

    @Override
    public String asJavaScriptObjectNotation() {
        this.addProperty("someStringProperty", someStringProperty);
        this.addProperty(testObject2);
        return this.createAsJSON();
    }

}
