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
package de.cuioss.jsf.api.converter.nameprovider;

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.portal.common.bundle.PortalResourceBundleBean;
import de.cuioss.uimodel.nameprovider.DisplayMessageProvider;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * A JSF converter that transforms {@link DisplayMessageProvider} objects into formatted message string
 * representations suitable for display in UI components.
 * <p>
 * This converter is registered automatically for the {@link DisplayMessageProvider} class through
 * the {@link FacesConverter#forClass()} mechanism, making it the default converter used when
 * a DisplayMessageProvider object is rendered in a JSF view.
 * </p>
 * <p>
 * The converter uses {@code DisplayMessageProvider#getMessageFormated} with the appropriate 
 * resource bundle to format the message and any parameters contained within the DisplayMessageProvider. 
 * It automatically handles internationalization by using the {@link PortalResourceBundleBean} 
 * to access locale-specific messages.
 * </p>
 * <p>
 * Usage example (implicit, as it's registered for the DisplayMessageProvider class):
 * </p>
 * <pre>
 * &lt;h:outputText value="#{bean.displayMessage}" /&gt;
 * </pre>
 * <p>
 * Or explicitly:
 * </p>
 * <pre>
 * &lt;h:outputText value="#{bean.displayMessage}" converter="#{displayMessageProviderConverter}" /&gt;
 * </pre>
 * <p>
 * This is a formatting-only converter and does not support converting from String back to
 * DisplayMessageProvider. The {@link #getAsObject(FacesContext, UIComponent, String)} method will throw an
 * {@link UnsupportedOperationException} if called.
 * </p>
 *
 * @author Eugen Fischer
 * @since 1.0
 * @see DisplayMessageProvider
 * @see PortalResourceBundleBean
 * @see AbstractConverter
 */
@FacesConverter(forClass = DisplayMessageProvider.class)
public class DisplayMessageProviderConverter extends AbstractConverter<DisplayMessageProvider> {

    /**
     * Converts a {@link DisplayMessageProvider} object to its formatted string representation.
     * <p>
     * This method formats the message using the {@link PortalResourceBundleBean} to obtain
     * localized texts and applies any parameter substitutions defined in the DisplayMessageProvider.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The {@link DisplayMessageProvider} to be converted, guaranteed to be non-null
     * @return the formatted message string
     * @throws ConverterException if conversion fails
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final DisplayMessageProvider value) throws ConverterException {

        return value.getMessageFormated(new PortalResourceBundleBean());
    }
}
