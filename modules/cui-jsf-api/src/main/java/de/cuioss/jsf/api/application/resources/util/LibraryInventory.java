package de.cuioss.jsf.api.application.resources.util;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Helper class keeping track of the resources provided by concrete / theme / library
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class LibraryInventory implements Serializable {

    private static final long serialVersionUID = -3201509823036422970L;

    /** The library/theme name this Inventory object is related to. */
    @Getter
    private final String libraryName;

    /**
     * The currently used resouceNames / mapping. The resourceName is a key. The value can either be
     * the resourcename or the min-variant if configured this way.
     */
    private final ConcurrentHashMap<String, String> resourceNameMapping = new ConcurrentHashMap<>();

    /**
     * Computes the library for a given resource.
     *
     * @param resourceName identifying the resource, must not be null
     * @return The given resourceName or the min-variant if mapped within resourceNameMapping.
     */
    public String getResourceMapping(String resourceName) {
        return resourceNameMapping.get(resourceName);
    }

    /**
     * @param resourceName to be checked
     * @return boolean indicating whether a mapping for this elements is available
     */
    public boolean containsMapping(String resourceName) {
        return resourceNameMapping.containsKey(resourceName);
    }

    /**
     * Adds a concrete mapping.
     *
     * @param resourceName must not be null
     * @param mappedName must not be null
     */
    public void addMapping(String resourceName, String mappedName) {
        resourceNameMapping.putIfAbsent(resourceName, mappedName);
    }
}
