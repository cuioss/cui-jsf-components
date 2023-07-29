package de.cuioss.jsf.bootstrap.common.partial;

import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import lombok.RequiredArgsConstructor;

/**
 * This class simplifies the accessing /creation / interacting with css styles
 * related to bootstrap grid layout.
 * <h3>Design</h3> The general design assumes that the consuming component
 * defines four attributes in order to be used properly:
 * <ul>
 * <li>size: Integer: The size of the appropriate column. Must be between
 * 1-12</li>
 * <li>styleClass: String: Additional styleClasses to be added</li>
 * <li>offsetSize: Integer: Optional attribute resulting in an corresponding
 * bootstrap-offset class with the given classPrefix</li>
 * <li>renderAsColumn: Boolean: If column classes should be rendered. Otherwise
 * only styleClass is passed through</li>
 * </ul>
 * With these attribute set / defined properly this class always creates a
 * proper css class String.
 *
 * @author Matthias Walliczek
 */
@RequiredArgsConstructor
public class ColumnCssResolver {

    private static final int MAX_SIZE = 12;

    private static final int MIN_SIZE = 1;

    /**
     * Default column prefix
     */
    public static final String COL_PREFIX = "col-md-";

    /**
     * Default column offset prefix
     */
    public static final String COL_OFFSET_PREFIX = "col-md-offset-";

    private final Integer size;

    private final Integer offsetSize;

    private final boolean renderAsColumn;

    private final String styleClass;

    /**
     * @return a {@link StyleClassBuilder} containing the the column specific css
     *         classes, e.g. "col-md-4 col-md-offset-2"
     */
    public StyleClassBuilder resolveColumnCss() {
        final StyleClassBuilder builder = new StyleClassBuilderImpl();
        if (renderAsColumn) {
            requireNonNull(size, "size");
            final int sizeInt = size;
            if (sizeInt < MIN_SIZE || sizeInt > MAX_SIZE) {
                throw new IllegalArgumentException("size must be between 1 and 12");
            }

            builder.append(COL_PREFIX + size);

            if (null != offsetSize) {
                final int offsetSizeInt = offsetSize;
                if (offsetSizeInt < MIN_SIZE || offsetSizeInt > MAX_SIZE) {
                    throw new IllegalArgumentException("offsetSize must be between 1 and 12");
                }
                builder.append(COL_OFFSET_PREFIX + offsetSize);
            }
        }
        builder.append(styleClass);
        return builder;

    }

}
