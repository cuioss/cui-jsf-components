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
package de.cuioss.jsf.api.application.locale;

import java.util.Locale;

import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.common.accessor.ManagedAccessor;

/**
 * Determines the current active user-locale. It uses the {@link ViewHandler}
 * therefore. <em>Caution: </em>In case of type in the portal context, use
 * PortalLocale instead
 */
public class LocaleAccessor implements ManagedAccessor<Locale> {

	private static final long serialVersionUID = -7372535413254248257L;

	private Locale locale;

	@Override
	public Locale getValue() {
		if (null == locale) {
			var context = FacesContext.getCurrentInstance();
			locale = context.getApplication().getViewHandler().calculateLocale(context);
		}
		return locale;
	}

}
