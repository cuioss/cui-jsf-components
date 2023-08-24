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

import static de.cuioss.jsf.api.security.CuiSanitizer.COMPLEX_HTML;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.application.locale.LocaleProducerAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.uimodel.nameprovider.CodeTypeDisplayNameProvider;

@FacesConverter(forClass = CodeTypeDisplayNameProvider.class)
public class CodeTypeDisplayNameProviderConverter extends AbstractConverter<CodeTypeDisplayNameProvider> {

    @Override
    protected String convertToString(FacesContext context, UIComponent component, CodeTypeDisplayNameProvider value)
            throws ConverterException {
        return COMPLEX_HTML.apply(value.getContent().getResolved(new LocaleProducerAccessor().getValue().getLocale()));
    }
}
