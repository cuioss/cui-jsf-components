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
package de.cuioss.jsf.api.components.util.modifier.support;

import javax.faces.component.UIComponentBase;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class StyleClassProvider extends UIComponentBase implements ComponentStyleClassProvider {

    @Getter
    @Setter
    private String styleClass;

    @Override
    public StyleClassBuilder getStyleClassBuilder() {
        return null;
    }

    @Override
    public String getFamily() {
        return "StyleClassProvider";
    }

}
