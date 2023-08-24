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
    public ValidatorMetadata(String name, String description, List<AttributeMetadata> attributes, String validatorId) {
        super(name, description, attributes);
        this.validatorId = validatorId;
    }

}
