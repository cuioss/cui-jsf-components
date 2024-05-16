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

import java.text.MessageFormat;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;

import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.portal.common.bundle.ResourceBundleWrapper;
import de.cuioss.portal.common.cdi.PortalBeanManager;
import de.cuioss.uimodel.nameprovider.LabeledKey;

/**
 * Converter for the type {@link LabeledKey}
 *
 * @author Oliver Wolff
 */
@FacesConverter(forClass = LabeledKey.class)
public class LabeledKeyConverter extends AbstractConverter<LabeledKey> {

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component, final LabeledKey value) {
        String result;
        var bundle = PortalBeanManager.resolveRequiredBean(ResourceBundleWrapper.class);
        if (value.getParameter().isEmpty()) {
            result = bundle.getString(value.getContent());
        } else {
            result = MessageFormat.format(bundle.getString(value.getContent()),
                    value.getParameter().toArray(new Object[value.getParameter().size()]));
        }
        return CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(result);
    }

}
