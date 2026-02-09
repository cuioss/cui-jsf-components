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
 * Represents a view in a JSF application, combining a view ID with its corresponding URL parameters
 * and optional outcome information.
 * <p>
 * This class provides a unified way to identify views and handle navigation within JSF applications.
 * It encapsulates:
 * <ul>
 *   <li>The view ID (logical path to the view)</li>
 *   <li>The navigation outcome (if applicable)</li>
 *   <li>URL parameters associated with the view</li>
 * </ul>
 * 
 * <p>
 * ViewIdentifier can be used to:
 * <ul>
 *   <li>Create {@link NavigationCase} objects for programmatic navigation</li>
 *   <li>Perform redirects to views with parameters</li>
 *   <li>Capture the current view state for later navigation</li>
 * </ul>
 * 
 * <p>
 * This class is immutable and thread-safe.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class ViewIdentifier implements Serializable, Redirector {

    /**
     * Parameter name for the Deep State Window ID used in JSF applications with state saving.
     */
    private static final String DSWID = "dswid";

    /** 
     * The outcome identifying back navigation.
     * This constant is used to create navigation cases that represent navigation
     * back to a previous view.
     */
    public static final String BACK = "back";

    @Serial
    private static final long serialVersionUID = -8246314995093995027L;

    /**
     * The view ID, which is the logical path to the JSF view.
     */
    @Getter
    private final String viewId;

    /**
     * The navigation outcome associated with this view, if applicable.
     * May be null if the view was not accessed via a navigation outcome.
     */
    @Getter
    private final String outcome;

    /**
     * The URL parameters associated with this view.
     * These parameters will be included when redirecting to this view.
     */
    @Getter
    private final List<UrlParameter> urlParameters;

    /**
     * Creates a ViewIdentifier from a ViewDescriptor, optionally filtering the URL parameters.
     * <p>
     * This factory method extracts the logical view ID and URL parameters from the provided
     * ViewDescriptor, applying the specified parameter filter if provided.
     * </p>
     *
     * @param viewDescriptor  The view descriptor representing the view, must not be null
     *                        for a meaningful result
     * @param parameterFilter Defines which parameters to include/exclude. May be null or empty
     * @return A new ViewIdentifier representing the specified view, or null if the
     *         viewDescriptor or its logical view ID is null
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
     * Creates a NavigationCase representation of this ViewIdentifier.
     * <p>
     * This method allows the ViewIdentifier to be used with the JSF navigation system
     * by creating a NavigationCase that encapsulates the view ID and parameters.
     * A shorthand version for back navigation is available via {@link #toBackNavigationCase()}.
     * </p>
     * <p>
     * The view ID and parameters are derived from this ViewIdentifier instance,
     * while the other NavigationCase properties are provided as parameters.
     * </p>
     *
     * @param fromViewId        The originating view ID, returned by {@link NavigationCase#getFromViewId()}
     * @param fromAction        The originating action, returned by {@link NavigationCase#getFromAction()}
     * @param fromOutcome       The originating outcome, returned by {@link NavigationCase#getFromOutcome()}
     * @param condition         A string to be interpreted as a ValueExpression for the navigation condition
     * @param redirect          Whether the navigation should be a redirect, returned by {@link NavigationCase#isRedirect()}
     * @param includeViewParams Whether to include view parameters, returned by {@link NavigationCase#isIncludeViewParams()}
     * @return A NavigationCase representing this ViewIdentifier
     */
    public NavigationCase toNavigationCase(final String fromViewId, final String fromAction, final String fromOutcome,
            final String condition, final boolean redirect, final boolean includeViewParams) {
        return new NavigationCase(fromViewId, fromAction, outcome, condition, viewId,
                UrlParameter.createParameterMap(urlParameters), redirect, includeViewParams);
    }

    /**
     * Creates a NavigationCase for navigating back to this view.
     * <p>
     * This is a convenience method that creates a NavigationCase with the BACK outcome,
     * redirect=true, and includes all URL parameters from this ViewIdentifier.
     * </p>
     *
     * @return A NavigationCase representing a back navigation to this view
     */
    public NavigationCase toBackNavigationCase() {
        return toNavigationCase(null, null, BACK, null, true, false);
    }

    /**
     * Redirects to this view, preserving the current window ID if available.
     * <p>
     * This implementation ignores any parameters passed to this method and
     * uses the URL parameters stored in this ViewIdentifier instead.
     * It also preserves the Deep State Window ID (dswid) parameter if available
     * in the current request to ensure proper window management in JSF.
     * </p>
     * <p>
     * If this ViewIdentifier has a view ID, it will redirect directly to that view.
     * Otherwise, it will use the outcome to determine the navigation target.
     * </p>
     *
     * @param facesContext The current FacesContext
     * @param parameters   Ignored in this implementation, as parameters are taken from this ViewIdentifier
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
