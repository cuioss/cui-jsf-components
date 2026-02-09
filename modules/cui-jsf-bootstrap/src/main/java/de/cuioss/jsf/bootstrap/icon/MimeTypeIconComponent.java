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
package de.cuioss.jsf.bootstrap.icon;

import de.cuioss.jsf.api.components.base.AbstractBaseCuiComponent;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.icon.support.CssMimeTypeIcon;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.FacesComponent;
import lombok.experimental.Delegate;

/**
 * <p>
 * Specialized icon component for displaying MIME type icons from the CUI icon library.
 * This component renders appropriate icons based on file types or MIME type identifiers,
 * providing a consistent visual representation for different file formats across the application.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Displays icons specific to file types/MIME types (e.g., PDF, Excel, audio)</li>
 *   <li>Supports all MIME types defined in the {@link MimeTypeIcon} enum</li>
 *   <li>Configurable icon size (xs, sm, md, lg, xl)</li>
 *   <li>Optional decorator styling for layered icon display</li>
 *   <li>Tooltip/title support with resource bundle integration</li>
 *   <li>Customizable styling through additional CSS classes and styles</li>
 * </ul>
 * 
 * <h2>Component Structure</h2>
 * <p>
 * The component renders a styled icon representation of a file type using the CUI icon system.
 * Depending on the configuration, it may render:
 * </p>
 * <ul>
 *   <li>A simple icon representation of the MIME type</li>
 *   <li>A stacked icon with base layer and a type indicator</li>
 *   <li>A customized decorator layer if decorator class is specified</li>
 * </ul>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link TitleProvider} - For tooltip/title support with resource bundle integration</li>
 *   <li>{@link ContextSizeProvider} - For icon size configuration (xs, sm, md, lg, xl)</li>
 *   <li>{@link ComponentStyleClassProvider} - For additional CSS styling</li>
 *   <li>{@link StyleAttributeProvider} - For inline CSS styling</li>
 *   <li><b>mimeTypeIcon</b> - The {@link MimeTypeIcon} enum value to be displayed</li>
 *   <li><b>mimeTypeString</b> - String representation of a MIME type (alternative to mimeTypeIcon)</li>
 *   <li><b>decoratorClass</b> - Additional CSS class for the decorating layer (defaults to "cui-mime-type-no-decorator")</li>
 * </ul>
 * 
 * <h2>Selection Logic</h2>
 * <p>
 * The component resolves which MIME type icon to display using the following logic:
 * </p>
 * <ol>
 *   <li>If mimeTypeIcon is set, it takes precedence</li>
 *   <li>Otherwise, if mimeTypeString is set, it's converted to the corresponding enum value</li>
 *   <li>If neither is set, or if mimeTypeString doesn't map to a valid enum, falls back to MimeTypeIcon.UNDEFINED</li>
 * </ol>
 * 
 * <h2>Usage Examples</h2>
 * 
 * <p><b>Using enum constant name as string:</b></p>
 * <pre>
 * &lt;boot:mimeTypeIcon mimeTypeString="AUDIO_MPEG" titleValue="Audio file" /&gt;
 * </pre>
 * 
 * <p><b>Using the enum directly (in backing bean):</b></p>
 * <pre>
 * &lt;boot:mimeTypeIcon mimeTypeIcon="#{backingBean.mimeType}" size="lg" /&gt;
 * </pre>
 * 
 * <p><b>With custom decorator:</b></p>
 * <pre>
 * &lt;boot:mimeTypeIcon mimeTypeString="PDF" decoratorClass="my-pdf-decorator" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @see MimeTypeIcon
 * @see MimeTypeIconRenderer
 * @see IconComponent
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
     * #mimeTypeString is set #mimeTypeIcon take precedence.
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
     * In case of this class being present, an additional layer will be rendered with
     * the decoratorStyleClass being applied.
     *
     * @return the decoratorClass. If none is set it returns
     * {@link CssMimeTypeIcon#CUI_STACKED_ICON_NO_DECORATOR}
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
