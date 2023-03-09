package com.icw.ehf.cui.application.view.matcher;

import javax.faces.context.FacesContext;

import com.icw.ehf.cui.core.api.application.navigation.NavigationUtils;
import com.icw.ehf.cui.core.api.common.view.ViewDescriptor;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * {@link ViewMatcher} that looks up an outcome for a concrete view.
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class OutcomeBasedViewMatcher implements ViewMatcher {

    private static final long serialVersionUID = -8359315030575513107L;

    @NonNull
    private final String outcome;

    private ViewDescriptor descriptor;

    @Override
    public boolean match(ViewDescriptor viewDescriptor) {
        return getDescriptor().getLogicalViewId().equals(viewDescriptor.getLogicalViewId());
    }

    private ViewDescriptor getDescriptor() {
        if (null == descriptor) {
            descriptor = NavigationUtils.lookUpToViewDescriptorBy(FacesContext.getCurrentInstance(), outcome);
        }
        return descriptor;
    }

}
