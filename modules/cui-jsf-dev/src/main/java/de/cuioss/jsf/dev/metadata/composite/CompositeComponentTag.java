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
package de.cuioss.jsf.dev.metadata.composite;

import de.cuioss.jsf.dev.metadata.composite.attributes.ComponentPropertiesWrapper;
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
 * Specialized JSF component for accessing and displaying metadata of JSF composite components.
 * This class acts as a bridge between the JSF composite component system and development tools
 * that need to inspect component metadata for documentation purposes.
 * </p>
 * 
 * <p>
 * The component provides access to:
 * </p>
 * <ul>
 *   <li>Component metadata via the JSF BeanInfo API</li>
 *   <li>Component properties and attributes</li>
 *   <li>Component facets</li>
 *   <li>Sample usage code for the component</li>
 * </ul>
 * 
 * <p>
 * Usage in a Facelets page:
 * </p>
 * <pre>{@code
 * <cc:compositeComponentTag compositeName="myComponent" 
 *                           compositeLibrary="my-components" 
 *                           id="componentMetadata">
 *   <f:facet name="sample">
 *     <!-- Sample implementation of the component -->
 *     <my:myComponent ... />
 *   </f:facet>
 * </cc:compositeComponentTag>
 * }</pre>
 * 
 * <p>
 * <em>Thread Safety:</em> This class is not thread-safe as it maintains instance state.
 * Each instance is tied to a specific JSF component tree and request lifecycle.
 * </p>
 *
 * @author e0571
 * @since 1.0
 */
@FacesComponent("de.cuioss.cui.dev.compositeComponentTag")
public final class CompositeComponentTag extends UINamingContainer {

    /**
     * <p>
     * Default suffix for XHTML-based composite components.
     * </p>
     * <p>
     * This is appended to component names that don't already include the suffix
     * when resolving composite component resources.
     * </p>
     */
    private static final String DEFAULT_COMPONENT_SUFFIX = ".xhtml";

    /**
     * Logger for this class.
     */
    private static final CuiLogger LOGGER = new CuiLogger(CompositeComponentTag.class);

    /**
     * <p>
     * Storage for the component's metadata object.
     * </p>
     * 
     * <p>
     * This field is lazily initialized when {@link #getMetadataInfo()} is called
     * and cached for subsequent calls. It is not serialized with the component state.
     * </p>
     */
    private BeanInfo metadata;

    /**
     * <p>
     * Wrapper for component properties that provides additional functionality
     * beyond the standard BeanInfo API.
     * </p>
     * 
     * <p>
     * This field is lazily initialized when {@link #getPropertyDescriptors()} is called
     * and cached for subsequent calls. It is not serialized with the component state.
     * </p>
     */
    private ComponentPropertiesWrapper componentPropertiesWrapper = new ComponentPropertiesWrapper();

    /**
     * <p>
     * Returns the library name of the composite component being described.
     * </p>
     * 
     * <p>
     * This value is obtained from the {@code compositeLibrary} attribute passed to this component.
     * </p>
     *
     * @return the string representation of the library name, never {@code null}
     */
    public String getLibrary() {
        return String.valueOf(getAttributes().get("compositeLibrary"));
    }

    /**
     * <p>
     * Returns the name of the composite component being described.
     * </p>
     * 
     * <p>
     * This value is obtained from the {@code compositeName} attribute passed to this component.
     * If the name doesn't end with {@value #DEFAULT_COMPONENT_SUFFIX}, the suffix is appended.
     * </p>
     *
     * @return the name of the composite component with proper suffix
     * @throws IllegalArgumentException if the composite name attribute is null or empty
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
     * Returns the view ID of the current JSF view.
     * </p>
     * 
     * <p>
     * This is used when resolving the component metadata to provide
     * context for the resource resolution.
     * </p>
     *
     * @return the view ID of the current JSF view
     */
    private String getViewName() {
        return getFacesContext().getViewRoot().getViewId();
    }

    /**
     * <p>
     * Returns the ViewDeclarationLanguage for the current view.
     * </p>
     * 
     * <p>
     * The ViewDeclarationLanguage is used to retrieve component metadata
     * for composite components.
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
     * Returns the BeanInfo metadata for the composite component.
     * </p>
     * 
     * <p>
     * This method employs lazy initialization and caching. On first call, it:
     * </p>
     * <ol>
     *   <li>Locates the composite component resource using the resource handler</li>
     *   <li>Retrieves component metadata from the ViewDeclarationLanguage</li>
     *   <li>Caches the result for subsequent calls</li>
     * </ol>
     * 
     * <p>
     * The method is synchronized to prevent concurrent initialization issues.
     * </p>
     *
     * @return the BeanInfo metadata object for the composite component
     * @throws IllegalArgumentException if the specified composite component resource cannot be found
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
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(
                            "Lazy loaded metadata for Composite Component " + getLibrary() + "/" + getCompositeName());
                }
            }
        }
        return metadata;
    }

    /**
     * <p>
     * Returns the BeanDescriptor for the composite component.
     * </p>
     * 
     * <p>
     * The BeanDescriptor contains component-level metadata such as:
     * </p>
     * <ul>
     *   <li>Component type</li>
     *   <li>Component family</li>
     *   <li>Component description</li>
     *   <li>Component-wide attributes</li>
     * </ul>
     *
     * @return the BeanDescriptor for the composite component
     */
    public BeanDescriptor getComponentDescriptor() {
        return getMetadataInfo().getBeanDescriptor();
    }

    /**
     * <p>
     * Returns a wrapper for the property descriptors of the composite component.
     * </p>
     * 
     * <p>
     * The ComponentPropertiesWrapper enhances the standard PropertyDescriptor objects with:
     * </p>
     * <ul>
     *   <li>Categorization of properties (attributes, facets, etc.)</li>
     *   <li>Additional metadata relevant for documentation</li>
     *   <li>Convenient access methods for specific property types</li>
     * </ul>
     * 
     * <p>
     * This method uses lazy initialization to build the wrapper on first access.
     * The wrapper is then cached for subsequent calls.
     * </p>
     *
     * @return the ComponentPropertiesWrapper containing all property descriptors
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
     * Event listener method for the JSF AddToViewEvent.
     * </p>
     * 
     * <p>
     * This method is called when the component is added to the view. It pre-initializes
     * the property descriptors to ensure they are available during rendering.
     * </p>
     * 
     * <p>
     * Usage in component tag:
     * </p>
     * <pre>{@code
     * <f:event type="jakarta.faces.event.PreRenderComponentEvent"
     *          listener="#{cc.processAddToViewEvent}" />
     * }</pre>
     *
     * @param event the ComponentSystemEvent triggered when the component is added to the view
     */
    public void processAddToViewEvent(final ComponentSystemEvent event) {
        getPropertyDescriptors();
    }

    /**
     * <p>
     * Determines whether a sample facet is available for this component.
     * </p>
     * 
     * <p>
     * A sample facet contains example usage of the composite component being described.
     * It is defined as a facet with name "sample" in the component tag.
     * </p>
     * 
     * <p>
     * Example of defining a sample facet:
     * </p>
     * <pre>{@code
     * <cc:compositeComponentTag ...>
     *   <f:facet name="sample">
     *     <!-- Sample implementation of the component -->
     *   </f:facet>
     * </cc:compositeComponentTag>
     * }</pre>
     *
     * @return {@code true} if a facet with name "sample" is defined, {@code false} otherwise
     */
    public boolean isSampleFacetAvailable() {
        return null != getFacet("sample");
    }

    /**
     * <p>
     * Determines whether a sample source facet is available for this component.
     * </p>
     * 
     * <p>
     * A sample source facet contains the source code for example usage of the component.
     * It is defined as a facet with name "sampleSource" in the component tag.
     * </p>
     * 
     * <p>
     * Example of defining a sample source facet:
     * </p>
     * <pre>{@code
     * <cc:compositeComponentTag ...>
     *   <f:facet name="sampleSource">
     *     <!-- Sample source code for the component -->
     *   </f:facet>
     * </cc:compositeComponentTag>
     * }</pre>
     *
     * @return {@code true} if a facet with name "sampleSource" is defined, {@code false} otherwise
     */
    public boolean isSampleSourceFacetAvailable() {
        return null != getFacet("sampleSource");
    }

    /**
     * <p>
     * Returns the source code for the sample facet.
     * </p>
     * 
     * <p>
     * This method uses the SampleSourceFinder to extract the source code of the
     * sample facet from the view file containing this component. This allows
     * developers to see the exact markup used in the sample.
     * </p>
     * 
     * <p>
     * The method works by:
     * </p>
     * <ol>
     *   <li>Finding the real path of the current view file</li>
     *   <li>Using SampleSourceFinder to extract the sample source based on component ID</li>
     * </ol>
     *
     * @return the source code for the sample facet as a string
     * @see SampleSourceFinder
     */
    public String getSampleFacetSource() {
        final var context = (ServletContext) getFacesContext().getExternalContext().getContext();
        final var sampleSourceFinder = new SampleSourceFinder(
                new File(context.getRealPath(getFacesContext().getViewRoot().getViewId())), getId());
        return sampleSourceFinder.getSampleSource();
    }
}
