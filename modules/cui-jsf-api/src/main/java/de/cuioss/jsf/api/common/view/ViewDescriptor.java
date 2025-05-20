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
 * Represents metadata about a JSF view.
 * <p>
 * This interface provides access to view-related information like logical name, URL,
 * and method expressions for navigation. It's used primarily for navigation purposes
 * within the application.
 * </p>
 *
 * @author Oliver Wolff
 */
public interface ViewDescriptor extends Serializable {

    /**
     * Retrieves the physical view identifier that represents the actual view resource.
     * <p>
     * This identifier ends with the physical file extension, typically ".xhtml" for JSF views.
     * It represents the actual file path within the application and is used for internal
     * view resolution.
     * </p>
     * <p>
     * For example, "/pages/user/profile.xhtml"
     * </p>
     *
     * @return the String based identifier of the current view with its physical extension,
     *         may be null or empty if no view is defined
     */
    String getViewId();

    /**
     * Provides a simplified identifier derived from the view ID for use in CSS classes.
     * <p>
     * This identifier is typically a cleaned version of the view ID that follows CSS
     * naming conventions, making it suitable for use in CSS selectors.
     * </p>
     * <p>
     * For example, a view ID "/pages/user/profile.xhtml" might result in a short identifier
     * like "pages-user-profile"
     * </p>
     *
     * @return a CSS-compatible short identifier based on the view ID
     */
    String getShortIdentifier();

    /**
     * Retrieves the logical view identifier used for URLs and navigation.
     * <p>
     * This identifier ends with the logical suffix (e.g., ".jsf") that is used in URLs
     * and for navigation rules. It represents how the view is addressed in the browser
     * and in navigation rules.
     * </p>
     * <p>
     * For example, "/pages/user/profile.jsf"
     * </p>
     *
     * @return the String based identifier of the current view with its logical extension,
     *         may be null or empty if no view is defined
     */
    String getLogicalViewId();

    /**
     * Determines whether this descriptor represents an actual view.
     * <p>
     * This method checks whether a view ID is defined (not null and not empty),
     * which indicates that this descriptor refers to an actual view rather than
     * being an empty or uninitialized descriptor.
     * </p>
     *
     * @return {@code true} if a view is defined (view ID is not null or empty),
     *         {@code false} otherwise
     */
    boolean isViewDefined();

    /**
     * Retrieves all URL parameters associated with this view.
     * <p>
     * The returned list includes all parameters, including technical ones that
     * might be used by the framework or application infrastructure.
     * </p>
     *
     * @return an unmodifiable list of all {@link UrlParameter} objects associated with this view,
     *         never null but may be empty
     */
    List<UrlParameter> getUrlParameter();

    /**
     * Retrieves URL parameters associated with this view, filtered according to
     * specified criteria.
     * <p>
     * This method allows for selective retrieval of parameters based on application-specific
     * filtering logic.
     * </p>
     *
     * @param parameterFilter for filtering the parameters, must not be null
     * @return an unmodifiable list of {@link UrlParameter} objects that match the filter criteria,
     *         never null but may be empty
     * @throws NullPointerException if parameterFilter is null
     */
    List<UrlParameter> getUrlParameter(ParameterFilter parameterFilter);
}
