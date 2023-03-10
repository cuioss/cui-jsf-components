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
public class ConverterMetadata extends Tag {

    private static final long serialVersionUID = 6616399666003213817L;

    @Getter
    private final String converterId;

    /**
     * @param name
     * @param description
     * @param attributes
     * @param converterId
     */
    public ConverterMetadata(String name, String description,
            List<AttributeMetadata> attributes, String converterId) {
        super(name, description, attributes);
        this.converterId = converterId;
    }

}
