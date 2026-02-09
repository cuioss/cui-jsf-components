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
package de.cuioss.jsf.api.common.view;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import de.cuioss.tools.net.ParameterFilter;
import de.cuioss.tools.net.UrlParameter;
import de.cuioss.tools.string.MoreStrings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Standard implementation of the {@link ViewDescriptor} interface.
 * <p>
 * This class provides a concrete implementation for representing JSF views at runtime,
 * including both view identification and associated URL parameters. It handles the
 * transformation of view IDs to CSS-compatible identifiers and provides immutable
 * access to view properties.
 * 
 * <p>
 * The implementation includes:
 * <ul>
 *   <li>Generation of short identifiers for CSS class usage by sanitizing the view ID</li>
 *   <li>Immutable storage of URL parameters</li>
 *   <li>Parameter filtering capabilities</li>
 *   <li>A fluid builder API for creating instances</li>
 * </ul>
 * 
 * <p>
 * This class is immutable and thread-safe after construction.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ViewDescriptor
 */
@EqualsAndHashCode
@ToString
public class ViewDescriptorImpl implements ViewDescriptor {

    @Serial
    private static final long serialVersionUID = -7389597518902482078L;

    /** Pattern for identifying non-Latin characters to be replaced in short identifiers. */
    private static final Pattern NON_LATIN = Pattern.compile("[^A-Za-z ]");

    /** Pattern for removing "_xhtml" suffix in short identifiers. */
    private static final Pattern XHTML = Pattern.compile("_xhtml$");

    /** Pattern for removing "_jsf" suffix in short identifiers. */
    private static final Pattern JSF = Pattern.compile("_jsf$");

    /**
     * The physical view ID that represents the actual view resource.
     * Ends with the physical file extension (e.g., ".xhtml").
     */
    @Getter
    private final String viewId;

    /**
     * A CSS-compatible short identifier derived from the view ID.
     * Created by sanitizing the view ID according to CSS naming conventions.
     */
    @Getter
    private final String shortIdentifier;

    /**
     * The logical view ID used for URLs and navigation.
     * Ends with the logical suffix (e.g., ".jsf").
     */
    @Getter
    private final String logicalViewId;

    /**
     * An unmodifiable list of URL parameters associated with this view.
     */
    @Getter
    private final List<UrlParameter> urlParameter;

    /**
     * Flag indicating whether this descriptor represents an actual view.
     * True if the view ID is not null or empty.
     */
    @Getter
    private final boolean viewDefined;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UrlParameter> getUrlParameter(final ParameterFilter parameterFilter) {
        return UrlParameter.filterParameter(getUrlParameter(), parameterFilter);
    }

    /**
     * Copy constructor that creates a new ViewDescriptorImpl based on an existing one,
     * with optional parameter filtering.
     * <p>
     * This constructor is useful when you need to create a view descriptor with the same
     * view IDs but different URL parameters.
     * </p>
     *
     * @param other the source view descriptor to copy from, must not be null
     * @param parameterFilter if not null, it will filter the URL parameters according
     *                       to the specified criteria
     * @throws NullPointerException if other is null
     */
    public ViewDescriptorImpl(final ViewDescriptor other, final ParameterFilter parameterFilter) {
        this(other.getViewId(), other.getLogicalViewId(),
                null == parameterFilter ? other.getUrlParameter() : other.getUrlParameter(parameterFilter));
    }

    /**
     * Standard constructor for creating a ViewDescriptorImpl with all required properties.
     * <p>
     * This constructor initializes a view descriptor with the specified view IDs and URL parameters.
     * It also handles the generation of the short identifier from the view ID.
     * </p>
     *
     * @param viewId the physical view ID, may be null
     * @param logicalViewId the logical view ID, may be null
     * @param urlParameter the list of URL parameters, may be null (will be treated as empty)
     */
    public ViewDescriptorImpl(final String viewId, final String logicalViewId, final List<UrlParameter> urlParameter) {
        this.viewId = viewId;
        if (null != viewId) {
            var sanitizedId = NON_LATIN.matcher(viewId).replaceAll("_");
            sanitizedId = XHTML.matcher(sanitizedId).replaceAll("");
            shortIdentifier = JSF.matcher(sanitizedId).replaceAll("");
        } else {
            shortIdentifier = null;
        }
        this.logicalViewId = logicalViewId;
        this.urlParameter = notNullUrlParamter(urlParameter);
        viewDefined = !MoreStrings.isEmpty(viewId);
    }

    /**
     * Builder class for creating instances of {@link ViewDescriptorImpl}.
     * <p>
     * This builder provides a fluid API for constructing view descriptors with
     * the desired properties.
     * </p>
     *
     * @author Oliver Wolff
     * @since 1.0
     */
    public static class ViewDescriptorImplBuilder {

        private String viewId;
        private String logicalViewId;
        private List<UrlParameter> urlParameter = Collections.emptyList();

        /**
         * Sets the physical view ID for the descriptor being built.
         *
         * @param viewId the physical view ID, may be null
         * @return this builder for method chaining
         */
        public ViewDescriptorImplBuilder withViewId(final String viewId) {
            this.viewId = viewId;
            return this;
        }

        /**
         * Sets the logical view ID for the descriptor being built.
         *
         * @param logicalViewId the logical view ID, may be null
         * @return this builder for method chaining
         */
        public ViewDescriptorImplBuilder withLogicalViewId(final String logicalViewId) {
            this.logicalViewId = logicalViewId;
            return this;
        }

        /**
         * Sets the URL parameters for the descriptor being built.
         *
         * @param urlParameter the list of URL parameters, may be null
         * @return this builder for method chaining
         */
        public ViewDescriptorImplBuilder withUrlParameter(final List<UrlParameter> urlParameter) {
            this.urlParameter = urlParameter;
            return this;
        }

        /**
         * Creates and returns a new {@link ViewDescriptorImpl} with the properties
         * configured in this builder.
         *
         * @return a new {@link ViewDescriptorImpl} instance
         */
        public ViewDescriptorImpl build() {
            return new ViewDescriptorImpl(viewId, logicalViewId, urlParameter);
        }
    }

    /**
     * Factory method for creating a new builder for {@link ViewDescriptorImpl} instances.
     *
     * @return a new {@link ViewDescriptorImplBuilder}
     */
    public static ViewDescriptorImplBuilder builder() {
        return new ViewDescriptorImplBuilder();
    }

    /**
     * Utility method to ensure that URL parameter lists are never null.
     * <p>
     * If the input parameter list is null, an empty immutable list is returned.
     * Otherwise, the input list is wrapped in an immutable list to protect against
     * external modification.
     * </p>
     *
     * @param parameter the list of URL parameters, may be null
     * @return an immutable list of URL parameters, never null
     */
    private static List<UrlParameter> notNullUrlParamter(final List<UrlParameter> parameter) {
        if (null == parameter) {
            return Collections.emptyList();
        }
        return immutableList(parameter);
    }
}
