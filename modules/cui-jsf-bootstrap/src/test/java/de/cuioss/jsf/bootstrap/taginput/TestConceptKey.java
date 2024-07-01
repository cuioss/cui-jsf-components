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
package de.cuioss.jsf.bootstrap.taginput;

import java.io.Serial;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString(callSuper = true)
public class TestConceptKey implements ConceptKeyType {

    @Serial
    private static final long serialVersionUID = 4612196631296025943L;

    @Override
    public ConceptCategory getCategory() {
        return new BaseConceptCategory();
    }

    @Override
    public Set<String> getAliases() {
        return Collections.emptySet();
    }

    @Override
    public String get(final String key, final String defaultValue) {
        return "test";
    }

    @Override
    public String get(final String key) {
        return "test";
    }

    @Override
    public boolean containsKey(final String key) {
        return false;
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return Collections.emptySet();
    }

    @Override
    public String getResolved(final Locale locale) {
        return "test";
    }

    @Override
    public String getIdentifier() {
        return "test";
    }

    @Override
    public int compareTo(final ConceptKeyType o) {
        return 1;
    }
}
