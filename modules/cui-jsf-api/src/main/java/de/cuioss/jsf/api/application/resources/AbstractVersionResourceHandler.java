package de.cuioss.jsf.api.application.resources;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ResourceWrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Resource Handle does adapt the request ulr for any resource
 * covered by {@link #shouldHandleRequestedResource(String, String)} by adding current module version to resource path
 */
@RequiredArgsConstructor
public abstract class AbstractVersionResourceHandler extends ResourceHandlerWrapper {

    @Getter
    private final ResourceHandler wrapped;

    @Override
    public Resource createResource(final String resourceName, final String libraryName) {
        if (shouldHandleRequestedResource(resourceName, libraryName)) {
            return modifiedResource(getWrapped().createResource(resourceName, libraryName));
        }
        return getWrapped().createResource(resourceName, libraryName);
    }

    /**
     * provide modul specific decision if the requested resource should get handled by this resource handler
     */
    protected abstract boolean shouldHandleRequestedResource(String resourceName, String libraryName);

    /**
     * method should provide project specific version info for module
     */
    protected abstract String getNewResourceVersion();

    /**
     * Add version parameter at the end of the request path from JSF resource.
     */
    private Resource modifiedResource(final Resource wrappedResource) {

        if (wrappedResource == null) {
            return null;
        }

        return new ResourceWrapper(wrappedResource) {

            @Override
            public String getRequestPath() {
                if (super.getRequestPath().contains("&v=")) {
                    return super.getRequestPath();
                }
                return super.getRequestPath() + "&v=" + getNewResourceVersion();
            }

            @Override
            public Resource getWrapped() {
                return wrappedResource;
            }
        };
    }
}
