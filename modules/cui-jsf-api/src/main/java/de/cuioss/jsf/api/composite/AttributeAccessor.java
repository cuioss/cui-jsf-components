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
package de.cuioss.jsf.api.composite;

import java.io.Serializable;
import java.util.Map;

/**
 * Small helper class that unifies the access on component attributes. The name
 * of the attribute is enclosed in the implementation In case of a different
 * type for the attribute and type-parameter the methods will throw an
 * {@link IllegalStateException}
 *
 * @author Oliver Wolff
 * @param <T> defining the return type
 */
public interface AttributeAccessor<T> extends Serializable {

    /**
     * Typesafe access on a certain attribute. The concrete attribute name is
     * encapsulated in the implementation.
     *
     * @param attributeMap must not be null
     * @return the type attribute or null if the attribute is not available
     */
    T value(Map<String, Object> attributeMap);

    /**
     * @param attributeMap must not be null
     * @return boolean indicating whether the attribute is available (not null) at
     *         all
     */
    boolean available(Map<String, Object> attributeMap);
}
