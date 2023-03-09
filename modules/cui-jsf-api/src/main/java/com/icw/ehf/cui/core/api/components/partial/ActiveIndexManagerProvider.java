package com.icw.ehf.cui.core.api.components.partial;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;

import java.util.Collections;
import java.util.List;

import javax.faces.component.StateHelper;

import com.icw.ehf.cui.core.api.components.support.ActiveIndexManager;
import com.icw.ehf.cui.core.api.components.util.CuiState;

import de.cuioss.tools.logging.CuiLogger;
import lombok.NonNull;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the activeIndexManager-attribute.
 * The
 * implementation relies on the correct use of attribute names, saying they must exactly match the
 * accessor methods.
 * </p>
 * <h2>activeIndexManager</h2>
 * <p>
 * Defines the activeIndexManager for a concrete component. The actual active indices are accessed
 * using {@link #resolveActiveIndexes()}
 * </p>
 *
 * @author Oliver Wolff
 */
public class ActiveIndexManagerProvider {

    private static final CuiLogger log = new CuiLogger(ActiveIndexManagerProvider.class);

    private static final String MANAGER_EVALUATION_ERROR_MSG =
        "Portal-114: Could not evaluate accordions active indexes from ActiveIndexManager. Returning list with entry {0}.";

    /** The key for the {@link StateHelper} */
    private static final String ACTIVE_INDEX_MANAGER = "activeIndexManager";

    private final CuiState state;

    /**
     * @param componentBridge must not be null
     */
    public ActiveIndexManagerProvider(@NonNull ComponentBridge componentBridge) {
        state = new CuiState(componentBridge.stateHelper());
    }

    /**
     * @return {@linkplain ActiveIndexManager}
     */
    public ActiveIndexManager getActiveIndexManager() {
        return state.get(ACTIVE_INDEX_MANAGER);
    }

    /**
     * @param activeIndexManager
     */
    public void setActiveIndexManager(ActiveIndexManager activeIndexManager) {
        state.put(ACTIVE_INDEX_MANAGER, activeIndexManager);
    }

    /**
     * @return List<Integer> of active indexes or a list with the element '0' if there is not
     *         {@linkplain ActiveIndexManager} set. This is a replacement for the previous return
     *         {@code null}
     */
    public List<Integer> resolveActiveIndexes() {
        try {
            var indexManager = getActiveIndexManager();
            if (null != indexManager) {
                var activeIndexes = indexManager.getActiveIndexes();
                if (null != activeIndexes) {
                    return immutableList(activeIndexes);
                }
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.warn(MANAGER_EVALUATION_ERROR_MSG, e);
        }
        return immutableList(0);
    }
}
