package de.cuioss.jsf.dev.metadata.model;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
     * @param tagElement
     */
    public void add(final T tagElement) {
        this.data.add(tagElement);
    }

    /**
     * @return the size of the contained data
     */
    public int size() {
        return this.data.size();
    }

    /**
     * @param name the name of the component to be returned
     * @return the found component or null
     */
    public T getByName(final String name) {
        var safeName = nullToEmpty(name);
        return getData().stream()
                .filter(tag -> safeName.equalsIgnoreCase(tag.getName())).findFirst().orElse(null);
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

}
