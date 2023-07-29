package de.cuioss.jsf.jqplot.renderer.highlight;

import java.io.Serializable;
import java.util.List;

import de.cuioss.jsf.jqplot.js.support.IPropertyProvider;
import de.cuioss.jsf.jqplot.js.support.JavaScriptSupport;
import de.cuioss.jsf.jqplot.js.support.PropertyProvider;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Oliver Wolff
 *
 * @param <T> at leaste {@link Serializable}
 */
@ToString(exclude = { "userClass" })
@EqualsAndHashCode(exclude = { "userClass" })
@RequiredArgsConstructor
public class Highlighting<T extends Serializable> implements Serializable, IHighlightDecoration<T>, IPropertyProvider {

    /** serialVersionUID */
    private static final long serialVersionUID = 3957697520759215314L;

    private final PropertyProvider propProvider = new PropertyProvider();

    @NonNull
    private final T userClass;

    private JsBoolean highlightMouseOver;

    @Override
    public T setHighlightMouseOver(final Boolean value) {
        this.highlightMouseOver = JsBoolean.create(value);
        return userClass;
    }

    private JsBoolean highlightMouseDown;

    @Override
    public T setHighlightMouseDown(final Boolean value) {
        this.highlightMouseDown = JsBoolean.create(value);
        return userClass;
    }

    @Override
    public List<JavaScriptSupport> getProperties() {
        propProvider.addProperty("highlightMouseOver", highlightMouseOver);
        propProvider.addProperty("highlightMouseDown", highlightMouseDown);
        return propProvider.getProperties();
    }

}
