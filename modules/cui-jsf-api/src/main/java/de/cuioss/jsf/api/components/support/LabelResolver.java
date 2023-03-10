package de.cuioss.jsf.api.components.support;

import java.io.Serializable;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import de.cuioss.jsf.api.application.bundle.CuiResourceBundle;
import de.cuioss.jsf.api.application.bundle.CuiResourceBundleAccessor;
import de.cuioss.jsf.api.common.accessor.ConverterAccessor;
import de.cuioss.tools.string.MoreStrings;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * Helper class encapsulating the dynamic resolving of labels, depending on the
 * existence of the attribute labelKey and labelValue. It uses the
 * {@link CuiResourceBundle} for looking up the labelKey.
 * </p>
 * <h3>Algorithm</h3>
 * <ul>
 * <li>If a value for the attribute "labelValue" exists: Return it. It checks
 * whether either a converter is available or the corresponding converterId
 * Attribute</li>
 * <li>If a value for the attribute "labelKey" exists: look it up in the
 * {@link CuiResourceBundle}</li>
 * <li>If none of the above takes place, throw {@link IllegalStateException}
 * depending whether strictMode is set to <code>true</code>. strictMode is set
 * to <code>false</code> it will return null on that case.</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@Builder(setterPrefix = "with")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LabelResolver {

    private static final HtmlOutputText DUMMY = new HtmlOutputText();

    private final String labelKey;
    private final Serializable labelValue;
    private final Object converter;
    private final boolean strictMode;

    @Builder.Default
    private final boolean escape = true;

    /**
     * Resolves the label according to the algorithm described at class level.
     *
     * @param context
     * @return the resolved label.
     */
    public String resolve(final FacesContext context) {
        if (strictMode && MoreStrings.isEmpty(labelKey) && null == labelValue) {
            throw new IllegalStateException(
                    "Neither labelvalue nor LabelKey is set. Either set it or use strictMode=false");
        }
        if (null != labelValue) {
            Converter resolvedConverter = null;
            if (converter instanceof Converter) {
                resolvedConverter = (Converter) converter;
            } else if (converter instanceof String) {
                resolvedConverter = resolveConverterById((String) converter);
            } else if (null != converter) {
                throw new IllegalStateException(
                        "Invalid converter property - can not handle " + converter.getClass());
            }
            if (null == resolvedConverter) {
                resolvedConverter = resolveConverterByClass(labelValue.getClass());
            }
            if (null == resolvedConverter) {
                throw new IllegalStateException(
                        "Unable to determine converter for valueClass="
                                + labelValue.getClass());
            }
            DUMMY.setEscape(escape);
            return resolvedConverter.getAsString(context, DUMMY, labelValue);
        }
        if (!MoreStrings.isEmpty(labelKey)) {
            return new CuiResourceBundleAccessor().getValue().getString(labelKey);
        }

        return null;
    }

    /**
     * @param klazz
     * @return
     */
    private static Converter resolveConverterByClass(final Class<? extends Serializable> klazz) {
        final var accessor = new ConverterAccessor();
        accessor.setTargetClass(klazz);
        return accessor.getValue();
    }

    /**
     * @param converterId
     * @return
     */
    private static Converter resolveConverterById(final String converterId) {
        final var accessor = new ConverterAccessor();
        accessor.setConverterId(converterId);
        return accessor.getValue();
    }
}
