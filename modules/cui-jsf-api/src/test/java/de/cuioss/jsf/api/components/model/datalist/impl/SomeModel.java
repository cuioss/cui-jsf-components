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
package de.cuioss.jsf.api.components.model.datalist.impl;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("javadoc")
@Data
@NoArgsConstructor
public class SomeModel implements Serializable {

    private static final long serialVersionUID = 5670597921615648898L;
    private String name;
    private Integer age;

    public SomeModel(final SomeModel other) {
        name = other.getName();
        age = other.getAge();
    }

    public SomeModel(final String name, final Integer age) {
        this.name = name;
        this.age = age;
    }
}
