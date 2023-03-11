package de.cuioss.jsf.components.chart.layout.shadow;

import java.io.Serializable;
import java.util.List;

import de.cuioss.jsf.components.js.support.IPropertyProvider;
import de.cuioss.jsf.components.js.support.JavaScriptSupport;
import de.cuioss.jsf.components.js.support.PropertyProvider;
import de.cuioss.jsf.components.js.types.JsBoolean;
import de.cuioss.jsf.components.js.types.JsDouble;
import de.cuioss.jsf.components.js.types.JsInteger;
import de.cuioss.jsf.components.js.types.JsString;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Decorating any target class with shadow properties
 *
 * @author Eugen Fischer
 * @param <T> user class, will be used for fluent api / builder
 */
@ToString(exclude = { "userClass" })
@EqualsAndHashCode(exclude = { "userClass" })
public class Shadow<T extends Serializable> implements Serializable, IShadowDecoration<T>,
        IPropertyProvider {

    /** serialVersionUID */
    private static final long serialVersionUID = 569815887703832188L;

    private final PropertyProvider propProvider = new PropertyProvider();

    private final T userClass;

    /**
     * @param userClass
     */
    public Shadow(@NonNull final T userClass) {
        this.userClass = userClass;
    }

    private JsBoolean shadowed;

    @Override
    public T setShadow(final Boolean value) {
        this.shadowed = JsBoolean.create(value);
        return userClass;
    }

    private JsDouble shadowAngle;

    @Override
    public T setShadowAngle(final Double value) {
        this.shadowAngle = new JsDouble(value);
        return userClass;
    }

    private JsInteger shadowOffset;

    @Override
    public T setShadowOffset(final Integer value) {
        this.shadowOffset = new JsInteger(value);
        return userClass;
    }

    private JsInteger shadowDepth;

    @Override
    public T setShadowDepth(final Integer value) {
        this.shadowDepth = new JsInteger(value);
        return userClass;
    }

    private JsString shadowAlpha;

    @Override
    public T setShadowAlpha(final String value) {
        this.shadowAlpha = new JsString(value);
        return userClass;
    }

    @Override
    public List<JavaScriptSupport> getProperties() {
        propProvider.addProperty("shadow", shadowed);
        propProvider.addProperty("shadowAlpha", shadowAlpha);
        propProvider.addProperty("shadowAngle", shadowAngle);
        propProvider.addProperty("shadowDepth", shadowDepth);
        propProvider.addProperty("shadowOffset", shadowOffset);
        return propProvider.getProperties();
    }

}
