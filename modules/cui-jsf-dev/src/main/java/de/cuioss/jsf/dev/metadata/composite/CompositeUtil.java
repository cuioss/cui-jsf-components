package de.cuioss.jsf.dev.metadata.composite;

import static java.util.Objects.requireNonNull;

import java.beans.BeanInfo;

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

        var name = compositeName;
        if (!name.endsWith(XHTML_SUFFIX)) {
            name = name + XHTML_SUFFIX;
        }

        var resourceHandler = facesContext
                .getApplication().getResourceHandler();
        var compositeComponentResource = resourceHandler
                .createResource(name, libraryName);
        if (null == compositeComponentResource) {
            throw new IllegalArgumentException("No resource found for "
                    + libraryName + "/" + compositeName);
        }
        metadata = facesContext.getApplication().getViewHandler()
                .getViewDeclarationLanguage(facesContext, FACELET_SUFFIX).getComponentMetadata(
                        facesContext, compositeComponentResource);
        log.trace("Lazy loaded metadata for Composite Component %s:%s", libraryName, compositeName);
        return metadata;
    }

}
