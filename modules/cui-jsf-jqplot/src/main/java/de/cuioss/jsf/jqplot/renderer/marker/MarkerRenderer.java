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
package de.cuioss.jsf.jqplot.renderer.marker;

import de.cuioss.jsf.jqplot.renderer.Renderer;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Eugen Fischer
 * @see <a href=
 *      "http://www.jqplot.com/docs/files/jqplot-markerRenderer-js.html#$.jqplot.MarkerRenderer">MarkerRenderer</a>
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class MarkerRenderer extends Renderer {

    /** serial Version UID */
    private static final long serialVersionUID = 4001151503269081389L;

    /**
     *
     */
    public MarkerRenderer() {
        super("$.jqplot.MarkerRenderer");
    }

}
