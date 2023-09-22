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

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import de.cuioss.tools.net.ParameterFilter;
import de.cuioss.tools.net.UrlParameter;
import de.cuioss.tools.string.MoreStrings;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class ViewDescriptorImpl implements ViewDescriptor {

    private static final long serialVersionUID = -7389597518902482078L;

    private static final Pattern NON_LATIN = Pattern.compile("[^A-Za-z ]");
    private static final Pattern XHTML = Pattern.compile("_xhtml$");
    private static final Pattern JSF = Pattern.compile("_jsf$");

    @Getter
    private final String viewId;

    @Getter
    private final String shortIdentifier;

    @Getter
    private final String logicalViewId;

    @Getter
    private final List<UrlParameter> urlParameter;

    @Getter
    private final boolean viewDefined;

    @Override
    public List<UrlParameter> getUrlParameter(final ParameterFilter parameterFilter) {
        return UrlParameter.filterParameter(getUrlParameter(), parameterFilter);
    }

    /**
     * Copy Constructor that can additionally filter the given parameter
     *
     * @param other           to be copied from. must not be null
     * @param parameterFilter if not null it will will filter the contained
     *                        {@link UrlParameter}
     */
    public ViewDescriptorImpl(final ViewDescriptor other, final ParameterFilter parameterFilter) {
        this(other.getViewId(), other.getLogicalViewId(),
                null == parameterFilter ? other.getUrlParameter() : other.getUrlParameter(parameterFilter));
    }

    /**
     * Constructor
     *
     * @param viewId
     * @param logicalViewId
     * @param urlParameter
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
     * The builder for instances of {@link ViewDescriptorImpl}
     *
     * @author Oliver Wolff
     */
    public static class ViewDescriptorImplBuilder {

        private String viewId;
        private String logicalViewId;
        private List<UrlParameter> urlParameter = Collections.emptyList();

        /**
         * @param viewId
         * @return The builder for instances of {@link ViewDescriptorImpl}
         */
        public ViewDescriptorImplBuilder withViewId(final String viewId) {
            this.viewId = viewId;
            return this;
        }

        /**
         * @param logicalViewId
         * @return The builder for instances of {@link ViewDescriptorImpl}
         */
        public ViewDescriptorImplBuilder withLogicalViewId(final String logicalViewId) {
            this.logicalViewId = logicalViewId;
            return this;
        }

        /**
         * @param urlParameter
         * @return The builder for instances of {@link ViewDescriptorImpl}
         */
        public ViewDescriptorImplBuilder withUrlParameter(final List<UrlParameter> urlParameter) {
            this.urlParameter = urlParameter;
            return this;
        }

        /**
         * @return The created instances of {@link ViewDescriptorImpl}
         */
        public ViewDescriptorImpl build() {
            return new ViewDescriptorImpl(viewId, logicalViewId, urlParameter);
        }
    }

    /**
     * @return The builder for instances of {@link ViewDescriptorImpl}
     */
    public static ViewDescriptorImplBuilder builder() {
        return new ViewDescriptorImplBuilder();
    }

    private static List<UrlParameter> notNullUrlParamter(final List<UrlParameter> parameter) {
        if (null == parameter) {
            return Collections.emptyList();
        }
        return immutableList(parameter);
    }

}
