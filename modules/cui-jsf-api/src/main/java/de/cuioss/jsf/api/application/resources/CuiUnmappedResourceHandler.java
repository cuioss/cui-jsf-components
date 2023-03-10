package de.cuioss.jsf.api.application.resources;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import org.omnifaces.resourcehandler.UnmappedResourceHandler;
import org.omnifaces.util.FacesLocal;

import de.cuioss.tools.string.MoreStrings;
import de.cuioss.tools.string.Splitter;

/**
 * This resource handler wrap omnifaces UnmappedResourceHandler for restriction of handling only
 * harmless resources.
 * Blacklist could be adapted by add context-param to web.xml (or fragment), this is the common JSF
 * configuration parameter
 * <p>
 * <code>
 *         &lt;context-param&gt;<br/>
 *         &lt;param-name&gt;javax.faces.RESOURCE_EXCLUDES&lt;/param-name&gt;<br/>
 *         &lt;param-value&gt;.class .jsp .jspx .properties .xhtml .groovy&lt;/param-value&gt;<br/>
 *         &lt;/context-param&gt;
 *      </code>
 * </p>
 *
 * TODO : could be replaced by original after https://github.com/omnifaces/omnifaces/issues/481 is
 * solved. See CUI-915
 *
 * @author i000576
 */
public class CuiUnmappedResourceHandler extends UnmappedResourceHandler {

    private static final List<Pattern> EXCLUDE_RESOURCES = initExclusionPatterns();

    /**
     * Creates a new instance of this unmapped resource handler which wraps the given resource
     * handler.
     *
     * @param wrapped The resource handler to be wrapped.
     */
    public CuiUnmappedResourceHandler(final ResourceHandler wrapped) {
        super(wrapped);
    }

    @Override
    public Resource decorateResource(final Resource resource) {

        if (null != resource && shouldBeHandledHere(resource.getRequestPath())) {
            return super.decorateResource(resource);
        }

        return resource;
    }

    @Override
    public boolean isResourceRequest(final FacesContext context) {
        final var requestURI = FacesLocal.getRequestURI(context);
        final var requestContextPath = FacesLocal.getRequestContextPath(context);
        final var isResourceRequest = requestURI.startsWith(requestContextPath + RESOURCE_IDENTIFIER);
        if (isResourceRequest) {
            context.getAttributes().put("com.sun.faces.RESOURCE_REQUEST", true);
        }
        return isResourceRequest && shouldBeHandledHere(requestURI)
                // wrapped should decide not super !
                || getWrapped().isResourceRequest(context);
    }

    @Override
    public void handleResourceRequest(final FacesContext context) throws IOException {
        final var requestURI = FacesLocal.getRequestURI(context);
        if (shouldBeHandledHere(requestURI)) {
            super.handleResourceRequest(context);
        } else {
            getWrapped().handleResourceRequest(context);
        }
    }

    private static boolean shouldBeHandledHere(final String requestURI) {
        return !isExcluded(requestURI);
    }

    private static boolean isExcluded(final String resourceId) {
        for (final Pattern pattern : EXCLUDE_RESOURCES) {
            if (pattern.matcher(resourceId).matches()) {
                return true;
            }
        }
        return false;
    }

    private static List<Pattern> initExclusionPatterns() {
        return immutableList(
                configuredExclusions().stream().map(pattern -> Pattern.compile(".*\\" + pattern)).collect(toList()));
    }

    /**
     * Lookup web.xml context-param
     * {@link javax.faces.application.ResourceHandler#RESOURCE_EXCLUDES_PARAM_NAME}.<br/>
     * If exists use as space separated configuration list,<br/>
     * otherwise fallback to
     * {@link javax.faces.application.ResourceHandler#RESOURCE_EXCLUDES_DEFAULT_VALUE}
     */
    private static List<String> configuredExclusions() {
        final var exclusions = Optional
                .ofNullable(getContextParameter())
                .orElse(ResourceHandler.RESOURCE_EXCLUDES_DEFAULT_VALUE);
        return Splitter.on(' ').omitEmptyStrings().trimResults().splitToList(exclusions);
    }

    private static String getContextParameter() {
        return MoreStrings.emptyToNull(FacesContext.getCurrentInstance().getExternalContext()
                .getInitParameter(ResourceHandler.RESOURCE_EXCLUDES_PARAM_NAME));
    }
}
