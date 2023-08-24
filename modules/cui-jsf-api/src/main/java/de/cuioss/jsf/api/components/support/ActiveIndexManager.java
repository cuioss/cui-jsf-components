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
package de.cuioss.jsf.api.components.support;

import java.io.Serializable;
import java.util.List;

/**
 * Programmatically manipulating the active indexes.
 *
 * @author Oliver Wolff
 * @author Sven Haag
 */
public interface ActiveIndexManager extends Serializable {

    /**
     * @return the String representation of the active indexes.
     */
    String getActiveIndexesString();

    /**
     * @param activeIndexesString to be set. The implementation may support multiple
     *                            indexes.
     */
    void setActiveIndexesString(String activeIndexesString);

    /**
     * Resets to the configured default index/es.
     */
    void resetToDefaultIndex();

    /**
     * Sets the active index
     *
     * @param indexes identifying the open tabs, starting from 0. Empty call results
     *                in all tabs being closed.
     */
    void setActiveIndex(Integer... indexes);

    /**
     * @return list of active indexes or <code>null</code>.
     */
    List<Integer> getActiveIndexes();

    /**
     * Alternative to Constructor.
     *
     * @param defaultIndexes to be used as default index and as initial
     *                       activeIndexesString
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
     * @param indexes identifying the open tabs, starting from 0. Empty call results
     *                in all tabs being closed.
     */
    void setActiveIndex(List<Integer> indexes);

    /**
     * @return true if there is at least one active index, false otherwise.
     */
    boolean hasActiveIndex();
}
