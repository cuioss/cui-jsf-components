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
package de.cuioss.jsf.components.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.ocpsoft.prettytime.PrettyTime;

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;

/**
 * Converter to display a {@link Date}, {@link Calendar}, {@link LocalDateTime},
 * {@link ZonedDateTime} or {@link LocalDate} as pretty time. If it detects a
 * {@link LocalDate} it uses {@link LocalDate#atStartOfDay()} in order to set a
 * defined point in time. It loads the current {@link Locale} using the
 * {@link LocaleAccessor}. If you want to use it you need the dependency at
 * runtime
 *
 * <pre>
 * {@code
	<groupId>org.ocpsoft.prettytime</groupId>
	<artifactId>prettytime</artifactId>}
 * </pre>
 *
 * @author Matthias Walliczek
 */
@FacesConverter(value = "de.cuioss.cui.converter.PrettyTimeConverter")
public class PrettyTimeConverter extends AbstractConverter<Object> {

	// See
	// https://github.com/ocpsoft/prettytime/blob/master/jsf/src/main/java/org/ocpsoft/prettytime/jsf/PrettyTimeConverter.java
	private static final int CACHE_SIZE = 20;
	private static final Map<Locale, PrettyTime> PRETTY_TIME_MAP = new LinkedHashMap<>(CACHE_SIZE + 1, 1.1F, true) {

		private static final long serialVersionUID = -8941794067746423324L;

		@Override
		protected boolean removeEldestEntry(final Map.Entry<Locale, PrettyTime> eldest) {
			return size() > CACHE_SIZE;
		}
	};

	private final LocaleAccessor localeProducerAccessor = new LocaleAccessor();

	/**
	 * @return the instance of {@link PrettyTime} for the current {@link Locale}
	 */
	private PrettyTime getPrettyTime() {
		var current = localeProducerAccessor.getValue();
		PrettyTime prettyTime;
		synchronized (PRETTY_TIME_MAP) {
			prettyTime = PRETTY_TIME_MAP.computeIfAbsent(current, PrettyTime::new);
		}
		return prettyTime;
	}

	@Override
	protected String convertToString(final FacesContext context, final UIComponent component, final Object value)
			throws ConverterException {
		Date toBeConverted = null;
		if (value instanceof Date date) {
			toBeConverted = date;
		} else if (value instanceof Calendar calendar) {
			toBeConverted = calendar.getTime();
		} else if (value instanceof ZonedDateTime zDate) {
			var instant = zDate.toInstant();
			toBeConverted = Date.from(instant);
		} else if (value instanceof LocalDateTime lDate) {
			var instant = lDate.atZone(ZoneId.systemDefault()).toInstant();
			toBeConverted = Date.from(instant);
		} else if (value instanceof LocalDate lDate) {
			var instant = lDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			toBeConverted = Date.from(instant);
		}

		if (null == toBeConverted)
			throw new ConverterException(
					"Invalid Type given: Expected one of 'java.util.Date', 'java.util.Calendar', 'java.time.LocalDateTime' or 'java.time.LocalDate' but found "
							+ value.getClass());
		return getPrettyTime().format(toBeConverted);
	}
}
