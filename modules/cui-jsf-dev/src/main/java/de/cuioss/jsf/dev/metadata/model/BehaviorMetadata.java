package de.cuioss.jsf.dev.metadata.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Oliver Wolff
 *
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class BehaviorMetadata extends Tag {

    private static final long serialVersionUID = -1713844116234074377L;

    @Getter
    private final String behaviorId;

    @Getter
    private final String rendererType = "Not defined for Behavior";

    /**
     * @param name
     * @param description
     * @param attributes
     * @param targetbehaviorId
     */
    public BehaviorMetadata(final String name, final String description,
            final List<AttributeMetadata> attributes, final String targetbehaviorId) {
        super(name, description, attributes);
        this.behaviorId = targetbehaviorId;
    }

}
