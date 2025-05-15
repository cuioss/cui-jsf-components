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
package de.cuioss.jsf.api.converter.nameprovider;

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.portal.common.bundle.ResourceBundleWrapper;
import de.cuioss.portal.common.cdi.PortalBeanManager;
import de.cuioss.uimodel.nameprovider.LabeledKey;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;

import java.text.MessageFormat;

/**
 * A JSF converter that transforms {@link LabeledKey} objects into localized text strings.
 * <p>
 * This converter resolves resource bundle keys contained in LabeledKey objects and formats
 * them with any parameters provided. It is automatically registered for the {@link LabeledKey}
 * class through the {@link FacesConverter#forClass()} mechanism.
 * 
 * <p>
 * The converter performs the following operations:
 * <ol>
 *   <li>Resolves the {@link ResourceBundleWrapper} from the CDI context</li>
 *   <li>Looks up the resource bundle key stored in the {@link LabeledKey#getContent()} method</li>
 *   <li>If the LabeledKey contains parameters ({@link LabeledKey#getParameter()}), formats the 
 *       message using {@link MessageFormat}</li>
 *   <li>Sanitizes the resulting text using {@link CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES}
 *       to ensure safe HTML rendering</li>
 * </ol>
 * 
 * <p>
 * Usage example (implicit, as it's registered for the LabeledKey class):
 * <pre>
 * &lt;h:outputText value="#{bean.labeledKey}" /&gt;
 * </pre>
 * 
 * <p>
 * Or explicitly:
 * <pre>
 * &lt;h:outputText value="#{bean.labeledKey}" converter="#{labeledKeyConverter}" /&gt;
 * </pre>
 * 
 * <p>
 * This is a formatting-only converter and does not support converting from String back to
 * LabeledKey. The {@link #getAsObject(FacesContext, UIComponent, String)} method will throw an
 * {@link UnsupportedOperationException} if called.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see LabeledKey
 * @see ResourceBundleWrapper
 * @see CuiSanitizer#COMPLEX_HTML_PRESERVE_ENTITIES
 */
@FacesConverter(forClass = LabeledKey.class)
public class LabeledKeyConverter extends AbstractConverter<LabeledKey> {

    /**
     * Converts a {@link LabeledKey} object to its localized string representation.
     * <p>
     * This method resolves the resource bundle key stored in the LabeledKey, applies any
     * parameters using MessageFormat if available, and sanitizes the result for safe HTML rendering.
     * </p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this value is associated
     * @param value     The {@link LabeledKey} to be converted, guaranteed to be non-null
     * @return the localized, formatted, and sanitized string representation
     */
    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final LabeledKey value) {
        String result;
        // Resolve the resource bundle wrapper from the CDI context
        var bundle = PortalBeanManager.resolveRequiredBean(ResourceBundleWrapper.class);
        
        // Format the message, with or without parameters
        if (value.getParameter().isEmpty()) {
            result = bundle.getString(value.getContent());
        } else {
            result = MessageFormat.format(bundle.getString(value.getContent()),
                    value.getParameter().toArray(new Object[0]));
        }
        
        // Apply sanitization for safe HTML rendering
        return CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(result);
    }
}
