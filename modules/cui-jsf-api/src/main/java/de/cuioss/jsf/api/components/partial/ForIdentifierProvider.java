package de.cuioss.jsf.api.components.partial;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import java.util.List;
import java.util.Optional;

import javax.faces.component.StateHelper;

import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.tools.string.Splitter;
import lombok.NonNull;

/**
 * Provides the capability to resolve multiple for-identifier
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the for-identifier string for a
 * component.
 * The implementation relies on the correct use of attribute names, saying they must exactly match
 * the accessor methods.
 * </p>
 * <h2>forIdentifier</h2>
 * <p>
 * The id of the child input component. Usually defaults to 'input'. Usually to be
 * kept the same. In special cases you can provides a space separated list for the
 * forIdentifier attribute resulting in n message elements being appended.
 * </p>
 *
 * @author Oliver Wolff
 *
 */
public class ForIdentifierProvider {

    /** The key for the {@link StateHelper} */
    public static final String KEY = "forIdentifier";

    /** "input" */
    public static final String DEFAULT_FOR_IDENTIFIER = "input";

    private final CuiState state;

    /**
     * Defines the default identifier for this partial, in case no explicit identifier is given by
     * the client.
     */
    @NonNull
    private final String defaultIdentifier;

    /**
     * @param bridge must not be null
     * @param defaultIdentifier to be used if no 'forIdentifier' is set
     */
    public ForIdentifierProvider(@NonNull ComponentBridge bridge, @NonNull String defaultIdentifier) {
        state = new CuiState(bridge.stateHelper());
        this.defaultIdentifier = defaultIdentifier;
    }

    /**
     * @return the forIdentifier
     */
    public String getForIdentifier() {
        return state.get(KEY, defaultIdentifier);
    }

    /**
     * @param forIdentifier
     *            the forIdentifier to set
     */
    public void setForIdentifier(final String forIdentifier) {
        state.put(KEY, forIdentifier);
    }

    /**
     * @return the first element of the stored forIds if available
     */
    public Optional<String> resolveFirstIdentifier() {
        return resolveIdentifierAsList().stream().findFirst();
    }

    /**
     * @return a list of String elements separated by ' ' or empty String is none is found.
     */
    public List<String> resolveIdentifierAsList() {
        return Splitter.on(' ').omitEmptyStrings().trimResults()
                .splitToList(nullToEmpty(getForIdentifier()));
    }

}
