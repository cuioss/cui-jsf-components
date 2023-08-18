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
package de.cuioss.jsf.api.application.history.impl;

import javax.annotation.PostConstruct;

import de.cuioss.jsf.api.application.history.HistoryManager;
import de.cuioss.jsf.api.application.history.accessor.HistoryConfigurationAccessor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * Bean keeping track of the view history. For configuration see package-info
 * The implementation utilizes a stack to store the history. The actual work is
 * done by {@link HistoryManagerImpl}
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(of = "delegate")
@ToString(of = "delegate")
public class HistoryManagerBean implements HistoryManager {

    private static final long serialVersionUID = 2205593126500409010L;

    private final HistoryConfigurationAccessor configurationAccessor = new HistoryConfigurationAccessor();

    @Delegate(types = HistoryManager.class)
    private HistoryManager delegate;

    /**
     * Initializes the bean. See class documentation for expected result.
     */
    @PostConstruct
    public void initBean() {
        final var configuration = configurationAccessor.getValue();
        delegate = new HistoryManagerImpl(configuration);
    }

}
