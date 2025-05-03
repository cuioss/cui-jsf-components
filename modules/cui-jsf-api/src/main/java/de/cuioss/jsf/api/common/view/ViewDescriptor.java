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
package de.cuioss.jsf.api.common.view;

import de.cuioss.tools.net.ParameterFilter;
import de.cuioss.tools.net.UrlParameter;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a JSF-view at runtime
 *
 * @author Oliver Wolff
 *
 */
public interface ViewDescriptor extends Serializable {

    /**
     * @return the String based identifier of the current view-id. It ends on the
     *         real (physical) suffix, e.g. .xhtml
     */
    String getViewId();

    /**
     * @return the short identifier based on {@link #getViewId()} to be used as css
     *         class
     */
    String getShortIdentifier();

    /**
     * @return the String based identifier of the current view-id. It ends on the
     *         logical suffix, e.g. .jsf
     */
    String getLogicalViewId();

    /**
     * @return boolean indicating whether a view is present, {@link #getViewId()} is
     *         not null or empty
     */
    boolean isViewDefined();

    /**
     * @return the list of {@link UrlParameter} associated with this view. This list
     *         contains every parameter, including technical
     */
    List<UrlParameter> getUrlParameter();

    /**
     * @param parameterFilter for filtering the parameter. Must not be null
     * @return the list of {@link UrlParameter} associated with this view and
     *         filtered regarding the given {@link ParameterFilter}
     */
    List<UrlParameter> getUrlParameter(ParameterFilter parameterFilter);

}
