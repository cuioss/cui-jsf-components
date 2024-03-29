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
package de.cuioss.jsf.api.components.partial;

import javax.faces.component.UIComponent;
import java.io.Serializable;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the title
 * attribute. The implementation relies on the correct user of the attribute
 * names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>titleKey</h2>
 * <p>
 * The key for looking up the text for the title-attribute. Although this
 * attribute is not required you must provide either this or #titleValue if you
 * want a title to be displayed.
 * </p>
 * <h2>titleValue</h2>
 * <p>
 * The Object displayed for the title-attribute. This is a replacement for
 * #titleKey. If both are present titleValue takes precedence. This object is
 * usually a String. If not, the developer must ensure that a corresponding
 * converter is either registered for the type or must provide a converter using
 * #titleConverter.
 * </p>
 * <h2>titleConverter</h2>
 * <p>
 * The optional converterId to be used in case of titleValue is set and needs
 * conversion.
 * </p>
 * <h2>Update to the state-management</h2>
 * In previous versions targeted at mojarra, the logic of modifying the final title was in the method
 * {@link #resolveTitle()}}.
 * With myfaces on the other hand the default Renderer use {@link UIComponent#getAttributes()} for looking up the
 * title.
 * <p>
 * The Solution: {@link #resolveAndStoreTitle()} must be called prior rendering.
 * This must be done prior Rendering, usually by the concrete {@link javax.faces.render.Renderer}
 * <em>This workaround is only necessary for cases, where the rendering is done by the concrete implementation.</em>
 * </p>
 *
 * @author Oliver Wolff
 */
public interface TitleProvider {

    /**
     * @param titleKey to be set.
     */
    void setTitleKey(String titleKey);

    /**
     * @return the resolved titleKey.
     */
    String getTitleKey();

    /**
     * @param titleValue to be set.
     */
    void setTitleValue(Serializable titleValue);

    /**
     * @return the titleValue.
     */
    Serializable getTitleValue();

    /**
     * @return the titleConverter
     */
    Object getTitleConverter();

    /**
     * @param titleConverter the titleConverter to set
     */
    void setTitleConverter(Object titleConverter);

    /**
     * @return the resolved title is available, otherwise it will return null.
     */
    String resolveTitle();

    /**
     * Resolves and stores the final title in the {@link javax.faces.component.StateHelper}, see class-documentation
     * for details.
     */
    void resolveAndStoreTitle();

    /**
     * @return the resolved title is available, otherwise it will return null.
     */
    String getTitle();

    /**
     * @return boolean indicating whether a title is there (set)
     */
    boolean isTitleSet();

    /**
     * @param title as defined within components but this one will always throw an
     *              {@link UnsupportedOperationException} indicating that the
     *              developer should always use {@link #setTitleValue(Serializable)}
     *              instead
     */
    void setTitle(String title);

}
