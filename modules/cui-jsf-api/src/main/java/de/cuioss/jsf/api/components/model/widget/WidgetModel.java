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
package de.cuioss.jsf.api.components.model.widget;

import de.cuioss.jsf.api.components.model.lazyloading.LazyLoadingModel;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.faces.event.PostAddToViewEvent;

/**
 * Data model for the widget component to be used at patient overview.
 *
 * @author Matthias Walliczek
 */
public interface WidgetModel extends LazyLoadingModel {

    /**
     * @return the title key of the widget.
     */
    IDisplayNameProvider<?> getTitle();

    /**
     * @return the name of the icon or null.
     */
    String getTitleIcon();

    /**
     * @return the title of the widget.
     * Takes precedence over {@linkplain #getTitle()}.
     */
    String getTitleValue();

    /**
     * @return the action outcome of the core widget function.
     */
    String getCoreAction();

    /**
     * @return the action outcome of the primary widget function.
     */
    String getPrimaryAction();

    /**
     * @return the action title of the primary widget function.
     */
    IDisplayNameProvider<?> getPrimaryActionTitle();

    /**
     * @return should the core action link be disabled?
     */
    boolean isDisableCoreAction();

    /**
     * @return should the primary action link be rendered?
     */
    boolean isRenderPrimaryAction();

    /**
     * @return should the primary action link be disabled?
     */
    boolean isDisablePrimaryAction();

    /**
     * @return the id used for sorting
     */
    String getId();

    /**
     * @return should the widget be rendered?
     */
    boolean isRendered();

    /**
     * Start the initialization during {@link PostAddToViewEvent}. Should return immediately.
     */
    void startInitialize();
}
