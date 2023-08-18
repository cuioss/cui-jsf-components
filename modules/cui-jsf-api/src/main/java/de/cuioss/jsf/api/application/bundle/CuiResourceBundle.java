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

import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Messages provider utilized for combining multiple {@link ResourceBundle}
 * usually in the form of a {@link ResourceBundleWrapper}. It is normally
 * utilized as a ManagedBean.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(callSuper = false)
@ToString
public class CuiResourceBundle extends ResourceBundle implements Serializable {

    /** The default name this bean should be registered to. */
    public static final String BEAN_NAME = "msgs";

    private static final long serialVersionUID = -522513490589688119L;

    /**
     * {@link ResourceBundleWrapper} must be injected from outside.
     */
    @Setter
    @Getter
    private ResourceBundleWrapper resourceBundleWrapper;

    @Override
    protected Object handleGetObject(final String key) {
        return resourceBundleWrapper.getMessage(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return resourceBundleWrapper.getKeys();
    }
}
