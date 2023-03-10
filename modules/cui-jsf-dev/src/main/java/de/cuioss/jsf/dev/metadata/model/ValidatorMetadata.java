package de.cuioss.jsf.dev.metadata.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Describes a converter element
 *
 * @author Oliver Wolff
 *
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class ValidatorMetadata extends Tag {

    private static final long serialVersionUID = 6616399666003213817L;

    @Getter
    private final String validatorId;

    /**
     * @param name
     * @param description
     * @param attributes
     * @param validatorId
     */
    public ValidatorMetadata(String name, String description,
            List<AttributeMetadata> attributes, String validatorId) {
        super(name, description, attributes);
        this.validatorId = validatorId;
    }

}
