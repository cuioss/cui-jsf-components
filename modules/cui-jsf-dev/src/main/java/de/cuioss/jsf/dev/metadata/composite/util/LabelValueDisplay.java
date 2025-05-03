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
package de.cuioss.jsf.dev.metadata.composite.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

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
    @Serial
    private static final long serialVersionUID = -539225580197700564L;

    /** the label for the display. */
    @Getter
    private final String label;

    /** the value for the display. */
    @Getter
    private final String value;
}
