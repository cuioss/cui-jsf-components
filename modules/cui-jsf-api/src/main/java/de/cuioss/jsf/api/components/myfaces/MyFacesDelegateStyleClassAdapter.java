package de.cuioss.jsf.api.components.myfaces;

import de.cuioss.jsf.api.components.base.BaseCuiCommandButton;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProviderImpl;

/**
 * <h2>Adapting to MyFaces</h2>
 * In previous versions targeted at mojarra, the logic of modifying the final style-class was in the method
 * {@link de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider#getStyleClass()}.
 * With myfaces on the other hand the default Renderer uses {@link jakarta.faces.component.UIComponent#getAttributes()}
 * for looking up the styleClass, bypassing the corresponding get-method.
 * <em>This workaround is only necessary for cases, where the rendering is done by the concrete implementation (delegation).</em>
 * The Solution:
 * <ul>
 * <li>The configured class, {@link de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider#setStyleClass(String)} will be stored under the keys
 * {@value ComponentStyleClassProviderImpl#KEY} and {@value ComponentStyleClassProviderImpl#LOCAL_STYLE_CLASS_KEY}</li>
 * <li>Component-specific additions are to be provided via {@link de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider#computeAndStoreFinalStyleClass(StyleClassBuilder)} (StyleClassBuilder)}.
 *      This must be done prior Rendering, usually by the concrete {@link jakarta.faces.render.Renderer}</li>
 * <li>Finally the {@link jakarta.faces.render.Renderer} must call {@link #writeStyleClassToParent()}</li>
 *  </ul>
 *  See {@link BaseCuiCommandButton} for usage
 */
public interface MyFacesDelegateStyleClassAdapter {

    /**
     * Writes the current style-class tot the parent
     */
    void writeStyleClassToParent();
}
