/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.jqplot.model;

import de.cuioss.jsf.jqplot.axes.Axes;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Options to set up a mock plot with a data loading indicator if no data is
 * specified. Example:
 *
 * <pre>
 * show: false,
 *           indicator: 'Loading Data...',
 *           axes: {
 *               xaxis: {
 *                   min: 0,
 *                   max: 10,
 *                   tickInterval: 2,
 *                   show: true
 *               },
 *               yaxis: {
 *                   min: 0,
 *                   max: 12,
 *                   tickInterval: 3,
 *                   show: true
 *               }
 *           }
 * </pre>
 *
 * @author Eugen Fischer
 */
@Data
public class NoDataIndicator implements Serializable {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = -1690798530650850562L;

    private boolean show = false;

    private String indicator;

    private Axes axes;
}
