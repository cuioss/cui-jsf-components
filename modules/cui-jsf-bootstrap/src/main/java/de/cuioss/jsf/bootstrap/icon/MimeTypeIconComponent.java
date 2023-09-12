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
package de.cuioss.jsf.bootstrap.icon;

import javax.faces.component.FacesComponent;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.partial.ComponentStyleClassProvider;
import de.cuioss.jsf.api.components.partial.ContextSizeProvider;
import de.cuioss.jsf.api.components.partial.StyleAttributeProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.icon.support.CssMimeTypeIcon;
import de.cuioss.tools.string.MoreStrings;
import lombok.experimental.Delegate;

/**
 * <p>
 * Renders an MimeTypeIcon regarding to the cui-icon contract.
 * </p>
 * <p>
 * A list of all available mimeTypeIcons can be found at the <a href=
 * "https://cuioss.de/cui-reference-documentation/pages/documentation/icons/mime_types.jsf">
 * Reference Documentation</a>
 * </p>
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider}</li>
 * <li>{@link ContextSizeProvider}</li>
 * <li>{@link ComponentStyleClassProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * <li>decoratorClass: Additional class for the decorating layer. Defaults to
 * "cui-mime-type-no-decorator"</li>
 * <li>mimeTypeIcon: The MimeTypeIcon to be displayed. In case #mimeTypeIcon and
 * #mimeTypeString is set #mimeTypeIcon take precedence</li>
 * <li>mimeTypeString: The string representation of a concrete mime-type. In
 * case #mimeTypeIcon and #mimeTypeString is set #mimeTypeIcon take
 * precedence.</li>
 * </ul>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:mimeTypeIcon mimeTypeString="AUDIO_MPEG" titleValue="AUDIO_MPEG" size="xl" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 */
@FacesComponent(BootstrapFamily.MIME_TYPE_ICON_COMPONENT)
public class MimeTypeIconComponent extends AbstractBaseCuiComponent implements TitleProvider {

    private static final String DECORATOR_CLASS_KEY = "decoratorClass";

    private static final String MIME_TYPE_KEY = "mimeTypeIcon";

    private static final String MIME_TYPE_STRING_KEY = "mimeTypeString";

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final ContextSizeProvider contextSizeProvider;

    /**
     *
     */
    public MimeTypeIconComponent() {
        super.setRendererType(BootstrapFamily.MIME_TYPE_ICON_COMPONENT_RENDERER);
        titleProvider = new TitleProviderImpl(this);
        contextSizeProvider = new ContextSizeProvider(this);
    }

    /**
     * @return the mimeTypeIcon
     */
    public MimeTypeIcon getMimeTypeIcon() {
        return (MimeTypeIcon) getStateHelper().eval(MIME_TYPE_KEY);
    }

    /**
     * @param mimeTypeIcon the mimeTypeIcon to set
     */
    public void setMimeTypeIcon(MimeTypeIcon mimeTypeIcon) {
        getStateHelper().put(MIME_TYPE_KEY, mimeTypeIcon);
    }

    /**
     * @return the mimeTypeString
     */
    public String getMimeTypeString() {
        return (String) getStateHelper().eval(MIME_TYPE_STRING_KEY);
    }

    /**
     * @param mimeTypeString the mimeTypeString to set
     */
    public void setMimeTypeString(String mimeTypeString) {
        getStateHelper().put(MIME_TYPE_STRING_KEY, mimeTypeString);
    }

    /**
     * @return the resolved {@link MimeTypeIcon}. In case #mimeTypeIcon and
     *         #mimeTypeString is set #mimeTypeIcon take precedence.
     */
    public MimeTypeIcon resolveMimeTypeIcon() {
        var icon = getMimeTypeIcon();
        if (null == icon) {
            var identifier = getMimeTypeString();
            if (MoreStrings.isEmpty(identifier)) {
                icon = MimeTypeIcon.UNDEFINED;
            } else {
                icon = MimeTypeIcon.valueOf(identifier.toUpperCase());
            }
        }
        return icon;
    }

    /**
     * In case of this class being present an additional layer will be rendered with
     * the decoratorStyleClass being applied.
     *
     * @return the decoratorClass. If none is set it returns
     *         {@link CssMimeTypeIcon#CUI_STACKED_ICON_NO_DECORATOR}
     */
    public String getDecoratorClass() {
        return (String) getStateHelper().eval(DECORATOR_CLASS_KEY,
                CssMimeTypeIcon.CUI_STACKED_ICON_NO_DECORATOR.getStyleClass());
    }

    /**
     * @param decoratorClass the decoratorClass to set
     */
    public void setDecoratorClass(String decoratorClass) {
        getStateHelper().put(DECORATOR_CLASS_KEY, decoratorClass);
    }

    @Override
    public String getFamily() {
        return BootstrapFamily.COMPONENT_FAMILY;
    }
}
