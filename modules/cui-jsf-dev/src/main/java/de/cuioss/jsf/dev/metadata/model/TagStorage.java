/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.dev.metadata.model;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * A generic container for managing collections of metadata objects that implement the
 * {@link Tag} interface. This class provides storage, retrieval, and iteration functionality
 * for tag metadata, as well as basic operations like sorting and lookups by name.
 * </p>
 *
 * <p>
 * TagStorage is used by {@link de.cuioss.jsf.dev.metadata.TagLib} to manage
 * different types of metadata, including components, converters, validators, and behaviors.
 * </p>
 *
 * <p>
 * Sample usage:
 * </p>
 * <pre>
 * // Create a storage for component metadata
 * TagStorage&lt;UIComponentMetadata&gt; componentStorage = new TagStorage&lt;&gt;();
 *
 * // Add components
 * componentStorage.add(new UIComponentMetadata("button", attributes, "Button component", "jakarta.faces.Button", null, null));
 *
 * // Sort by name
 * componentStorage.sortCollected();
 *
 * // Find a specific component
 * Optional&lt;UIComponentMetadata&gt; button = componentStorage.getByName("button");
 *
 * // Iterate over all components
 * for (UIComponentMetadata component : componentStorage) {
 *     System.out.println(component.getName());
 * }
 * </pre>
 *
 * <p>
 * This class is not thread-safe. Operations that modify the storage should be
 * synchronized externally if used across multiple threads.
 * </p>
 *
 * @author Oliver Wolff
 * @param <T> The type of Tag metadata objects to be stored, must implement {@link Tag}
 * @since 1.0
 */
@EqualsAndHashCode
@ToString
public class TagStorage<T extends Tag> implements Serializable, Iterable<T> {

    @Serial
    private static final long serialVersionUID = 5602475160645336869L;

    /**
     * <p>
     * The underlying list storing the tag metadata objects.
     * </p>
     * <p>
     * This list is initialized as an empty ArrayList and is populated through the {@link #add(Tag)}
     * method. It can be sorted using {@link #sortCollected()}.
     * </p>
     */
    @Getter
    private final List<T> data = new ArrayList<>();

    /**
     * <p>
     * Cached result of whether data is available in the storage.
     * </p>
     * <p>
     * This cache improves performance for frequent calls to {@link #isDataAvailable()}.
     * The value is lazily initialized on the first call to isDataAvailable().
     * </p>
     */
    private Boolean dataAvailable;

    /**
     * <p>
     * Checks whether there are any tag metadata objects in the storage.
     * </p>
     * <p>
     * This method caches its result for improved performance on repeated calls.
     * The cache is invalidated implicitly when new items are added.
     * </p>
     *
     * @return {@code true} if there is at least one metadata object in the storage,
     *         {@code false} otherwise
     */
    public boolean isDataAvailable() {
        if (null == dataAvailable) {
            dataAvailable = !data.isEmpty();
        }
        return dataAvailable;
    }

    /**
     * <p>
     * Sorts the stored tag metadata objects by their natural order.
     * </p>
     * <p>
     * The sorting uses the {@link Comparable} implementation of the {@link Tag} interface,
     * which typically sorts by name.
     * </p>
     * <p>
     * This method should be called after all tag metadata objects have been added to ensure
     * consistent ordering when iterating or accessing the data.
     * </p>
     */
    public void sortCollected() {
        Collections.sort(data);
    }

    /**
     * <p>
     * Adds a tag metadata object to the storage.
     * </p>
     * <p>
     * This method invalidates the dataAvailable cache if it was previously empty.
     * </p>
     *
     * @param tagElement The tag metadata object to add, must not be {@code null}
     */
    public void add(final T tagElement) {
        data.add(tagElement);
    }

    /**
     * <p>
     * Returns the number of tag metadata objects in the storage.
     * </p>
     *
     * @return The size of the data collection
     */
    public int size() {
        return data.size();
    }

    /**
     * <p>
     * Finds a tag metadata object by its name.
     * </p>
     * <p>
     * The search is case-insensitive. If the input name is {@code null}, it will be
     * treated as an empty string.
     * </p>
     * <p>
     * Note: If multiple tag metadata objects have the same name (case-insensitive),
     * only the first match will be returned.
     * </p>
     *
     * @param name The name of the tag metadata object to find
     * @return An {@link Optional} containing the found tag metadata object,
     *         or an empty Optional if no match was found
     */
    public Optional<T> getByName(final String name) {
        var safeName = nullToEmpty(name);
        return getData().stream().filter(tag -> safeName.equalsIgnoreCase(tag.getName())).findFirst();
    }

    /**
     * <p>
     * Returns an iterator over the tag metadata objects in this storage.
     * </p>
     * <p>
     * This method allows TagStorage to be used in for-each loops.
     * </p>
     * <p>
     * Note: The iterator does not support the {@link Iterator#remove()} operation.
     * </p>
     *
     * @return An iterator over the tag metadata objects in this storage
     */
    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

}
