package de.cuioss.jsf.jqplot.js.support;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Oliver Wolff
 * @param <T> at least {@link JsValue}
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class JsProperty<T extends JsValue> implements JavaScriptSupport {

    private static final String JSON_FORMAT = "%s:%s";

    /** serial version UID */
    private static final long serialVersionUID = 8197592435718895633L;

    @Getter
    private final String propertyName;

    private final T propertyValue;

    @Override
    public String asJavaScriptObjectNotation() {
        if (null == propertyValue) {
            return null;
        }

        final var valueAsString = propertyValue.getValueAsString();

        if (null == valueAsString) {
            return null;
        }

        return String.format(JSON_FORMAT, propertyName, valueAsString);
    }

}
