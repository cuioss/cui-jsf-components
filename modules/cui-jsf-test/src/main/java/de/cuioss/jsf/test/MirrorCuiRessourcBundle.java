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
package de.cuioss.jsf.test;

import de.cuioss.portal.common.bundle.ResourceBundleWrapper;
import jakarta.enterprise.context.RequestScoped;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Collections;
import java.util.Set;

/**
 * Identity version of {@link ResourceBundleWrapper}. It will always return the
 * key.
 *
 * @author Oliver Wolff
 */
@RequestScoped
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class MirrorCuiRessourcBundle implements ResourceBundleWrapper {

    @Serial
    private static final long serialVersionUID = 7035144185855294606L;

    @Override
    public String getString(String key) {
        return key;
    }

    @Override
    public Set<String> keySet() {
        return Collections.emptySet();
    }

    @Override
    public String getBundleContent() {
        return "mirror";
    }

}
