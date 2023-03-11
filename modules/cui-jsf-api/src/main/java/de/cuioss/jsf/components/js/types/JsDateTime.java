package de.cuioss.jsf.components.js.types;

import java.time.temporal.Temporal;

import de.cuioss.jsf.components.js.support.JsValue;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/**
 * Provide safe way to act with Date as JSON property.
 *
 * @author Eugen Fischer
 */
@ToString(of = { "value" })
@EqualsAndHashCode(of = { "value" })
@Builder
@Value
public class JsDateTime implements JsValue {

    private static final long serialVersionUID = -726077446906162610L;

    @SuppressWarnings("squid:S1948") // All known implementations are Serializable
    private final Temporal value;

    @NonNull
    private final JsDateTimeFormat formatter;

    @Override
    public String getValueAsString() {
        if (null == value) {
            return null;
        }
        return new JsString(formatter.format(value)).getValueAsString();
    }

}
