package de.cuioss.jsf.dev.metadata.composite.util;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Simple Helper class for displaying key value pairs. The label is regarded as
 * a resource key for ResourceBundle 'cui_msg'.
 *
 * @author e0571
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class LabelValueDisplay implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = -539225580197700564L;

    /** the label for the display. */
    @Getter
    private final String label;

    /** the value for the display. */
    @Getter
    private final String value;
}
