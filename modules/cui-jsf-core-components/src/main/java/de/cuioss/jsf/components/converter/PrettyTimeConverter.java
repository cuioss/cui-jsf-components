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

import de.cuioss.jsf.api.common.accessor.LocaleAccessor;
import de.cuioss.jsf.api.converter.AbstractConverter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * <p>JSF converter that transforms date/time objects into human-readable, localized
 * relative time expressions using the PrettyTime library. Examples include phrases
 * like "5 minutes ago", "in 2 days", "moments ago", or "last week".</p>
 * 
 * <p>This converter supports a wide range of date/time types:</p>
 * <ul>
 *   <li>{@link Date} - Standard Java legacy date</li>
 *   <li>{@link Calendar} - Java legacy calendar</li>
 *   <li>{@link ZonedDateTime} - Java 8+ date-time with timezone</li>
 *   <li>{@link LocalDateTime} - Java 8+ date-time without timezone (uses system default)</li>
 *   <li>{@link LocalDate} - Java 8+ date-only (converts to start of day in system default timezone)</li>
 * </ul>
 * 
 * <p>The conversion is performed relative to the current time and uses the current
 * {@link Locale} from the JSF context via {@link LocaleAccessor} to ensure proper
 * localization of the resulting phrases.</p>
 * 
 * <p>The converter maintains an LRU cache of {@link PrettyTime} instances for different
 * locales to improve performance when the same locale is used repeatedly.</p>
 * 
 * <h2>Usage Example</h2>
 * 
 * <pre>
 * &lt;h:outputText value="#{bean.creationDate}"&gt;
 *     &lt;f:converter converterId="de.cuioss.cui.converter.PrettyTimeConverter" /&gt;
 * &lt;/h:outputText&gt;
 * </pre>
 *
 * <p><strong>Dependency Requirement:</strong> This converter requires the following
 * dependency at runtime:</p>
 *
 * <pre>
 * {@code
 * <groupId>org.ocpsoft.prettytime</groupId>
 * <artifactId>prettytime</artifactId>
 * }
 * </pre>
 * 
 * <p>This converter is thread-safe. The backing {@link PrettyTime} instances are
 * cached and accessed in a thread-safe manner.</p>
 *
 * @author Matthias Walliczek
 * @see PrettyTime The underlying library for generating human-readable time expressions
 * @see LocaleAccessor Used to access the current locale from the JSF context
 * @since 1.0
 */
@FacesConverter(value = "de.cuioss.cui.converter.PrettyTimeConverter")
public class PrettyTimeConverter extends AbstractConverter<Object> {

    /**
     * Maximum size of the PrettyTime instance cache to prevent memory leaks.
     * Once this limit is reached, the least recently used entries are removed.
     */
    private static final int CACHE_SIZE = 20;
    
    /**
     * Thread-safe LRU (Least Recently Used) cache of PrettyTime instances by locale.
     * This improves performance by reusing instances for frequently used locales.
     */
    private static final Map<Locale, PrettyTime> PRETTY_TIME_MAP = new LinkedHashMap<>(CACHE_SIZE + 1, 1.1F, true) {

        @Serial
        private static final long serialVersionUID = -8941794067746423324L;

        @Override
        protected boolean removeEldestEntry(final Map.Entry<Locale, PrettyTime> eldest) {
            return size() > CACHE_SIZE;
        }
    };

    /**
     * Accessor for retrieving the current locale from the JSF context.
     */
    private final LocaleAccessor localeProducerAccessor = new LocaleAccessor();

    /**
     * Retrieves or creates a PrettyTime instance for the current locale.
     * This method uses a thread-safe cache of PrettyTime instances to improve performance.
     *
     * @return A PrettyTime instance configured for the current locale
     */
    private PrettyTime getPrettyTime() {
        var current = localeProducerAccessor.getValue();
        PrettyTime prettyTime;
        synchronized (PRETTY_TIME_MAP) {
            prettyTime = PRETTY_TIME_MAP.computeIfAbsent(current, PrettyTime::new);
        }
        return prettyTime;
    }

    /**
     * <p>Converts a date/time object to a human-readable, relative time expression.
     * The method supports multiple date/time types and converts them to a {@link Date}
     * before passing to the PrettyTime library for formatting.</p>
     * 
     * <p>For {@link LocalDate} objects, the method uses the start of day (midnight)
     * in the system default timezone to ensure consistent behavior.</p>
     *
     * @param context The FacesContext for the current request, not null
     * @param component The component associated with this converter, not null
     * @param value The date/time object to be converted (supported types: {@link Date},
     *              {@link Calendar}, {@link ZonedDateTime}, {@link LocalDateTime}, {@link LocalDate})
     * @return A localized, human-readable string representing the relative time
     * @throws ConverterException If the value is null or not one of the supported date/time types
     */
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

        if (null == toBeConverted) {
            throw new ConverterException(
                    "Invalid Type given: Expected one of 'java.util.Date', 'java.util.Calendar', 'java.time.LocalDateTime' or 'java.time.LocalDate' but found "
                            + value.getClass());
        }
        return getPrettyTime().format(toBeConverted);
    }
}
