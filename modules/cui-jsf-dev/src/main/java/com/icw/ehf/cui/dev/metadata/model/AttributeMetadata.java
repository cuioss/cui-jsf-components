package com.icw.ehf.cui.dev.metadata.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Represents an Attribute on a component.
 *
 * @author Oliver Wolff
 *
 */
@Data
public class AttributeMetadata implements Serializable {

    private static final long serialVersionUID = 7576492765093982094L;

    private final String name;
    private final String type;
    private final String description;
    private final Boolean required;

}
