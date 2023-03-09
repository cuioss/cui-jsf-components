package com.icw.ehf.cui.dev.metadata.composite;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewDeclarationLanguage;

import com.icw.ehf.cui.dev.metadata.composite.attributes.ComponentPropertiesWrapper;
import com.icw.ehf.cui.dev.metadata.composite.util.SampleSourceFinder;
import de.cuioss.tools.logging.CuiLogger;

/**
 * Helper class for accessing Metadata of Composite components.
 * <p>
 * Inspired by
 * http://weblogs.java.net/blog/edburns/archive/2009/09/02/jsf2-composite
 * -component-metadata
 * </p>
 *
 * @author e0571
 */
@FacesComponent("com.icw.cui.dev.compositeComponentTag")
public final class CompositeComponentTag extends UINamingContainer {

    /** Default suffix for xhtml components. */
    private static final String DEFAULT_COMPONENT_SUFIX = ".xhtml";

    private static final CuiLogger log = new CuiLogger(CompositeComponentTag.class);

    /** Storage for the metadata object. not to be serialized. */
    private transient BeanInfo metadata;

    /** Storage for the metadata object. not to be serialized. */
    private transient ComponentPropertiesWrapper componentPropertiesWrapper =
            new ComponentPropertiesWrapper();

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
        String composite = String.valueOf(getAttributes().get("compositeName"));
        if (null == composite || composite.isEmpty()) {
            throw new IllegalArgumentException(
                    "Composite name must not be empty");
        }
        if (!composite.endsWith(DEFAULT_COMPONENT_SUFIX)) {
            composite += DEFAULT_COMPONENT_SUFIX;
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
        return getFacesContext().getApplication().getViewHandler()
                .getViewDeclarationLanguage(getFacesContext(), getViewName());
    }

    /**
     * @return the Metadata Object describing the Composite Component
     */
    private BeanInfo getMetadataInfo() {
        if (null == metadata) {
            synchronized (this) {
                ResourceHandler resourceHandler = getFacesContext()
                        .getApplication().getResourceHandler();
                Resource compositeComponentResource = resourceHandler
                        .createResource(getCompositeName(), getLibrary());
                if (null == compositeComponentResource) {
                    throw new IllegalArgumentException("No resource found for "
                            + getLibrary() + "/" + getCompositeName());
                }
                metadata = getDeclarationLanguage().getComponentMetadata(
                        getFacesContext(), compositeComponentResource);
                if (log.isTraceEnabled()) {
                    log.trace("Lazy loaded metadata for Composite Component "
                            + getLibrary() + "/" + getCompositeName());
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
                    getCompositeName().replace(DEFAULT_COMPONENT_SUFIX, ""));
            additionalInfo.put(
                    ComponentPropertiesWrapper.COMOPOSITE_LIBRARY_KEY,
                    getLibrary());
            componentPropertiesWrapper = new ComponentPropertiesWrapper();
            componentPropertiesWrapper.addParentComponentDescriptor(getMetadataInfo().getBeanDescriptor());
            for (PropertyDescriptor descriptor : getMetadataInfo()
                    .getPropertyDescriptors()) {
                componentPropertiesWrapper
                .addChildComponentDescriptor(descriptor);
            }
            componentPropertiesWrapper.setConfigured(true);
        }
        return componentPropertiesWrapper;
    }

    /**
     * Listener for AddToViewEvent, currently only for demonstration purpose.
     *
     * @param event
     *            , not utilized.
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
     *         defined
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
        final javax.servlet.ServletContext context =
                (javax.servlet.ServletContext) getFacesContext()
                .getExternalContext().getContext();
        final SampleSourceFinder sampleSourceFinder = new SampleSourceFinder(
                new File(context.getRealPath(getFacesContext().getViewRoot()
                        .getViewId())),
                getId());
        return sampleSourceFinder.getSampleSource();
    }
}
