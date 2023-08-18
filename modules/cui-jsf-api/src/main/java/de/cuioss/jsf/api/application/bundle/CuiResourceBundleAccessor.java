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
package de.cuioss.jsf.api.application.bundle;

import java.util.ResourceBundle;

import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

/**
 * Accesses instances of {@link CuiResourceBundle}.
 *
 * @author Oliver Wolff
 */
public class CuiResourceBundleAccessor extends ManagedBeanAccessor<ResourceBundle> {

    private static final long serialVersionUID = -2944602525380062391L;

    /**
     * Constructor.
     */
    public CuiResourceBundleAccessor() {
        super(CuiResourceBundle.BEAN_NAME, ResourceBundle.class, true);
    }

}
