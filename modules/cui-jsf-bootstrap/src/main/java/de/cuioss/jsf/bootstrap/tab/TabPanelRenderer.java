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
package de.cuioss.jsf.bootstrap.tab;

import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;

/**
 * Renderer for the {@link TabPanelComponent} that produces Bootstrap-compliant
 * markup for tab panels. Configured not to render children during the encode phase.
 * 
 * <h2>Generated Markup</h2>
 * <pre>
 * &lt;div id="tabContainer:tab1" class="tab-pane fade active show" 
 *      role="tabpanel" aria-labelledby="tabContainer:tab1-tab"&gt;
 *   [Tab content components]
 * &lt;/div&gt;
 * </pre>
 * 
 * <h2>CSS Classes</h2>
 * <ul>
 *   <li><code>tab-pane</code> - Main tab panel class</li>
 *   <li><code>active</code> - Applied to the active tab panel</li>
 *   <li><code>fade</code> - For transition effects (optional)</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see TabPanelComponent
 * @see BaseDecoratorRenderer
 */
public class TabPanelRenderer extends BaseDecoratorRenderer<TabPanelComponent> {

    /**
     * Constructor that configures the renderer not to render children.
     * 
     * @param renderChildren boolean indicating whether children should be rendered
     *                     (always false for this implementation)
     */
    public TabPanelRenderer(final boolean renderChildren) {
        super(false);
    }

}
