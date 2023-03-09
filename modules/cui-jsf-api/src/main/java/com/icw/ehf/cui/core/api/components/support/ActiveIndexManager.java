package com.icw.ehf.cui.core.api.components.support;

import java.io.Serializable;
import java.util.List;

/**
 * Programmatically manipulating the active indexes.
 *
 * @author Oliver Wolff (i001428)
 * @author Sven Haag
 */
public interface ActiveIndexManager extends Serializable {

    /**
     * @return the String representation of the active indexes.
     */
    String getActiveIndexesString();

    /**
     * @param activeIndexesString to be set. The implementation may support multiple indexes.
     */
    void setActiveIndexesString(String activeIndexesString);

    /**
     * Resets to the configured default index/es.
     */
    void resetToDefaultIndex();

    /**
     * Sets the active index
     *
     * @param indexes identifying the open tabs, starting from 0. Empty call results in all tabs
     *            being closed.
     */
    void setActiveIndex(Integer... indexes);

    /**
     * @return list of active indexes or <code>null</code>.
     */
    List<Integer> getActiveIndexes();

    /**
     * Alternative to Constructor.
     *
     * @param defaultIndexes to be used as default index and as initial activeIndexesString
     */
    void setDefaultIndex(List<Integer> defaultIndexes);

    /**
     * If this manager is used solely for single tab content this method can toggle
     * from no index to default index and vice versa.
     */
    void toggleSingleIndex();

    /**
     * Sets the active index
     *
     * @param indexes identifying the open tabs, starting from 0. Empty call results in all tabs
     *            being closed.
     */
    void setActiveIndex(List<Integer> indexes);

    /**
     * @return true if there is at least one active index, false otherwise.
     */
    boolean hasActiveIndex();
}
