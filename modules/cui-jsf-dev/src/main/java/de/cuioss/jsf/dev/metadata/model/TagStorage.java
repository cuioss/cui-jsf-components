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

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Oliver Wolff
 *
 * @param <T>
 */
@EqualsAndHashCode
@ToString
public class TagStorage<T extends Tag> implements Serializable, Iterable<T> {

    @Serial
    private static final long serialVersionUID = 5602475160645336869L;

    @Getter
    private final List<T> data = new ArrayList<>();

    private Boolean dataAvailable;

    /**
     * @return boolean indicating whether data is available
     */
    public boolean isDataAvailable() {
        if (null == dataAvailable) {
            dataAvailable = !data.isEmpty();
        }
        return dataAvailable;
    }

    /**
     *
     */
    public void sortCollected() {
        Collections.sort(data);
    }

    /**
     * @param tagElement to be added
     */
    public void add(final T tagElement) {
        data.add(tagElement);
    }

    /**
     * @return the size of the contained data
     */
    public int size() {
        return data.size();
    }

    /**
     * @param name the name of the component to be returned
     * @return the found component or empty {@link Optional}
     */
    public Optional<T> getByName(final String name) {
        var safeName = nullToEmpty(name);
        return getData().stream().filter(tag -> safeName.equalsIgnoreCase(tag.getName())).findFirst();
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

}
