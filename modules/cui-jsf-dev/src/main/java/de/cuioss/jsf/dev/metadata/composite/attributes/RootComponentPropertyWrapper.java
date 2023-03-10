package de.cuioss.jsf.dev.metadata.composite.attributes;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.List;

import de.cuioss.jsf.dev.metadata.composite.util.LabelValueDisplay;

/**
 * Implementation for the core component.
 *
 * @author e0571
 */
public class RootComponentPropertyWrapper extends AbstractPropertyWrapper {

    private static final long serialVersionUID = -5598320044290880454L;

    /**
     * Constructor
     *
     * @param featureDescriptor to be wrapped
     */
    public RootComponentPropertyWrapper(final FeatureDescriptor featureDescriptor) {
        super(featureDescriptor, createDisplayValues(featureDescriptor));
    }

    /**
     * Creates the data to be displayed.
     *
     * @param descriptor
     * @return the list of display data
     */
    private static List<LabelValueDisplay> createDisplayValues(final FeatureDescriptor descriptor) {
        final List<LabelValueDisplay> result = new ArrayList<>();
        // Only add display name if it is not the same like name
        if (!descriptor.getName().equals(descriptor.getDisplayName())) {
            result.add(new LabelValueDisplay(LABEL_DISPLAY_NAME, descriptor.getDisplayName()));
        }
        result.add(new LabelValueDisplay(LABEL_SHORT_DESCRIPTION, descriptor
                .getShortDescription()));
        return result;
    }

    @Override
    public PropertyType getPropertyType() {
        return PropertyType.ROOT;
    }

}
