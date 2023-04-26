package de.cuioss.jsf.jqplot.options.label;

import java.io.Serializable;
import java.util.List;

import de.cuioss.jsf.jqplot.js.support.IPropertyProvider;
import de.cuioss.jsf.jqplot.js.support.JavaScriptSupport;
import de.cuioss.jsf.jqplot.js.support.PropertyProvider;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsInteger;
import de.cuioss.jsf.jqplot.js.types.JsString;
import de.cuioss.jsf.jqplot.options.color.Color;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Eugen Fischer
 * @param <T> decorated class type
 */
@ToString(exclude = { "userClass" })
@EqualsAndHashCode(exclude = { "userClass" })
public class Label<T extends Serializable> implements ILabelDecorator<T>, Serializable,
        IPropertyProvider {

    /** serial Version UID */
    private static final long serialVersionUID = -5383303038095005652L;

    private final PropertyProvider propProvider = new PropertyProvider();

    private final T userClass;

    /**
     * @param userClass
     */
    public Label(@NonNull final T userClass) {
        this.userClass = userClass;
    }

    private JsBoolean showLabel = null;

    @Override
    public T setShowLabel(final Boolean value) {
        this.showLabel = JsBoolean.create(value);
        return userClass;
    }

    private JsString formatString = null;

    @Override
    public T setFormatString(final String value) {
        this.formatString = new JsString(value);
        return userClass;
    }

    private JsString fontFamily = null;

    @Override
    public T setFontFamily(final String value) {
        this.fontFamily = new JsString(value);
        return userClass;
    }

    private JsString fontSize = null;

    @Override
    public T setFontSize(final String value) {
        this.fontSize = new JsString(value);
        return userClass;
    }

    private JsInteger angle;

    /**
     * @param value
     * @return fluent api style
     */
    @Override
    public T setAngle(final Integer value) {
        angle = new JsInteger(value);
        return userClass;
    }

    private Color textColor = null;

    @Override
    public T setTextColor(final String value) {
        this.textColor = Color.createFrom(value);
        return userClass;
    }

    private JsBoolean escapeHTML = null;

    @Override
    public T setEscapeHTML(final Boolean value) {
        this.escapeHTML = JsBoolean.create(value);
        return userClass;
    }

    @Override
    public List<JavaScriptSupport> getProperties() {
        propProvider.addProperty("showLabel", showLabel);
        propProvider.addProperty("fontFamily", fontFamily);
        propProvider.addProperty("fontSize", fontSize);
        propProvider.addProperty("formatString", formatString);
        propProvider.addProperty("angle", angle);
        propProvider.addProperty("textColor", textColor);
        propProvider.addProperty("escapeHTML", escapeHTML);
        return propProvider.getProperties();
    }

}
