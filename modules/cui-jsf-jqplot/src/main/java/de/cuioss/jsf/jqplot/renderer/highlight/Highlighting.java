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
package de.cuioss.jsf.jqplot.renderer.highlight;

import de.cuioss.jsf.jqplot.js.support.IPropertyProvider;
import de.cuioss.jsf.jqplot.js.support.JavaScriptSupport;
import de.cuioss.jsf.jqplot.js.support.PropertyProvider;
import de.cuioss.jsf.jqplot.js.types.JsBoolean;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Oliver Wolff
 *
 * @param <T> at leaste {@link Serializable}
 */
@ToString(exclude = {"userClass"})
@EqualsAndHashCode(exclude = {"userClass"})
@RequiredArgsConstructor
public class Highlighting<T extends Serializable> implements Serializable, IHighlightDecoration<T>, IPropertyProvider {

    /** serialVersionUID */
    @Serial
    private static final long serialVersionUID = 3957697520759215314L;

    private final PropertyProvider propProvider = new PropertyProvider();

    @NonNull
    private final T userClass;

    private JsBoolean highlightMouseOver;

    @Override
    public T setHighlightMouseOver(final Boolean value) {
        highlightMouseOver = JsBoolean.create(value);
        return userClass;
    }

    private JsBoolean highlightMouseDown;

    @Override
    public T setHighlightMouseDown(final Boolean value) {
        highlightMouseDown = JsBoolean.create(value);
        return userClass;
    }

    @Override
    public List<JavaScriptSupport> getProperties() {
        propProvider.addProperty("highlightMouseOver", highlightMouseOver);
        propProvider.addProperty("highlightMouseDown", highlightMouseDown);
        return propProvider.getProperties();
    }

}
