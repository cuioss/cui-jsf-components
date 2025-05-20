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
package de.cuioss.jsf.api.components.base;

import jakarta.faces.event.PreRenderComponentEvent;

/**
 * Interface that streamlines the handling of {@link PreRenderComponentEvent}s within CUI components.
 * <p>
 * This interface is designed to be implemented by components that need to perform
 * specific actions just before they are rendered in the JSF lifecycle, typically in
 * the Render Response phase.
 * </p>
 * <p>
 * Components implementing this interface should expect to have their 
 * {@link #preRenderComponentEvent(PreRenderComponentEvent)} method called by their
 * parent component or a rendering utility just before rendering. This provides a
 * consistent hook for last-minute preparations, initializations, or calculations
 * needed for rendering.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * public class MyComponent extends BaseCuiComponent implements PreRenderComponentListener {
 *     
 *     &#64;Override
 *     public void preRenderComponentEvent(PreRenderComponentEvent event) {
 *         // Perform pre-render preparations
 *         prepareDataForRendering();
 *         initializeRenderingState();
 *     }
 *     
 *     // Component implementation...
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see PreRenderComponentEvent
 */
public interface PreRenderComponentListener {

    /**
     * Called just before a component is about to be rendered.
     * <p>
     * This method provides a hook for components to perform any necessary
     * preparations, initializations, or calculations immediately before
     * rendering occurs. It is typically called by the component's parent
     * or by a rendering utility.
     * </p>
     * <p>
     * Implementations should not throw exceptions from this method as that
     * could disrupt the rendering phase. If an error occurs, it should be
     * logged and handled gracefully.
     * </p>
     * 
     * @param event the pre-render component event containing information about
     *              the component that is about to be rendered
     */
    void preRenderComponentEvent(PreRenderComponentEvent event);
}
