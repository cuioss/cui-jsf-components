/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.api.components.myfaces;

import de.cuioss.jsf.api.components.base.BaseCuiCommandButton;
import de.cuioss.jsf.api.components.partial.TitleProvider;

/**
 * <h2>Adapting to MyFaces</h2>
 * In previous versions targeted at mojarra, the logic of modifying the final title was in the method {@link TitleProvider#getTitle()}
 * With myfaces on the other hand the default Renderer uses {@link jakarta.faces.component.UIComponent#getAttributes()}
 * for looking up the title, bypassing the corresponding get-method.
 * <em>This workaround is only necessary for cases, where the rendering is done by the concrete implementation (delegation).</em>
 * The Solution:
 * <ul>
 * <li>Use the standard-methods like {@link TitleProvider#setTitleKey(String)} as usual</li>
 * <li>The {@link jakarta.faces.render.Renderer} must call {@link TitleProvider#resolveAndStoreTitle()}</li>
 * <li>Finally, the {@link jakarta.faces.render.Renderer} must call {@link #writeTitleToParent()}</li>
 * </ul>
 *  See {@link BaseCuiCommandButton} for usage
 */
public interface MyFacesDelegateTitleAdapter {

    /**
     * Writes the current style-class to the parent
     */
    void writeTitleToParent();
}
