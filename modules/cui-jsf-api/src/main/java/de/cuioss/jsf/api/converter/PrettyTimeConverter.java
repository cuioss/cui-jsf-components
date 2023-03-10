package de.cuioss.jsf.api.converter;

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

import de.cuioss.jsf.api.application.locale.LocaleProducerAccessor;

/**
 * Converter to display a {@link Date}, {@link Calendar}, {@link LocalDateTime},
 * {@link ZonedDateTime} or {@link LocalDate} as pretty time. If it detects a {@link LocalDate} it
 * uses {@link LocalDate#atStartOfDay()} in order to set a defined point in time. It
 * loads the current {@link Locale} using the {@link LocaleProducerAccessor}. If
 * you want to use it you need the dependency at runtime
 *
 * <pre>
 * {@code
	<groupId>org.ocpsoft.prettytime</groupId>
	<artifactId>prettytime</artifactId>}
 * </pre>
 *
 * @author Matthias Walliczek
 */
@FacesConverter(value = "com.icw.cui.converter.PrettyTimeConverter")
public class PrettyTimeConverter extends AbstractConverter<Object> {

    // See
    // https://github.com/ocpsoft/prettytime/blob/master/jsf/src/main/java/org/ocpsoft/prettytime/jsf/PrettyTimeConverter.java
    private static final int CACHE_SIZE = 20;
    private static final Map<Locale, PrettyTime> PRETTY_TIME_MAP =
        new LinkedHashMap<>(CACHE_SIZE + 1,
                1.1F, true) {

            private static final long serialVersionUID = -8941794067746423324L;

            @Override
            protected boolean removeEldestEntry(final Map.Entry<Locale, PrettyTime> eldest) {
                return size() > CACHE_SIZE;
            }
        };

    private final LocaleProducerAccessor localeProducerAccessor = new LocaleProducerAccessor();

    /**
     * @return the instance of {@link PrettyTime} for the current {@link Locale}
     */
    private PrettyTime getPrettyTime() {
        var current = localeProducerAccessor.getValue().getLocale();
        PrettyTime prettyTime;
        synchronized (PRETTY_TIME_MAP) {
            prettyTime = PRETTY_TIME_MAP.computeIfAbsent(current, PrettyTime::new);
        }
        return prettyTime;
    }

    @Override
    protected String convertToString(final FacesContext context, final UIComponent component,
            final Object value)
        throws ConverterException {
        Date toBeConverted = null;
        if (value instanceof Date) {
            toBeConverted = (Date) value;
        } else if (value instanceof Calendar) {
            toBeConverted = ((Calendar) value).getTime();
        } else if (value instanceof ZonedDateTime) {
            var zDate = (ZonedDateTime) value;
            var instant = zDate.toInstant();
            toBeConverted = Date.from(instant);
        } else if (value instanceof LocalDateTime) {
            var lDate = (LocalDateTime) value;
            var instant = lDate.atZone(ZoneId.systemDefault()).toInstant();
            toBeConverted = Date.from(instant);
        } else if (value instanceof LocalDate) {
            var lDate = (LocalDate) value;
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
