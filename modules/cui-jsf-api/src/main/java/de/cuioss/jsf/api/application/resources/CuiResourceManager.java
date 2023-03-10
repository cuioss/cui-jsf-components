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
     * @param librayname
     *            defining the desired {@link LibraryInventory}. Must not be
     *            null.
     * @return the found {@link LibraryInventory}. If there is no corresponding
     *         library there it will create one.
     */
    public LibraryInventory getLibraryInventory(String librayname) {
        requireNotEmpty(librayname);
        if (!libraries.containsKey(librayname)) {
            libraries.put(librayname, new LibraryInventory(librayname));
        }
        return libraries.get(librayname);
    }
}
