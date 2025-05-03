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
package de.cuioss.jsf.dev.metadata.composite;

import static java.util.Objects.requireNonNull;

import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.context.FacesContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.beans.BeanInfo;

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
    public static BeanInfo loadMetadataInfo(final FacesContext facesContext, final String libraryName,
            final String compositeName) {
        requireNonNull(facesContext);
        requireNonNull(libraryName);
        requireNonNull(compositeName);
        BeanInfo metadata;

        var name = compositeName;
        if (!name.endsWith(XHTML_SUFFIX)) {
            name = name + XHTML_SUFFIX;
        }

        var resourceHandler = facesContext.getApplication().getResourceHandler();
        var compositeComponentResource = resourceHandler.createResource(name, libraryName);
        if (null == compositeComponentResource) {
            throw new IllegalArgumentException("No resource found for " + libraryName + "/" + compositeName);
        }
        metadata = facesContext.getApplication().getViewHandler()
                .getViewDeclarationLanguage(facesContext, FACELET_SUFFIX)
                .getComponentMetadata(facesContext, compositeComponentResource);
        log.trace("Lazy loaded metadata for Composite Component %s:%s", libraryName, compositeName);
        return metadata;
    }

}
