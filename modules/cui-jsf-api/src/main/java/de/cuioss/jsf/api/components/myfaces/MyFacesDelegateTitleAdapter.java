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
