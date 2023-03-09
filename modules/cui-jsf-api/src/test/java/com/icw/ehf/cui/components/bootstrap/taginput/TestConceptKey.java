package com.icw.ehf.cui.components.bootstrap.taginput;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("javadoc")
@EqualsAndHashCode
@ToString(callSuper = true)
public class TestConceptKey implements ConceptKeyType {

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
