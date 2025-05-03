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
package de.cuioss.jsf.jqplot.options;

import de.cuioss.jsf.jqplot.options.color.Color;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Oliver Wolff
 *
 */
@Data
public class FillOptions implements Serializable {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = -4743127216847493275L;

    /** first index (0 based) of series in fill */
    private Integer series1 = null;

    /** second index (0 based) of series in fill */
    private Integer series2 = null;

    /** color of fill [default fillColor of series1] */
    private Color color = null;

    /** fill will be drawn below this series (0 based index) */
    private Integer baseSeries = 0;

    /** false to turn off fill [default true] */
    private boolean fill = true;
}
