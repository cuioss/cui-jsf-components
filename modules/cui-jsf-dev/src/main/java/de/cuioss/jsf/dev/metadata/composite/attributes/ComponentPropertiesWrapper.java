package de.cuioss.jsf.dev.metadata.composite.attributes;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.view.AttachedObjectTarget;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Simple Wrapper for attribute types for Composite Components.
 *
 * @author e0571
 */
@ToString
@EqualsAndHashCode
public class ComponentPropertiesWrapper implements Serializable {

    /**
     * Key for the Map {@link #addParentComponentDescriptor(FeatureDescriptor)} providing
     * additional info to covered by the {@link java.beans.BeanDescriptor} .
     */
    public static final String COMOPOSITE_NAME_KEY = "compositeNameKey";

    /**
     * Keys for the Map {@link #addParentComponentDescriptor(FeatureDescriptor)} providing
     * additional info to covered by the {@link java.beans.BeanDescriptor} .
     */
    public static final String COMOPOSITE_LIBRARY_KEY = "compositeLibKey";

    /**
     * Key for the Map {@link #addParentComponentDescriptor(FeatureDescriptor)} providing
     * additional info to covered by the {@link java.beans.BeanDescriptor} .
     */
    public static final String COMOPOSITE_URL_KEY = "compositeUrlKey";

    /** Serial version UID */
    private static final long serialVersionUID = 8036185753548211952L;

    /** The List of attached objects. */
    @Getter
    private final List<AbstractPropertyWrapper> attachedObjects =
        new ArrayList<>();

    /** The facets. */
    @Getter
    private final List<AbstractPropertyWrapper> facetList =
        new ArrayList<>();

    /** The standard attributes. */
    @Getter
    private final List<AbstractPropertyWrapper> attributes =
        new ArrayList<>();

    /** The descriptor for the root component. */
    @Getter
    private AbstractPropertyWrapper rootDescriptor;

    /** Flag indicating if the component is configured. needed for lazy initialization. */
    @Getter
    @Setter
    private boolean configured = false;

    /**
     * Initializes the properties for the parent composite component. It extract facets and Attached
     * objects
     *
     * @param descriptor to be wrapped
     */
    @SuppressWarnings("unchecked")
    public void addParentComponentDescriptor(final FeatureDescriptor descriptor) {
        final var extraValues = descriptor.attributeNames();
        String curName;
        while (extraValues.hasMoreElements()) {
            // Caution: the PropertyDescriptor is used in two ways: it either contains a single
            // attribute or a list of facets / attached Objects.
            curName = extraValues.nextElement();
            if (AttachedObjectTarget.ATTACHED_OBJECT_TARGETS_KEY.equals(curName)) {
                final var list = (List<AttachedObjectTarget>) descriptor
                        .getValue(curName);
                for (final AttachedObjectTarget attachedObjectTarget : list) {
                    attachedObjects.add(new AttachedObjectPropertyWrapper(descriptor,
                            attachedObjectTarget));
                }
            } else if (UIComponent.FACETS_KEY.equals(curName)) {
                final var loadedFacets =
                    (Map<String, PropertyDescriptor>) descriptor
                            .getValue(curName);
                for (final Entry<String, PropertyDescriptor> key : loadedFacets.entrySet()) {
                    facetList.add(new FacetPropertyWrapper(key.getKey(),
                            loadedFacets.get(key.getKey())));
                }
            }
        }
        // Add root descriptor
        this.rootDescriptor = new RootComponentPropertyWrapper(descriptor);
    }

    /**
     * Add a feature descriptor.
     *
     * @param descriptor the descriptor to add.
     */
    public void addChildComponentDescriptor(final FeatureDescriptor descriptor) {
        final var extraValues = descriptor.attributeNames();
        String curName;
        while (extraValues.hasMoreElements()) {
            // Caution: the PropertyDescriptor is used in two ways: it either contains a single
            // attribute or a list of facets / attached Objects.
            curName = extraValues.nextElement();
            if (AttachedObjectTarget.ATTACHED_OBJECT_TARGETS_KEY.equals(curName)) {
                throw new IllegalArgumentException("Attached Objects should not be found here");
            }
            if (UIComponent.FACETS_KEY.equals(curName)) {
                throw new IllegalArgumentException("Facets should not be found here");
            } else {
                attributes.add(new AttributePropertyWrapper(descriptor));
            }
            break;
        }
    }

    /**
     * Return true if this wrapper wraps an attribute
     *
     * @return Boolean indicating whether this wrapper wraps an attribute
     */
    public boolean isAttributeHolder() {
        return !attributes.isEmpty();
    }

    /**
     * Return true if this wrapper wraps facets
     *
     * @return Boolean indicating whether this wrapper wraps facets
     */
    public boolean isFacetHolder() {
        return !facetList.isEmpty();
    }

    /**
     * Return true if this wrapper wraps attached objects
     *
     * @return Boolean indicating whether this wrapper wraps attached objects
     */
    public boolean isAttachedObjectsHolder() {
        return !attachedObjects.isEmpty();
    }
}
