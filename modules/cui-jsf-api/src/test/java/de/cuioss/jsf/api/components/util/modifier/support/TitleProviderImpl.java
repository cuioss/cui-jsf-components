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
package de.cuioss.jsf.api.components.util.modifier.support;

import java.io.Serializable;

import javax.faces.component.UIComponentBase;

import de.cuioss.jsf.api.components.partial.TitleProvider;
import lombok.Getter;
import lombok.Setter;

public class TitleProviderImpl extends UIComponentBase implements TitleProvider {

    @Getter
    private String titleValue;

    @Getter
    @Setter
    private String title;

    @Override
    public String getFamily() {
        return null;
    }

    @Override
    public void setTitleKey(final String titleKey) {
    }

    @Override
    public String getTitleKey() {
        return null;
    }

    @Override
    public void setTitleValue(final Serializable titleValue) {
        this.titleValue = (String) titleValue;
    }

    @Override
    public Object getTitleConverter() {
        return null;
    }

    @Override
    public void setTitleConverter(final Object titleConverter) {
    }

    @Override
    public String resolveTitle() {
        return titleValue;
    }

    @Override
    public void resolveAndStoreTitle() {

    }

    @Override
    public boolean isTitleSet() {
        return null != titleValue;
    }
}
