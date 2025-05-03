/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.dev.metadata.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

/**
 * Describes a visual component.
 *
 * @author Oliver Wolff
 *
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class UIComponentMetadata extends Tag {

    @Serial
    private static final long serialVersionUID = 8405697485171286343L;

    @Getter
    private final String componentType;
    @Getter
    private final String rendererType;
    @Getter
    private final String handlerClass;

    /**
     * @param name          must not be null
     * @param attributes    must not be null, but may be empty
     * @param description   may be null
     * @param componentType may be null
     * @param rendererType  may be null
     * @param handlerClass  may be null
     */
    public UIComponentMetadata(final String name, final List<AttributeMetadata> attributes, final String description,
            final String componentType, final String rendererType, final String handlerClass) {
        super(name, description, attributes);
        this.componentType = componentType;
        this.rendererType = rendererType;
        this.handlerClass = handlerClass;
    }
}
