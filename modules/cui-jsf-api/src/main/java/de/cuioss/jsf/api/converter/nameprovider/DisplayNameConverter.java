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
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 * A JSF converter for {@link DisplayName} objects that renders them as sanitized HTML strings.
 * <p>
 * This converter is registered automatically for the {@link DisplayName} class through the 
 * {@link FacesConverter#forClass()} mechanism, making it the default converter used when 
 * a DisplayName object is rendered in a JSF view.
 * </p>
 * <p>
 * The converter extracts the content from the DisplayName object using {@link DisplayName#getContent()}
 * and applies the {@link CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES} sanitizer to it. This allows
 * for safe rendering of HTML content that might be contained in the DisplayName, while still
 * protecting against XSS attacks.
 * </p>
 * <p>
 * Usage example (implicit, as it's registered for the DisplayName class):
 * </p>
 * <pre>
 * &lt;h:outputText value="#{bean.displayName}" /&gt;
 * </pre>
 * <p>
 * Or explicitly:
 * </p>
 * <pre>
 * &lt;h:outputText value="#{bean.displayName}" converter="#{displayNameConverter}" /&gt;
 * </pre>
 * <p>
 * This is a formatting-only converter and does not support converting from String back to 
 * DisplayName. The {@link #getAsObject(FacesContext, UIComponent, String)} method will throw an 
 * {@link UnsupportedOperationException} if called.
 * </p>
 *
 * @author Eugen Fischer
 * @since 1.0
 * @see DisplayName
 * @see IDisplayNameProvider
 * @see CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES
 */
@FacesConverter(forClass = DisplayName.class)
public class DisplayNameConverter extends AbstractConverter<DisplayName> {

    /**
     * Converts a {@link DisplayName} object to a sanitized HTML string for rendering.
     * <p>
     * This method extracts the content from the DisplayName using {@link DisplayName#getContent()}
     * and applies the {@link CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES} sanitizer to ensure
     * the output is safe for rendering in HTML context.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The {@link DisplayName} to be converted, guaranteed to be non-null
     * @return a sanitized string representation of the DisplayName content
     * @throws ConverterException if conversion fails
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final DisplayName value)
            throws ConverterException {
        return CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(value.getContent());
    }
}
