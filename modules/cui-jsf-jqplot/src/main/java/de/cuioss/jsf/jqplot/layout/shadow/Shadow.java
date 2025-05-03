/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.layout.shadow;

import de.cuioss.jsf.jqplot.js.support.IPropertyProvider;
import de.cuioss.jsf.jqplot.js.support.JavaScriptSupport;
import de.cuioss.jsf.jqplot.js.support.PropertyProvider;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import de.cuioss.jsf.jqplot.js.types.JsDouble;
import de.cuioss.jsf.jqplot.js.types.JsInteger;
import de.cuioss.jsf.jqplot.js.types.JsString;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Decorating any target class with shadow properties
 *
 * @author Eugen Fischer
 * @param <T> user class, will be used for fluent api / builder
 */
@ToString(exclude = {"userClass"})
@EqualsAndHashCode(exclude = {"userClass"})
public class Shadow<T extends Serializable> implements Serializable, IShadowDecoration<T>, IPropertyProvider {

    /** serialVersionUID */
    @Serial
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
        shadowed = JsBoolean.create(value);
        return userClass;
    }

    private JsDouble shadowAngle;

    @Override
    public T setShadowAngle(final Double value) {
        shadowAngle = new JsDouble(value);
        return userClass;
    }

    private JsInteger shadowOffset;

    @Override
    public T setShadowOffset(final Integer value) {
        shadowOffset = new JsInteger(value);
        return userClass;
    }

    private JsInteger shadowDepth;

    @Override
    public T setShadowDepth(final Integer value) {
        shadowDepth = new JsInteger(value);
        return userClass;
    }

    private JsString shadowAlpha;

    @Override
    public T setShadowAlpha(final String value) {
        shadowAlpha = new JsString(value);
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
