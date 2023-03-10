package de.cuioss.jsf.api.application.view.matcher;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Will always return false / true depending on given attribute match
 *
 * @author Oliver Wolff
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class EmptyViewMatcher implements ViewMatcher {

    private static final long serialVersionUID = -3249573462984449100L;

    private final boolean match;

    @Override
    public boolean match(final ViewDescriptor descriptor) {
        return match;
    }

}
