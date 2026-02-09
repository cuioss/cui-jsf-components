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
package de.cuioss.jsf.api.components.support;

import de.cuioss.jsf.api.common.accessor.ConverterAccessor;
import de.cuioss.portal.common.bundle.ResourceBundleWrapper;
import de.cuioss.portal.common.cdi.PortalBeanManager;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * Helper class encapsulating the dynamic resolving of labels, depending on the
 * existence of the attribute labelKey and labelValue. It uses the
 * {@link ResourceBundleWrapper} for looking up the labelKey.
 * </p>
 * <h3>Algorithm</h3>
 * <ul>
 * <li>If a value for the attribute "labelValue" exists: Return it. It checks
 * whether either a converter is available or the corresponding converterId
 * Attribute</li>
 * <li>If a value for the attribute "labelKey" exists: look it up in the
 * {@link ResourceBundleWrapper}</li>
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
    private final Boolean strictMode;

    @Builder.Default
    private final boolean escape = true;

    /**
     * Resolves the label according to the algorithm described at class level.
     *
     * @param context
     * @return the resolved label.
     */
    public String resolve(final FacesContext context) {
        if (Boolean.TRUE.equals(strictMode) && MoreStrings.isEmpty(labelKey) && null == labelValue) {
            throw new IllegalStateException(
                    "Neither labelvalue nor LabelKey is set. Either set it or use strictMode=false");
        }
        if (null != labelValue) {
            Converter resolvedConverter = null;
            if (converter instanceof Converter converter1) {
                resolvedConverter = converter1;
            } else if (converter instanceof String string) {
                resolvedConverter = resolveConverterById(string);
            } else if (null != converter) {
                throw new IllegalStateException("Invalid converter property - can not handle " + converter.getClass());
            }
            if (null == resolvedConverter) {
                resolvedConverter = resolveConverterByClass(labelValue.getClass());
            }
            if (null == resolvedConverter) {
                throw new IllegalStateException(
                        "Unable to determine converter for valueClass=" + labelValue.getClass());
            }
            DUMMY.setEscape(escape);
            return resolvedConverter.getAsString(context, DUMMY, labelValue);
        }
        if (!MoreStrings.isEmpty(labelKey)) {
            return PortalBeanManager.resolveRequiredBean(ResourceBundleWrapper.class).getString(labelKey);
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
