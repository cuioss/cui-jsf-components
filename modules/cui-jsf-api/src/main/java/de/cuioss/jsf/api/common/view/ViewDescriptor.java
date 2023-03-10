package de.cuioss.jsf.api.common.view;

import java.io.Serializable;
import java.util.List;

import de.cuioss.tools.net.ParameterFilter;
import de.cuioss.tools.net.UrlParameter;

/**
 * Represents a JSF-view at runtime
 *
 * @author Oliver Wolff
 *
 */
public interface ViewDescriptor extends Serializable {

    /**
     * @return the String based identifier of the current view-id. It ends on
     *         the real (physical) suffix, e.g. .xhtml
     */
    String getViewId();

    /**
     * @return the short identifier based on {@link #getViewId()} to be used as css class
     */
    String getShortIdentifier();

    /**
     * @return the String based identifier of the current view-id. It ends on
     *         the logical suffix, e.g. .jsf
     */
    String getLogicalViewId();

    /**
     * @return boolean indicating whether a view is present,
     *         {@link #getViewId()} is not null or empty
     */
    boolean isViewDefined();

    /**
     * @return the list of {@link UrlParameter} associated with this view. This
     *         list contains every parameter, including technical
     */
    List<UrlParameter> getUrlParameter();

    /**
     * @param parameterFilter
     *            for filtering the parameter. Must not be null
     * @return the list of {@link UrlParameter} associated with this view and
     *         filtered regarding the given {@link ParameterFilter}
     */
    List<UrlParameter> getUrlParameter(ParameterFilter parameterFilter);

}
