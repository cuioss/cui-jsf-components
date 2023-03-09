package com.icw.ehf.cui.components.bootstrap.layout;

import javax.faces.component.FacesComponent;
import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;

import com.icw.ehf.cui.components.bootstrap.BootstrapFamily;
import com.icw.ehf.cui.core.api.components.partial.ComponentBridge;
import com.icw.ehf.cui.core.api.components.partial.LabelProvider;
import com.icw.ehf.cui.core.api.components.partial.TitleProvider;
import com.icw.ehf.cui.core.api.components.partial.TitleProviderImpl;

import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an Label for input elements. The label and title is resolved using the cui standard
 * label-resolving mechanism.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link LabelProvider}</li>
 * <li>{@link TitleProvider}</li>
 * </ul>
 *
 * @author Matthias Walliczek
 */
@FacesComponent(BootstrapFamily.OUTPUT_LABEL_COMPONENT)
@SuppressWarnings("squid:MaximumInheritanceDepth") // Artifact of Jsf-structure
public class OutputLabelComponent extends HtmlOutputLabel
        implements ComponentBridge, TitleProvider {

    @Delegate
    private final LabelProvider labelProvider;

    @Delegate
    private final TitleProvider titleProvider;

    /**
     *
     */
    public OutputLabelComponent() {
        super();
        labelProvider = new LabelProvider(this);
        titleProvider = new TitleProviderImpl(this);
    }

    @Override
    public StateHelper stateHelper() {
        return this.getStateHelper();
    }

    @Override
    public FacesContext facesContext() {
        return getFacesContext();
    }

    @Override
    public Object getValue() {
        return labelProvider.resolveLabel();
    }

    @Override
    public UIComponent facet(String facetName) {
        return getFacet(facetName);
    }
}
