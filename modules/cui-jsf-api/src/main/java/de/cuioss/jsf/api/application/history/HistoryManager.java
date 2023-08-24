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
package de.cuioss.jsf.api.application.history;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PhaseListener;
import javax.faces.event.PostAddToViewEvent;

import de.cuioss.jsf.api.application.navigation.ViewIdentifier;
import de.cuioss.jsf.api.common.view.ViewDescriptor;
import de.cuioss.tools.net.ParameterFilter;

/**
 * Implementations will keep track of the client side history
 *
 * @author Oliver Wolff
 */
public interface HistoryManager extends Serializable, Iterable<ViewIdentifier> {

    /**
     * @return the current view, that is not put history yet. If no navigation done
     *         before, the default "home" configured {@linkplain ViewIdentifier}
     *         will be retrieved
     */
    ViewIdentifier getCurrentView();

    /**
     * Adds the current view to the {@link HistoryManager}. This method is usually
     * called programmatically, not by an {@link ComponentSystemEvent}. Within the
     * CUI-portal this is done with a {@link PhaseListener}. <em>Caution</em>: The
     * caller must ensure that this method is not called with
     * {@link FacesContext#isPostback()}
     *
     * @param viewId must not be null.
     */
    void addCurrentUriToHistory(ViewDescriptor viewId);

    /**
     * This methods checks whether a page was reloaded using f5 or the corresponding
     * link.
     * <h3>Assumptions</h3>
     * <ul>
     * <li>This works for pages which participate in HistoryManager by calling
     * {@link #addCurrentUriToHistory(ViewDescriptor)} with a
     * {@link PostAddToViewEvent} on the page</li>
     * <li>This method <em>must</em> be called within the initViewAction or
     * {@link PostConstruct} because otherwise the algorithm will not work.</li>
     * <li>Calling from a different part of the lifecycle will result in a false
     * positive check.</li>
     * </ul>
     *
     * @return boolean indicating whether the current page is reloaded.
     */
    boolean isPageReload();

    /**
     * HistoryManagerListener react on each call
     *
     * @param pageReloadValue
     */
    void setPageReload(boolean pageReloadValue);

    /**
     * @return configured {@link ParameterFilter}
     */
    ParameterFilter getParameterFilter();

    /**
     * @return the previous page. If there is none found it returns the defaultpage.
     *         The found ViewIdentifer, if it is not the fallback, will be
     *         <em>removed</em> from the history
     */
    ViewIdentifier popPrevious();

    /**
     * @return the previous page. If there is none found it returns the default
     *         page. The found ViewIdentifer, if it is not the fallback, will be
     *         <em>kept</em> from the history
     */
    ViewIdentifier peekPrevious();

}
