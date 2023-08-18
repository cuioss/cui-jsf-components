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
package de.cuioss.jsf.api.application.history.accessor;

import de.cuioss.jsf.api.application.history.HistoryManager;
import de.cuioss.jsf.api.application.history.impl.HistoryManagerImpl;
import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

/**
 * Helper class for accessing instances of {@link HistoryManager} within objects
 * that are not under control of the MangedBeanFacility, e.g. Converter,
 * validators, components.
 *
 * @author Oliver Wolff
 */
public class HistoryManagerAccessor extends ManagedBeanAccessor<HistoryManager> {

    private static final long serialVersionUID = 654595256504186362L;

    /**
     * Constructor
     */
    public HistoryManagerAccessor() {
        super(HistoryManagerImpl.BEAN_NAME, HistoryManager.class, true);
    }

}
