package com.icw.ehf.cui.dev.metadata.composite;

import static java.util.Objects.requireNonNull;

import java.beans.BeanInfo;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import de.cuioss.tools.logging.CuiLogger;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Provide some utility methods for dealing with Composite Components.
 *
 * @author Oliver Wolff
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompositeUtil {

    private static final CuiLogger log = new CuiLogger(CompositeUtil.class);
    private static final String XHTML_SUFFIX = ".xhtml";
    private static final String FACELET_SUFFIX = "some.xhtml";

    /**
     * Loads the metadata for the given composite component
     *
     * @param facesContext
     * @param libraryName
     * @param compositeName
     * @return {@link BeanInfo} representing the metadata for that composite
     */
    public static BeanInfo loadMetadataInfo(final FacesContext facesContext,
            final String libraryName, final String compositeName) {
        requireNonNull(facesContext);
        requireNonNull(libraryName);
        requireNonNull(compositeName);
        BeanInfo metadata;

        String name = compositeName;
        if (!name.endsWith(XHTML_SUFFIX)) {
            name = name + XHTML_SUFFIX;
        }

        ResourceHandler resourceHandler = facesContext
                .getApplication().getResourceHandler();
        Resource compositeComponentResource = resourceHandler
                .createResource(name, libraryName);
        if (null == compositeComponentResource) {
            throw new IllegalArgumentException("No resource found for "
                    + libraryName + "/" + compositeName);
        }
        metadata = facesContext.getApplication().getViewHandler()
                .getViewDeclarationLanguage(facesContext, FACELET_SUFFIX).getComponentMetadata(
                        facesContext, compositeComponentResource);
        if (log.isTraceEnabled()) {
            log.trace("Lazy loaded metadata for Composite Component "
                    + libraryName + "/" + compositeName);
        }
        return metadata;
    }

}
