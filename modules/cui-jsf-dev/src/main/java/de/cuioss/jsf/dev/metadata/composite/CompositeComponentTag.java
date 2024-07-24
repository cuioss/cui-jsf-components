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

import de.cuioss.jsf.dev.metadata.composite.attributes.ComponentPropertiesWrapper;
import de.cuioss.jsf.dev.metadata.composite.util.SampleSourceFinder;
import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.view.ViewDeclarationLanguage;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for accessing Metadata of Composite components.
 * <p>
 * Inspired by
 * <a href="<a">href="http://weblogs.java.net/blog/edburns/archive/2009/09/02/jsf2-</a>composite">...</a>
 * -component-metadata
 * </p>
 *
 * @author e0571
 */
@FacesComponent("de.cuioss.cui.dev.compositeComponentTag")
public final class CompositeComponentTag extends UINamingContainer {

    /**
     * Default suffix for xhtml components.
     */
    private static final String DEFAULT_COMPONENT_SUFFIX = ".xhtml";

    private static final CuiLogger log = new CuiLogger(CompositeComponentTag.class);

    /**
     * Storage for the metadata object. Not to be serialized.
     */
    private BeanInfo metadata;

    /**
     * Storage for the metadata object. not to be serialized.
     */
    private ComponentPropertiesWrapper componentPropertiesWrapper = new ComponentPropertiesWrapper();

    /**
     * @return the string representation of the library
     */
    public String getLibrary() {
        return String.valueOf(getAttributes().get("compositeLibrary"));
    }

    /**
     * @return the name of the composite
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
     * @return the name of the current view
     */
    private String getViewName() {
        return getFacesContext().getViewRoot().getViewId();
    }

    /**
     * @return the actual ViewDeclarationLanguage
     */
    private ViewDeclarationLanguage getDeclarationLanguage() {
        return getFacesContext().getApplication().getViewHandler().getViewDeclarationLanguage(getFacesContext(),
            getViewName());
    }

    /**
     * @return the Metadata Object describing the Composite Component
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
                if (log.isTraceEnabled()) {
                    log.trace(
                        "Lazy loaded metadata for Composite Component " + getLibrary() + "/" + getCompositeName());
                }
            }
        }
        return metadata;
    }

    /**
     * Return the BeanDescriptor for the component as a whole
     *
     * @return the BeanDescriptor for the component as a whole
     */
    public BeanDescriptor getComponentDescriptor() {
        return getMetadataInfo().getBeanDescriptor();
    }

    /**
     * Return the PropertyDescriptors for the component as a whole
     *
     * @return the PropertyDescriptors for the component as a whole
     */
    public ComponentPropertiesWrapper getPropertyDescriptors() {
        if (!componentPropertiesWrapper.isConfigured()) {
            Map<String, String> additionalInfo = new HashMap<>();
            additionalInfo.put(ComponentPropertiesWrapper.COMOPOSITE_NAME_KEY,
                getCompositeName().replace(DEFAULT_COMPONENT_SUFFIX, ""));
            additionalInfo.put(ComponentPropertiesWrapper.COMOPOSITE_LIBRARY_KEY, getLibrary());
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
     * Listener for AddToViewEvent, currently only for demonstration purpose.
     *
     * @param event , not utilized.
     */
    public void processAddToViewEvent(final ComponentSystemEvent event) {
        getPropertyDescriptors();
    }

    /**
     * Return whether a facet with name="sample" is defined
     *
     * @return boolean indicating whether a facet with name="sample" is defined
     */
    public boolean isSampleFacetAvailable() {
        return null != getFacet("sample");
    }

    /**
     * Return whether a facet with name="sampleSource" is defined
     *
     * @return boolean indicating whether a facet with name="sampleSource" is
     * defined
     */
    public boolean isSampleSourceFacetAvailable() {
        return null != getFacet("sampleSource");
    }

    /**
     * Return the source for the sample facet
     *
     * @return the source for the sample facet
     */
    public String getSampleFacetSource() {
        final var context = (jakarta.servlet.ServletContext) getFacesContext().getExternalContext().getContext();
        final var sampleSourceFinder = new SampleSourceFinder(
            new File(context.getRealPath(getFacesContext().getViewRoot().getViewId())), getId());
        return sampleSourceFinder.getSampleSource();
    }
}
