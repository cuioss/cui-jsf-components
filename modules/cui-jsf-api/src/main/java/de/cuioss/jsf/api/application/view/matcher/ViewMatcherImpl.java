package de.cuioss.jsf.api.application.view.matcher;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.util.List;

import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.tools.collect.CollectionBuilder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Default implementation of {@link ViewMatcher} that checks the given view
 * against a given list of partial view-paths by using
 * {@link String#startsWith(String)}. If a configured String of the given
 * matchList is empty it matches to false for that String.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class ViewMatcherImpl implements ViewMatcher {

    private static final long serialVersionUID = -1211279289779853076L;

    /**
     * The list to be matched against
     */
    private final List<String> matchList;

    /**
     * @param matchList
     */
    public ViewMatcherImpl(@NonNull List<String> matchList) {
        var builder = new CollectionBuilder<String>();
        for (String element : matchList) {
            if (!isEmpty(element) && !element.trim().isEmpty()) {
                builder.add(element);
            }
        }
        this.matchList = builder.toImmutableList();
    }

    @Override
    public boolean match(final ViewDescriptor viewDescriptor) {
        for (String matcher : matchList) {
            if (viewDescriptor.getLogicalViewId().startsWith(matcher)) {
                return true;
            }
        }
        return false;
    }
}
