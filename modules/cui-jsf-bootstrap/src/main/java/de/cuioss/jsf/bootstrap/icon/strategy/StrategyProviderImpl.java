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
package de.cuioss.jsf.bootstrap.icon.strategy;

import static de.cuioss.tools.collect.CollectionLiterals.immutableMap;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.cuioss.tools.base.Preconditions;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Strategy Provider Implementation. Furthermore StrategyProviderImpl provide a
 * builder to create the StrategyProvider (see {@linkplain Builder}).
 *
 * @author Eugen Fischer
 * @param <K> bounded type for condition must be serializable
 * @param <V> bounded type for result must be serializable
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class StrategyProviderImpl<K extends Serializable, V extends Serializable> implements IStrategyProvider<K, V> {

    /** serial Version UID */
    private static final long serialVersionUID = 8396786922039611120L;

    private final Map<K, V> rules;

    private final Rule<? extends Serializable, V> defaultRule;

    @Override
    public V actOnCondition(final K condition) {
        if (rules.containsKey(condition)) {
            return rules.get(condition);
        }
        return defaultRule.getResult();
    }

    /**
     * Builder for creating StrategyProvider with immutable map of rules. Example:
     * <p>
     * <code>
     * static final StrategyProvider&lt;String, Integer&gt; WORD_TO_INT =
     * new StrategyProviderImpl.Builder&lt;String, Integer&gt;()
     * .add(Rule.create("a", 1))
     * .add(Rule.create("b", 2))
     * .defineDefaultRule(Rule.createDefaultRule(0))
     * .build();
     * </code>
     * <p>
     * For <i>small</i> immutable maps, the {@code ImmutableMap.of()} methods are
     * even more convenient.
     * <p>
     * Builder instances can be reused - it is safe to call {@link #build} multiple
     * times to build multiple maps in series. Each map is a superset of the maps
     * created before it.
     * </p>
     *
     * @author Eugen Fischer
     * @param <K> bounded type for condition must be serializable
     * @param <V> bounded type for result must be serializable
     */
    public static class Builder<K extends Serializable, V extends Serializable> {

        private final Map<K, V> mapBuilder = new HashMap<>();

        private Rule<? extends Serializable, V> defRule = null;

        /**
         * Add additional rule
         *
         * @param rule type safe rule object
         * @return fluent api style reference to the builder
         */
        public Builder<K, V> add(final Rule<K, V> rule) {
            mapBuilder.put(rule.getCondition(), rule.getResult());
            return this;
        }

        /**
         * Define default behavior. To protect user of wrong usage default rule can be
         * set once.
         *
         * @param rule {@linkplain Rule} must not be {@code null}. If there is a need of
         *             return {@code null} define a fitting default rule.
         * @return fluent api style reference to the builder
         * @throws IllegalArgumentException if default value should be overwritten
         *                                  during building object.
         */
        public Builder<K, V> defineDefaultRule(final Rule<? extends Serializable, V> rule) {
            Preconditions.checkState(null == defRule, "You try to overwrite allready defined default rule");
            defRule = requireNonNull(rule, "Default Rule must not be null");
            return this;
        }

        /**
         * Execute build after all needed rules are added and default value is defined.
         *
         * @return complete type safe StrategyHolder object which is ready for use
         * @throws IllegalArgumentException - if duplicate rues were added
         * @throws IllegalStateException    - if default value was not defined
         */
        public StrategyProviderImpl<K, V> build() {
            Preconditions.checkState(null != defRule, "You need to define default rule");
            return new StrategyProviderImpl<>(immutableMap(mapBuilder), defRule);
        }

    }

}
