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

import de.cuioss.jsf.api.common.accessor.ManagedBeanAccessor;

/**
 * Class for lookup managed bean which implements {@linkplain LocaleProducer}
 * interface. The implementation assumes an instance of {@link LocaleProducer}
 * being present under the name of {@link LocaleProducerImpl#BEAN_NAME}
 *
 * @author Eugen Fischer
 * @deprecated ManagedBeans are replaced by CDI. Use
 *             {@code PortalBeanManager.resolveBeanOrThrowIllegalStateException(Locale.class,
 * PortalLocale.class)}; instead
 */
@Deprecated
public class LocaleProducerAccessor extends ManagedBeanAccessor<LocaleProducer> {

    private static final long serialVersionUID = -7372535413254248257L;

    /**
     * Constructor.
     */
    public LocaleProducerAccessor() {
        super(LocaleProducerImpl.BEAN_NAME, LocaleProducer.class, true);
    }

}
