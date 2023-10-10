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
package de.cuioss.jsf.api.common.accessor;

import java.util.Locale;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;

import lombok.Getter;

/**
 * <p>
 * Determines the current active user-locale. It uses the {@link ViewHandler}
 * therefore.
 * </p>
 * <em>Caution:</em>
 * <ul>
 * <li>The {@link LocaleAccessor} must never be referenced longer than a
 * request:</li>
 * <li>In case of type in the portal context, use PortalLocale instead</li>
 * </ul>
 *
 *
 */
public class LocaleAccessor implements ManagedAccessor<Locale> {

    private static final long serialVersionUID = -7372535413254248257L;

    @Getter(lazy = true)
    private final Locale value = resolveValue();

    private Locale resolveValue() {
        var context = FacesContext.getCurrentInstance();
        return context.getApplication().getViewHandler().calculateLocale(context);
    }

}
