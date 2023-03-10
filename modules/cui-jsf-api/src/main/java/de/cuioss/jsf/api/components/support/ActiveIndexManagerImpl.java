package de.cuioss.jsf.api.components.support;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static de.cuioss.tools.string.MoreStrings.isEmpty;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Default implementation for {@link ActiveIndexManager}.
 * Adds convenient methods for programmatically manipulating the active indexes.
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
@EqualsAndHashCode
@ToString
public class ActiveIndexManagerImpl implements ActiveIndexManager {

    private static final long serialVersionUID = 6174751256305289837L;
    private static final String INDEX_SEPARATOR = " ";

    /**
     * The indexes of the content-accordion that are to be active(open) on initial display/change of
     * grouping.
     */
    private String defaultIndex;

    /**
     * The active index or indexes.
     * Multiple indexes are separated with space.
     */
    @Getter
    @Setter
    private String activeIndexesString;

    /**
     * @param defaultIndexes to be used as default index and as initial activeIndexesString
     */
    public ActiveIndexManagerImpl(final List<Integer> defaultIndexes) {
        super();
        setActiveIndex(defaultIndexes);
        defaultIndex = activeIndexesString;
    }

    @Override
    public void resetToDefaultIndex() {
        activeIndexesString = defaultIndex;
    }

    @Override
    public void setActiveIndex(final Integer... indexes) {
        final var builder = new StringBuilder();
        for (Integer integer : indexes) {
            if (null != integer) {
                builder.append(integer);
                builder.append(' ');
            }
        }
        setActiveIndexesString(builder.toString().trim());
    }

    @Override
    public void setDefaultIndex(final List<Integer> defaultIndexes) {
        setActiveIndex(defaultIndexes);
        defaultIndex = activeIndexesString;
    }

    @Override
    public void toggleSingleIndex() {
        if (isEmpty(activeIndexesString)) {
            resetToDefaultIndex();
        } else {
            activeIndexesString = "";
        }
    }

    @Override
    public final void setActiveIndex(final List<Integer> indexes) {
        if (null == indexes) {
            setActiveIndexesString("");
            return;
        }

        final var builder = new StringBuilder();
        for (Integer integer : indexes) {
            builder.append(integer);
            builder.append(INDEX_SEPARATOR);
        }
        var trimmedactiveIndex = builder.toString().trim();
        setActiveIndexesString(trimmedactiveIndex);
    }

    @Override
    public List<Integer> getActiveIndexes() {
        if (!isEmpty(activeIndexesString)) {
            var indizes = activeIndexesString.split(INDEX_SEPARATOR);
            final List<Integer> intList = new ArrayList<>();
            for (String index : indizes) {
                intList.add(Integer.valueOf(index));
            }
            return intList;
        }
        return immutableList();
    }

    @Override
    public boolean hasActiveIndex() {
        return null != getActiveIndexes() && !getActiveIndexes().isEmpty();
    }
}
