package com.icw.ehf.cui.components.js.support;

import java.util.ArrayList;
import java.util.List;

import de.cuioss.tools.string.Joiner;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author i000576
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class JsObject implements JavaScriptSupport {

    private static final long serialVersionUID = 9148909574306233473L;

    private final String objectName;

    private final transient PropertyProvider propProvider = new PropertyProvider();

    protected JsObject addProperties(final IPropertyProvider provider) {
        propProvider.addProperties(provider);
        return this;
    }

    protected JsObject addProperty(final String name, final JsValue value) {
        return addProperty(new JsProperty<>(name, value));
    }

    protected JsObject addProperty(final JavaScriptSupport property) {
        propProvider.addProperty(property);
        return this;
    }

    protected String transformeProperties() {
        final var properties = propProvider.getProperties();

        if (properties.isEmpty()) {
            return "";
        }

        final List<String> stringRepresentation = new ArrayList<>(properties.size());
        for (final JavaScriptSupport property : properties) {
            stringRepresentation.add(property.asJavaScriptObjectNotation());
        }
        return Joiner.on(",").skipNulls().join(stringRepresentation);
    }

    /**
     * If no properties denied return {@code null}.<br/>
     * If one property is defined create :
     *
     * <pre>
     *  objectName : {
     *      propertyName : value
     *  }
     * </pre>
     *
     * If more properties are defined create :
     *
     * <pre>
     *  objectName : {
     *      propertyName1 : value,
     *      ...
     *      propertyNameN : value
     *  }
     * </pre>
     */
    protected String createAsJSON() {

        final var stringRepresentation = transformeProperties();
        if (stringRepresentation.isEmpty()) {
            return null;
        }

        final var builder = new StringBuilder()
                .append(objectName)
                .append(": {")
                .append(stringRepresentation)
                .append("}");

        return builder.toString();
    }

    protected String createAsJSONObjectWithoutName() {

        final var stringRepresentation = transformeProperties();
        if (stringRepresentation.isEmpty()) {
            return null;
        }

        final var builder = new StringBuilder()
                .append("{")
                .append(stringRepresentation)
                .append("}");

        return builder.toString();
    }
}
