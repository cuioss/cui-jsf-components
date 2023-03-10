package de.cuioss.jsf.dev.metadata.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Describes a visual component.
 *
 * @author Oliver Wolff
 *
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class UIComponentMetadata extends Tag {

    private static final long serialVersionUID = 8405697485171286343L;

    @Getter
    private final String componentType;
    @Getter
    private final String rendererType;
    @Getter
    private final String handlerClass;

    /**
     * @param name
     *            must not be null
     * @param attributes
     *            must not be null, but may be empty
     * @param description
     *            may be null
     * @param componentType
     *            may be null
     * @param rendererType
     *            may be null
     * @param handlerClass
     *            may be null
     */
    public UIComponentMetadata(final String name, final List<AttributeMetadata> attributes,
            final String description, final String componentType, final String rendererType,
            final String handlerClass) {
        super(name, description, attributes);
        this.componentType = componentType;
        this.rendererType = rendererType;
        this.handlerClass = handlerClass;
    }
}
