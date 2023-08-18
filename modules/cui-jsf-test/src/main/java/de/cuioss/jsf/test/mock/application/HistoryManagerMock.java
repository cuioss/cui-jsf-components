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
package de.cuioss.jsf.test.mock.application;

import java.util.ArrayList;
import java.util.Iterator;

import de.cuioss.jsf.api.application.history.HistoryManager;
import de.cuioss.jsf.api.application.history.impl.HistoryConfigurationImpl;
import de.cuioss.jsf.api.application.history.impl.HistoryManagerImpl;
import de.cuioss.jsf.api.application.navigation.ViewIdentifier;
import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.test.jsf.config.BeanConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.tools.net.ParameterFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Mock version of {@link HistoryManager} that default to home =
 * {@value #VIEW_HOME} It can be easily configured as bean by using
 * {@link JsfTestConfiguration}
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class HistoryManagerMock implements HistoryManager, BeanConfigurator {

    private static final long serialVersionUID = -3934691506290620858L;

    /** The home navigation view */
    public static final String VIEW_HOME = "/faces/portal/home.jsf";

    /**
     * {@link ViewIdentifier}, representing home navigation.
     */
    public static final ViewIdentifier IDENTIFIER_HOME = new ViewIdentifier(VIEW_HOME, "home", new ArrayList<>());

    private final HistoryManager delegate;

    @Getter
    @Setter
    private boolean pageReload = false;

    /**
     * Initializes the bean. See class documentation for expected result.
     */
    public HistoryManagerMock() {
        var config = new HistoryConfigurationImpl();
        config.setFallbackOutcome("home");
        config.setFallback(VIEW_HOME);
        delegate = new HistoryManagerImpl(config);
    }

    @Override
    public Iterator<ViewIdentifier> iterator() {
        return delegate.iterator();
    }

    @Override
    public ViewIdentifier getCurrentView() {
        return delegate.getCurrentView();
    }

    @Override
    public void addCurrentUriToHistory(final ViewDescriptor viewId) {
        delegate.addCurrentUriToHistory(viewId);
    }

    @Override
    public ParameterFilter getParameterFilter() {
        return delegate.getParameterFilter();
    }

    @Override
    public ViewIdentifier popPrevious() {
        return delegate.popPrevious();
    }

    @Override
    public ViewIdentifier peekPrevious() {
        return delegate.peekPrevious();
    }

    @Override
    public void configureBeans(final BeanConfigDecorator decorator) {
        decorator.register(this, HistoryManagerImpl.BEAN_NAME);
    }
}
