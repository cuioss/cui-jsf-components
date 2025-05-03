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
package de.cuioss.jsf.api.application.navigation;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.tools.collect.CollectionLiterals;
import de.cuioss.tools.net.ParameterFilter;
import de.cuioss.tools.net.UrlParameter;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.application.NavigationCase;
import jakarta.faces.context.FacesContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Helper class, combining a view Id and the corresponding {@link UrlParameter}
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class ViewIdentifier implements Serializable, Redirector {

    private static final String DSWID = "dswid";

    /** The outcome identifying back navigation. */
    public static final String BACK = "back";

    @Serial
    private static final long serialVersionUID = -8246314995093995027L;

    @Getter
    private final String viewId;

    @Getter
    private final String outcome;

    @Getter
    private final List<UrlParameter> urlParameters;

    /**
     * @param viewDescriptor  represents the view
     * @param parameterFilter defines the parameter to be filtered. May be null or
     *                        empty.
     * @return The {@link ViewIdentifier} representing the current view incl
     *         {@link UrlParameter}. If a view can not be determined it returns null
     */
    public static ViewIdentifier getFromViewDesciptor(final ViewDescriptor viewDescriptor,
            final ParameterFilter parameterFilter) {
        if (null == viewDescriptor || null == viewDescriptor.getLogicalViewId()) {
            return null;
        }
        return new ViewIdentifier(viewDescriptor.getLogicalViewId(), null,
                viewDescriptor.getUrlParameter(parameterFilter));
    }

    /**
     * Create a {@link NavigationCase} representation of this {@link ViewIdentifier}
     * the number of parameters is in respect to the API contract. A shorthand
     * version is {@link #toBackNavigationCase()}. The elements String toViewId,
     * {@code Map<String, List<String>>} parameters are derived by this object, the
     * others are passed in.
     *
     * @param fromViewId        return from {@link NavigationCase#getFromViewId}
     * @param fromAction        return from {@link NavigationCase#getFromAction}
     * @param fromOutcome       return from {@link NavigationCase#getFromOutcome}
     * @param condition         A string to be interpreted as a
     *                          <code>ValueExpression</code> by a call to
     *                          {@link NavigationCase#getCondition}
     * @param redirect          return from {@link NavigationCase#isRedirect}
     * @param includeViewParams return {@link NavigationCase#isIncludeViewParams}
     * @return the corresponding navigation case
     */
    public NavigationCase toNavigationCase(final String fromViewId, final String fromAction, final String fromOutcome,
            final String condition, final boolean redirect, final boolean includeViewParams) {
        return new NavigationCase(fromViewId, fromAction, outcome, condition, viewId,
                UrlParameter.createParameterMap(urlParameters), redirect, includeViewParams);
    }

    /**
     * @return a {@link NavigationCase} representing a back navigation incorporating
     *         a redirect and the according {@link UrlParameter}
     */
    public NavigationCase toBackNavigationCase() {
        return toNavigationCase(null, null, BACK, null, true, false);
    }

    /**
     * The given {@link UrlParameter} are ignored but taken from this
     * {@link ViewIdentifier} itself
     */
    @Override
    public void redirect(final FacesContext facesContext, final UrlParameter... parameters) {
        var windowId = facesContext.getExternalContext().getRequestParameterMap().get(DSWID);

        var parameterList = CollectionLiterals.mutableList(parameters);
        if (!MoreStrings.isEmpty(windowId) && parameterList.stream().noneMatch(x -> DSWID.equals(x.getName()))) {
            parameterList.add(new UrlParameter(DSWID, windowId));
        }
        if (null != viewId) {
            NavigationUtils.sendRedirectParameterList(facesContext, viewId, parameterList);
            return;
        }
        NavigationUtils.sendRedirectOutcomeParameterList(facesContext, outcome, parameterList);
    }
}
