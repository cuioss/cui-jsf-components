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
package de.cuioss.jsf.jqplot.renderer;

import de.cuioss.jsf.jqplot.js.support.JsValue;
import lombok.Data;

/**
 * A class for creating a DOM element for the title. Value will be returned
 * plain!
 *
 * @author Eugen Fischer
 */
@Data
public class TitleRenderer implements JsValue {

    /** serialVersionUID */
    private static final long serialVersionUID = -5758045347477455164L;

    private final String value;

    @Override
    public String getValueAsString() {
        return value;
    }
}
