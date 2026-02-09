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
package de.cuioss.jsf.dev.metadata.composite.attributes;

import de.cuioss.jsf.dev.metadata.composite.util.SampleSourceFinder;
import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.view.ViewDeclarationLanguage;
import jakarta.servlet.ServletContext;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * A JSF component that displays XML code and metadata for JSF composite components.
 * </p>
 * 
 * <p>
 * This component serves as a documentation and demonstration tool for composite
 * components, providing:
 * </p>
 * <ul>
 *   <li>Display of component metadata (attributes, facets, attached objects)</li>
 *   <li>Sample usage code extraction and display</li>
 *   <li>Structured organization of component properties</li>
 * </ul>
 * 
 * <p>
 * The component is typically used in development and documentation pages to
 * provide self-documenting examples of composite components. It uses the JSF
 * component metadata API to extract information about the target component
 * and formats it for display.
 * </p>
 * 
 * <p>
 * Usage example in a Facelets page:
 * </p>
 * <pre>{@code
 * <dev:displayXmlCode id="componentDoc" 
 *                     compositeLibrary="my-components" 
 *                     compositeName="myButton">
 *   <f:facet name="sample">
 *     <!-- Sample usage of the component -->
 *     <my:myButton label="Click Me" action="#{bean.action}"/>
 *   </f:facet>
 * </dev:displayXmlCode>
 * }</pre>
 * 
 * <p>
 * The component supports two facets:
 * </p>
 * <ul>
 *   <li><strong>sample</strong> - Contains a sample usage of the documented component</li>
 *   <li><strong>sampleSource</strong> - Optional custom source code to display instead
 *       of auto-extracted source</li>
 * </ul>
 * 
 * <p>
 * Required attributes:
 * </p>
 * <ul>
 *   <li><strong>compositeLibrary</strong> - The library name of the component to document</li>
 *   <li><strong>compositeName</strong> - The name of the component to document</li>
 * </ul>
 * 
 * <p>
 * <em>Implementation Note:</em> The component uses lazy initialization for metadata retrieval
 * and processing to improve performance.
 * </p>
 * 
 * <p>
 * <em>Thread Safety:</em> This component follows the same thread-safety rules as
 * standard JSF components. The metadata is loaded in a thread-safe manner using
 * synchronization.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see ComponentPropertiesWrapper
 * @see SampleSourceFinder
 */
@FacesComponent("cuioss-reference-webui-portal.displayXmlCode")
public final class DisplayXmlCode extends UINamingContainer {

    private static final CuiLogger LOGGER = new CuiLogger(DisplayXmlCode.class);

    /**
     * Default suffix for XHTML component files.
     * Used when retrieving component resources and normalizing component names.
     */
    private static final String DEFAULT_COMPONENT_SUFFIX = ".xhtml";

    /**
     * <p>
     * Storage for the component metadata.
     * </p>
     * 
     * <p>
     * This field is lazily initialized when {@link #getMetadataInfo()} is called.
     * It contains the complete BeanInfo metadata for the composite component being
     * documented.
     * </p>
     * 
     * <p>
     * <em>Note:</em> This field is intentionally not serialized as it would be
     * re-initialized on deserialization.
     * </p>
     */
    private BeanInfo metadata;

    /**
     * <p>
     * Wrapper containing all processed component properties.
     * </p>
     * 
     * <p>
     * This wrapper organizes the component's properties into categories (attributes,
     * facets, attached objects) and provides structured access to them. It is lazily
     * initialized when {@link #getPropertyDescriptors()} is called.
     * </p>
     * 
     * <p>
     * <em>Note:</em> This field is intentionally not serialized as it would be
     * re-initialized on deserialization.
     * </p>
     */
    private ComponentPropertiesWrapper componentPropertiesWrapper = new ComponentPropertiesWrapper();

    /**
     * <p>
     * Retrieves the library name of the component being documented.
     * </p>
     * 
     * <p>
     * The library name is provided as the "compositeLibrary" attribute when using
     * this component. It identifies the JSF resource library containing the
     * composite component.
     * </p>
     *
     * @return the composite component's library name
     */
    public String getLibrary() {
        return String.valueOf(getAttributes().get("compositeLibrary"));
    }

    /**
     * <p>
     * Retrieves the name of the composite component being documented.
     * </p>
     * 
     * <p>
     * The component name is provided as the "compositeName" attribute when using
     * this component. This method ensures the name includes the proper ".xhtml"
     * suffix required for resource lookup.
     * </p>
     *
     * @return the normalized composite component name with .xhtml suffix
     * @throws IllegalArgumentException if the composite name is null or empty
     */
    private String getCompositeName() {
        var composite = String.valueOf(getAttributes().get("compositeName"));
        if (null == composite || composite.isEmpty()) {
            throw new IllegalArgumentException("Composite name must not be empty");
        }
        if (!composite.endsWith(DEFAULT_COMPONENT_SUFFIX)) {
            composite += DEFAULT_COMPONENT_SUFFIX;
        }
        return composite;
    }

    /**
     * <p>
     * Retrieves the view ID of the current page.
     * </p>
     * 
     * <p>
     * This is a utility method used to obtain the ViewDeclarationLanguage and
     * perform other view-related operations.
     * </p>
     *
     * @return the view ID of the current JSF view
     */
    private String getViewName() {
        return getFacesContext().getViewRoot().getViewId();
    }

    /**
     * <p>
     * Retrieves the ViewDeclarationLanguage for the current view.
     * </p>
     * 
     * <p>
     * The ViewDeclarationLanguage is needed to access the component metadata API.
     * This is a utility method used by {@link #getMetadataInfo()}.
     * </p>
     *
     * @return the ViewDeclarationLanguage for the current view
     */
    private ViewDeclarationLanguage getDeclarationLanguage() {
        return getFacesContext().getApplication().getViewHandler().getViewDeclarationLanguage(getFacesContext(),
                getViewName());
    }

    /**
     * <p>
     * Retrieves the metadata information for the composite component.
     * </p>
     * 
     * <p>
     * This method lazily loads the component metadata the first time it's called. 
     * It's synchronized to ensure thread safety during initialization. The metadata
     * is obtained through the JSF ViewDeclarationLanguage API.
     * </p>
     *
     * @return the BeanInfo metadata for the composite component
     * @throws IllegalArgumentException if the specified component resource cannot be found
     */
    private BeanInfo getMetadataInfo() {
        if (null == metadata) {
            synchronized (this) {
                var resourceHandler = getFacesContext().getApplication().getResourceHandler();
                var compositeComponentResource = resourceHandler.createResource(getCompositeName(), getLibrary());
                if (null == compositeComponentResource) {
                    throw new IllegalArgumentException(
                            "No resource found for " + getLibrary() + "/" + getCompositeName());
                }
                metadata = getDeclarationLanguage().getComponentMetadata(getFacesContext(), compositeComponentResource);
                LOGGER.trace("Lazy loaded metadata for Composite Component %s/%s", getLibrary(), getCompositeName());
            }
        }
        return metadata;
    }

    /**
     * <p>
     * Retrieves the BeanDescriptor for the composite component.
     * </p>
     * 
     * <p>
     * The BeanDescriptor contains top-level metadata about the component as a whole,
     * including its facets and attached objects.
     * </p>
     *
     * @return the BeanDescriptor for the composite component
     */
    public BeanDescriptor getComponentDescriptor() {
        return getMetadataInfo().getBeanDescriptor();
    }

    /**
     * <p>
     * Retrieves the wrapped property descriptors for the composite component.
     * </p>
     * 
     * <p>
     * This method lazily initializes the {@link #componentPropertiesWrapper} if needed.
     * It processes all metadata about the component and organizes it into a structured
     * format for display, including:
     * </p>
     * <ul>
     *   <li>Root component metadata</li>
     *   <li>Attributes</li>
     *   <li>Facets</li>
     *   <li>Attached objects</li>
     * </ul>
     * 
     * <p>
     * Additional information like component name and library are also added to the
     * wrapper.
     * </p>
     *
     * @return the ComponentPropertiesWrapper containing all processed property metadata
     */
    public ComponentPropertiesWrapper getPropertyDescriptors() {
        if (!componentPropertiesWrapper.isConfigured()) {
            Map<String, String> additionalInfo = new HashMap<>();
            additionalInfo.put(ComponentPropertiesWrapper.COMPOSITE_NAME_KEY,
                    getCompositeName().replace(DEFAULT_COMPONENT_SUFFIX, ""));
            additionalInfo.put(ComponentPropertiesWrapper.COMPOSITE_LIB_KEY, getLibrary());
            componentPropertiesWrapper = new ComponentPropertiesWrapper();
            componentPropertiesWrapper.addParentComponentDescriptor(getMetadataInfo().getBeanDescriptor());
            for (PropertyDescriptor descriptor : getMetadataInfo().getPropertyDescriptors()) {
                componentPropertiesWrapper.addChildComponentDescriptor(descriptor);
            }
            componentPropertiesWrapper.setConfigured(true);
        }
        return componentPropertiesWrapper;
    }

    /**
     * <p>
     * Listener for the AddToViewEvent JSF event.
     * </p>
     * 
     * <p>
     * This method ensures that the property descriptors are initialized when the
     * component is added to the view. It's used to trigger the lazy loading of
     * component metadata.
     * </p>
     *
     * @param event the component system event (not used in the current implementation)
     */
    public void processAddToViewEvent(final ComponentSystemEvent event) {
        getPropertyDescriptors();
    }

    /**
     * <p>
     * Determines whether a sample facet is available.
     * </p>
     * 
     * <p>
     * The sample facet contains an example usage of the component being documented.
     * This method checks if such a facet has been provided.
     * </p>
     *
     * @return {@code true} if a facet with name="sample" is defined, {@code false} otherwise
     */
    public boolean isSampleFacetAvailable() {
        return null != getFacet("sample");
    }

    /**
     * <p>
     * Determines whether a custom sample source facet is available.
     * </p>
     * 
     * <p>
     * The sampleSource facet can be used to provide custom source code for display
     * instead of the automatically extracted source. This method checks if such
     * a facet has been provided.
     * </p>
     *
     * @return {@code true} if a facet with name="sampleSource" is defined, {@code false} otherwise
     */
    public boolean isSampleSourceFacetAvailable() {
        return null != getFacet("sampleSource");
    }

    /**
     * <p>
     * Retrieves the source code of the sample facet.
     * </p>
     * 
     * <p>
     * This method uses the {@link SampleSourceFinder} to extract the source code
     * from the sample facet in the current view. This source code can then be
     * displayed as a demonstration of how to use the component.
     * </p>
     *
     * @return the extracted source code for the sample facet
     */
    public String getSampleFacetSource() {
        final var context = (ServletContext) getFacesContext().getExternalContext().getContext();
        final var sampleSourceFinder = new SampleSourceFinder(
                new File(context.getRealPath(getFacesContext().getViewRoot().getViewId())), getId());
        return sampleSourceFinder.getSampleSource();
    }
}
