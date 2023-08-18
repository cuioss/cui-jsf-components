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
package de.cuioss.jsf.api.application.resources;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import de.cuioss.jsf.api.application.resources.util.LibraryInventory;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A central application-scoped bean containing the computed information about
 * what resource names are there and which version is to be delivered.
 *
 * @author Oliver Wolff
 */
@ManagedBean
@ApplicationScoped
@ToString
@EqualsAndHashCode
public class CuiResourceManager implements Serializable {

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "cuiResourceManager";

    private static final long serialVersionUID = -3598150198738923569L;

    private final ConcurrentHashMap<String, LibraryInventory> libraries = new ConcurrentHashMap<>();

    /**
     * @param librayname defining the desired {@link LibraryInventory}. Must not be
     *                   null.
     * @return the found {@link LibraryInventory}. If there is no corresponding
     *         library there it will create one.
     */
    public LibraryInventory getLibraryInventory(String librayname) {
        requireNotEmpty(librayname);
        return libraries.computeIfAbsent(librayname, v -> new LibraryInventory(librayname));
    }
}
