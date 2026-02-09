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
package de.cuioss.jsf.api;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import de.cuioss.jsf.api.application.bundle.CuiJSfResourceBundleLocator;
import de.cuioss.portal.common.bundle.PortalResourceBundleBean;
import de.cuioss.portal.common.bundle.ResourceBundleRegistry;
import de.cuioss.portal.common.bundle.ResourceBundleWrapperImpl;
import org.jboss.weld.junit5.auto.AddBeanClasses;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * It adds the {@link CuiJSfResourceBundleLocator},
 * {@link PortalResourceBundleBean}, {@link ResourceBundleWrapperImpl}
 *
 *
 * @author Oliver Wolff
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@AddBeanClasses({CuiJSfResourceBundleLocator.class, PortalResourceBundleBean.class, ResourceBundleRegistry.class,
        ResourceBundleWrapperImpl.class, LocaleProducerMock.class})
public @interface EnableResourceBundleSupport {
}
