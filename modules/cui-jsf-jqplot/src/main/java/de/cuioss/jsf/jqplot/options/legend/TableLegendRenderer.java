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
package de.cuioss.jsf.jqplot.options.legend;

import de.cuioss.jsf.jqplot.renderer.Renderer;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Oliver Wolff
 *
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class TableLegendRenderer extends Renderer {

    /** serial Version UID */
    private static final long serialVersionUID = -1028698106797046622L;

    /**
     *
     */
    public TableLegendRenderer() {
        super("$.jqplot.EnhancedLegendRenderer");
        this.addPlugin("jqplot.enhancedLegendRenderer.min.js");
    }

}
