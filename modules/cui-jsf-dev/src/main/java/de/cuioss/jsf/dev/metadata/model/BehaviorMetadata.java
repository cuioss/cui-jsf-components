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

import java.io.Serial;
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

    @Serial
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
    public BehaviorMetadata(final String name, final String description, final List<AttributeMetadata> attributes,
            final String targetbehaviorId) {
        super(name, description, attributes);
        behaviorId = targetbehaviorId;
    }

}
