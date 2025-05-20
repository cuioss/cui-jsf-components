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
package de.cuioss.jsf.bootstrap.taglist;

import de.cuioss.jsf.api.components.base.BaseCuiNamingContainer;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.tag.support.TagHelper;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import jakarta.faces.component.FacesComponent;
import lombok.experimental.Delegate;

import java.util.Collection;

/**
 * Component that renders a collection of tags in a consistent layout.
 * Handles various input types including single tags, collections of tags, and strings.
 * 
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ContextSizeProvider} - Size of all tags (SM, DEFAULT, LG, XL)</li>
 * <li>{@link ComponentStyleClassProvider} - Custom CSS classes</li>
 * <li>{@link StyleAttributeProvider} - Custom inline styles</li>
 * <li>{@link ContextStateProvider} - Visual state/appearance of tags</li>
 * <li><b>value</b>: Tag content as {@link ConceptKeyType}, {@link String}, 
 *     or {@link Collection} of either type</li>
 * <li><b>contentEscape</b>: Whether to escape tag content (default: true)</li>
 * </ul>
 * 
 * <h2>Usage Example</h2>
 * <pre>
 * &lt;boot:tagList value="#{bean.tagCollection}" 
 *            state="INFO" size="SM" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@FacesComponent(BootstrapFamily.TAG_LIST_COMPONENT)
public class TagListComponent extends BaseCuiNamingContainer {

    /** Default exception message for an invalid value. */
    public static final String INVALID_VALUE_EXCEPTION = "Neither java.util.Collection, ConceptKeyType, nor String found for the value-attribute: %s";

    private static final String TAG_LIST_KEY = "value";

    /** Partial elements. */
    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    @Delegate
    private final ContextStateProvider contextStateProvider;

    /** Used for accessing the contentEscape attribute. */
    private final ContentProvider contentProvider;

    /**
     * Constructs a new TagListComponent with default settings.
     * Initializes the component with the appropriate renderer type and
     * required providers for context size, state, and content configuration.
     */
    public TagListComponent() {
        super.setRendererType(BootstrapFamily.TAG_LIST_COMPONENT_RENDERER);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        contentProvider = new ContentProvider(this);
    }

    /**
     * Returns the value of the component, which represents the tag(s) to be displayed.
     * The value can be either a single {@link ConceptKeyType}, a single {@link String}, 
     * or a {@link Collection} of either type.
     *
     * @return the object representing the tag(s) to be displayed
     */
    public Object getValue() {
        return getStateHelper().eval(TAG_LIST_KEY);
    }

    /**
     * Sets the value of the component, which represents the tag(s) to be displayed.
     * 
     * @param tagList the value to set, which can be either a single {@link ConceptKeyType}, 
     *                a single {@link String}, or a {@link Collection} of either type
     */
    public void setValue(final Object tagList) {
        getStateHelper().put(TAG_LIST_KEY, tagList);
    }

    /**
     * Determines whether the content of the tags should be HTML-escaped.
     * Escaping helps prevent XSS attacks when displaying user-provided content.
     *
     * @return boolean indicating whether tag content should be escaped (defaults to true)
     */
    public boolean getContentEscape() {
        return contentProvider.getContentEscape();
    }

    /**
     * Sets whether the content of the tags should be HTML-escaped.
     * 
     * @param contentEscape true to enable content escaping, false to disable it
     */
    public void setContentEscape(final boolean contentEscape) {
        contentProvider.setContentEscape(contentEscape);
    }

    /**
     * {@inheritDoc}
     * 
     * Extends the standard {@link jakarta.faces.component.UIComponent#isRendered()} check
     * to prevent rendering when there are no tags to display.
     * 
     * @return true if the component should be rendered (parent check passes and there
     *         are tags to display), false otherwise
     */
    @Override
    public boolean isRendered() {
        return super.isRendered() && !TagHelper.getValueAsSet(getValue()).isEmpty();
    }

    /**
     * {@inheritDoc}
     * 
     * @return the component family identifier
     */
    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }
}
