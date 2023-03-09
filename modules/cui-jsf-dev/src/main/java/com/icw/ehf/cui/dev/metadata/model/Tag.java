package com.icw.ehf.cui.dev.metadata.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Represents a top JSF Tag
 *
 * @author Oliver Wolff
 *
 */
@Data
public abstract class Tag implements Serializable, Comparable<Tag> {

    private static final long serialVersionUID = -6465433606569204179L;

    private final String name;
    private final String description;
    private final List<AttributeMetadata> attributes;

    @Override
    public int compareTo(final Tag o) {
        return this.getName().compareTo(o.getName());
    }

}
