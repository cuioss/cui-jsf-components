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
 * <p>
 * Utility class that provides helper methods for accessing and manipulating JSF
 * composite component metadata.
 * </p>
 * 
 * <p>
 * JSF composite components store their metadata in {@link BeanInfo} objects,
 * which can be accessed through the view declaration language API. This class
 * simplifies the process of retrieving this metadata.
 * </p>
 * 
 * <p>
 * Key features:
 * </p>
 * <ul>
 *   <li>Loading composite component metadata via resource resolution</li>
 *   <li>Handling of file name extensions for composite components</li>
 *   <li>Proper resource validation and error handling</li>
 * </ul>
 * 
 * <p>
 * Usage example:
 * </p>
 * <pre>{@code
 * // Load metadata for a composite component
 * BeanInfo metadata = CompositeUtil.loadMetadataInfo(
 *     facesContext,
 *     "my-library",
 *     "my-component");
 * 
 * // Access component properties
 * BeanDescriptor descriptor = metadata.getBeanDescriptor();
 * PropertyDescriptor[] properties = metadata.getPropertyDescriptors();
 * }</pre>
 * 
 * <p>
 * <em>Thread Safety:</em> This class is immutable and thread-safe.
 * All methods are static and do not maintain any state.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompositeUtil {

    private static final CuiLogger log = new CuiLogger(CompositeUtil.class);

    /**
     * <p>
     * The standard file extension for XHTML-based composite components.
     * </p>
     * <p>
     * This suffix is automatically appended to component names that don't
     * already include it when resolving composite component resources.
     * </p>
     */
    private static final String XHTML_SUFFIX = ".xhtml";

    /**
     * <p>
     * A placeholder facelet suffix used when retrieving the view declaration language.
     * </p>
     * <p>
     * This suffix is needed by the JSF API to determine which view declaration
     * language to use when retrieving component metadata. For JSF 2.x, any XHTML
     * suffix will work as the actual view is resolved separately.
     * </p>
     */
    private static final String FACELET_SUFFIX = "some.xhtml";

    /**
     * <p>
     * Loads the metadata for a composite component specified by library name and component name.
     * </p>
     * 
     * <p>
     * This method performs the following steps:
     * </p>
     * <ol>
     *   <li>Validates that all required parameters are non-null</li>
     *   <li>Ensures the component name has the correct suffix</li>
     *   <li>Resolves the component resource using the JSF resource handler</li>
     *   <li>Retrieves the component metadata using the view declaration language</li>
     * </ol>
     * 
     * <p>
     * The returned {@link BeanInfo} object contains:
     * </p>
     * <ul>
     *   <li>A {@link java.beans.BeanDescriptor} with component-wide metadata</li>
     *   <li>An array of {@link java.beans.PropertyDescriptor} objects for each component attribute</li>
     *   <li>Additional metadata specific to the composite component implementation</li>
     * </ul>
     *
     * @param facesContext the current JSF context, required for resource resolution
     * @param libraryName the name of the component library containing the composite component
     * @param compositeName the name of the composite component (with or without the .xhtml suffix)
     * 
     * @return a {@link BeanInfo} instance containing the metadata for the specified composite component
     * 
     * @throws NullPointerException if any of the parameters is {@code null}
     * @throws IllegalArgumentException if no resource can be found for the specified component
     */
    public static BeanInfo loadMetadataInfo(final FacesContext facesContext, final String libraryName,
            final String compositeName) {
        requireNonNull(facesContext, "FacesContext must not be null");
        requireNonNull(libraryName, "Library name must not be null");
        requireNonNull(compositeName, "Composite name must not be null");
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
