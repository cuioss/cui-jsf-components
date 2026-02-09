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
package de.cuioss.jsf.api.common.accessor;

import jakarta.faces.context.FacesContext;
import lombok.Getter;

import java.io.Serial;
import java.util.Locale;

/**
 * Accessor for resolving {@link Locale} in a serialization-safe manner.
 * <p>
 * This accessor simplifies access to the current locale and enables serialization-safe
 * handling in JSF views. It retrieves the locale from the view-root using
 * {@link FacesContext#getViewRoot()} and then {@code getLocale()}.
 * 
 * <p>
 * Usage example:
 * <pre>
 * private final LocaleAccessor localeAccessor = new LocaleAccessor();
 * 
 * public String getLocalizedGreeting() {
 *     return messageBundle.getString("greeting", localeAccessor.getValue());
 * }
 * </pre>
 *
 * @author Oliver Wolff
 */
public class LocaleAccessor implements ManagedAccessor<Locale> {

    @Serial
    private static final long serialVersionUID = -7372535413254248257L;

    /**
     * The lazily initialized user locale.
     * This field is initialized by the {@link #resolveValue()} method
     * on the first call to {@link #getValue()}.
     */
    @Getter(lazy = true)
    private final Locale value = resolveValue();

    /**
     * Resolves the current user locale using the JSF ViewHandler.
     * <p>
     * This method determines the appropriate locale for the current user
     * based on the JSF locale resolution algorithm, which considers:
     * <ul>
     *   <li>User locale preferences from the browser</li>
     *   <li>Application default locale</li>
     *   <li>JSF configuration settings</li>
     * </ul>
     *
     * @return The resolved Locale for the current user
     * @throws IllegalStateException if called outside of a valid JSF request context
     */
    private Locale resolveValue() {
        var context = FacesContext.getCurrentInstance();
        return context.getApplication().getViewHandler().calculateLocale(context);
    }
}
