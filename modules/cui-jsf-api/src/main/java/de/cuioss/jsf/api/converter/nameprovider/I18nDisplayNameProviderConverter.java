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

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.cuioss.jsf.api.application.locale.LocaleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import de.cuioss.jsf.api.security.CuiSanitizer;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;

/**
 * Converter handle to convert {@linkplain I18nDisplayNameProvider} to locale
 * specific text representation
 *
 * @author Eugen Fischer
 */
@FacesConverter(forClass = I18nDisplayNameProvider.class)
public class I18nDisplayNameProviderConverter extends AbstractConverter<I18nDisplayNameProvider> {

	private final LocaleAccessor localeProducerAccessor = new LocaleAccessor();

	@Override
	protected String convertToString(final FacesContext context, final UIComponent component,
			final I18nDisplayNameProvider value) throws ConverterException {

		final var locale = localeProducerAccessor.getValue();

		var text = value.lookupTextFor(locale);

		if (isEmpty(text)) {
			text = value.lookupTextWithFallbackFirstFittingLanguageOnly(locale);
		}

		if (!isEmpty(text))
			return CuiSanitizer.COMPLEX_HTML_PRESERVE_ENTITIES.apply(text);

		return "";
	}

}
