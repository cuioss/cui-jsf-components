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

import java.util.Collection;

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.base.BaseCuiNamingContainer;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ContentProvider;
import de.cuioss.jsf.api.components.partial.ContextSizeProvider;
import de.cuioss.jsf.api.components.partial.ContextStateProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.tag.support.TagHelper;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an Tag similar to a JIRA Tag. The tag is rendered within a span with
 * the according classes. The content and title are resolved using the cui
 * standard label-resolving mechanism.
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link ContextSizeProvider}</li>
 * <li>{@link ComponentStyleClassProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>value: the value of the component. It is supposed to be either a single
 * {@link ConceptKeyType}, {@link String} or a {@link Collection} of
 * {@link ConceptKeyType} or {@link String}</li>
 * <li>contentEscape: indicating whether the content of the tags need to be
 * escaped. defaults to <code>true</code></li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:tagList value="#{bean.tags}" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
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
     *
     */
    public TagListComponent() {
        super.setRendererType(BootstrapFamily.TAG_LIST_COMPONENT_RENDERER);
        contextSizeProvider = new ContextSizeProvider(this);
        contextStateProvider = new ContextStateProvider(this);
        contentProvider = new ContentProvider(this);
    }

    /**
     * @return the value of the component. It is supposed to be either a single
     *         {@link ConceptKeyType} or a {@link Collection} of
     *         {@link ConceptKeyType}
     */
    public Object getValue() {
        return getStateHelper().eval(TAG_LIST_KEY);
    }

    /**
     * @param tagList the value of the component. It is supposed to be either a
     *                single {@link ConceptKeyType} or a {@link Collection} of
     *                {@link ConceptKeyType}
     */
    public void setValue(final Object tagList) {
        getStateHelper().put(TAG_LIST_KEY, tagList);
    }

    /**
     * @return boolean indicating whether the content of the tags need to be
     *         escaped. defaults to true
     */
    public boolean getContentEscape() {
        return contentProvider.getContentEscape();
    }

    /**
     * @param contentEscape to set.
     */
    public void setContentEscape(final boolean contentEscape) {
        contentProvider.setContentEscape(contentEscape);
    }

    @Override
    public boolean isRendered() {
        return super.isRendered() && !TagHelper.getValueAsSet(getValue()).isEmpty();
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }
}
