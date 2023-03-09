package com.icw.ehf.cui.application.view.matcher;

import com.icw.ehf.cui.core.api.common.view.ViewDescriptor;

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
