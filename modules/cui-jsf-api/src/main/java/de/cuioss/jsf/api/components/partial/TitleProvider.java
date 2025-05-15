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

import java.io.Serializable;

import jakarta.faces.component.UIComponent;
import jakarta.faces.render.Renderer;

/**
 * Provider interface for components that need to support HTML title attributes.
 * <p>
 * This interface defines the contract for managing the state and resolving of the title
 * attribute on JSF components. Implementing components can provide title text either through
 * a resource bundle key, direct value, or a combination with conversion support.
 * </p>
 * <p>
 * The implementation relies on consistent attribute naming that matches the accessor methods
 * defined in this interface.
 * </p>
 * 
 * <h2>Attributes</h2>
 * <h3>titleKey</h3>
 * <p>
 * The key for looking up the localized text for the title attribute from a resource bundle.
 * This attribute is not required, but either this or {@code titleValue} must be provided
 * to display a title.
 * </p>
 * 
 * <h3>titleValue</h3>
 * <p>
 * The direct value to be displayed as the title attribute. This takes precedence over
 * {@code titleKey} if both are present. The value is typically a String but can be any
 * Serializable object. If a non-String value is provided, a converter must be available
 * either through the default converter facility or explicitly through {@code titleConverter}.
 * </p>
 * 
 * <h3>titleConverter</h3>
 * <p>
 * An optional converter ID or Converter instance to be used when {@code titleValue} needs
 * conversion to a String. Only required when titleValue is not a String and no default
 * converter is registered for its type.
 * </p>
 * 
 * <h2>State Management Note</h2>
 * <p>
 * Due to differences between JSF implementations (Mojarra vs MyFaces), there's a special
 * requirement for handling title resolution:
 * </p>
 * <p>
 * In previous versions targeted at Mojarra, the title resolution logic was solely in the
 * {@link #resolveTitle()} method. With MyFaces, the default Renderer uses 
 * {@link UIComponent#getAttributes()} to look up the title attribute.
 * </p>
 * <p>
 * To address this difference, implementations must call {@link #resolveAndStoreTitle()}
 * prior to rendering. This is typically handled by the component's {@link Renderer}
 * in the encodeBegin method. This workaround is only necessary for cases where the 
 * rendering is done by a concrete implementation rather than using the standard HTML renderer.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public interface TitleProvider {

    /**
     * Sets the resource bundle key for the title.
     *
     * @param titleKey the resource bundle key to be used for title lookup
     */
    void setTitleKey(String titleKey);

    /**
     * Returns the configured resource bundle key for the title.
     *
     * @return the resource bundle key used for title lookup, may be null
     */
    String getTitleKey();

    /**
     * Sets a direct value for the title.
     * <p>
     * This value takes precedence over the titleKey if both are specified.
     * </p>
     *
     * @param titleValue the value to be used as title, must be Serializable
     */
    void setTitleValue(Serializable titleValue);

    /**
     * Returns the configured direct value for the title.
     *
     * @return the direct value configured for the title, may be null
     */
    Serializable getTitleValue();

    /**
     * Returns the converter configured for the title value.
     *
     * @return the configured converter (as a String converterId or a Converter instance),
     *         may be null
     */
    Object getTitleConverter();

    /**
     * Sets the converter to use for the title value.
     * <p>
     * The converter is used when the titleValue is not a String and needs to be converted
     * to a String representation.
     * </p>
     *
     * @param titleConverter the converter to use (can be a String converterId or a
     *                      Converter instance)
     */
    void setTitleConverter(Object titleConverter);

    /**
     * Resolves the title based on the configured titleKey, titleValue, and titleConverter.
     * <p>
     * This method performs the following resolution logic:
     * </p>
     * <ol>
     *   <li>If titleValue is set, it is converted to a String (using the titleConverter if specified)</li>
     *   <li>If no titleValue is set but titleKey is available, the key is used to look up
     *       a message from the component's message bundle</li>
     *   <li>If neither is set, null is returned</li>
     * </ol>
     *
     * @return the resolved title if available, otherwise null
     */
    String resolveTitle();

    /**
     * Resolves the title and stores it in the component's state.
     * <p>
     * This method must be called before rendering to ensure proper title handling
     * across different JSF implementations. It resolves the title and stores it in the
     * component's attribute map for later retrieval by the standard HTML renderer.
     * </p>
     * <p>
     * This is primarily needed to handle differences between Mojarra and MyFaces
     * rendering behavior.
     * </p>
     */
    void resolveAndStoreTitle();

    /**
     * Returns the title from the component's state.
     * <p>
     * This method is typically used by renderers to retrieve the resolved title.
     * For proper functionality, {@link #resolveAndStoreTitle()} should be called
     * before this method.
     * </p>
     *
     * @return the resolved title if available, otherwise null
     */
    String getTitle();

    /**
     * Checks if a title is set (either via titleKey or titleValue).
     *
     * @return true if a title is set, false otherwise
     */
    boolean isTitleSet();

    /**
     * This method is provided for compatibility with the standard title attribute
     * pattern but should not be used directly.
     * <p>
     * Always throws an {@link UnsupportedOperationException} indicating that developers
     * should use {@link #setTitleValue(Serializable)} or {@link #setTitleKey(String)} instead.
     * </p>
     *
     * @param title the title string
     * @throws UnsupportedOperationException always thrown to discourage direct use
     */
    void setTitle(String title);
}
