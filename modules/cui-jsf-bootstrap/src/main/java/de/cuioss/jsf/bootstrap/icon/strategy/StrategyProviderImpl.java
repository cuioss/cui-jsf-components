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
package de.cuioss.jsf.bootstrap.icon.strategy;

import static de.cuioss.tools.collect.CollectionLiterals.immutableMap;
import static java.util.Objects.requireNonNull;

import de.cuioss.tools.base.Preconditions;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Standard implementation of the {@link IStrategyProvider} interface that provides a rule-based
 * strategy resolution system. This implementation manages a collection of rules and supports
 * a default fallback rule for unmatched conditions.
 * </p>
 * 
 * <h2>Key Features</h2>
 * <ul>
 *   <li>Immutable implementation with thread-safety guarantees</li>
 *   <li>Builder pattern for intuitive and safe construction</li>
 *   <li>Efficient condition matching using a map-based lookup</li>
 *   <li>Required default rule to ensure complete condition coverage</li>
 *   <li>Type-safe generic implementation</li>
 * </ul>
 * 
 * <h2>Implementation Details</h2>
 * <p>
 * The class stores rules as key-value pairs in an immutable map for fast lookup, where:
 * </p>
 * <ul>
 *   <li>Keys are the rule conditions</li>
 *   <li>Values are the associated results</li>
 *   <li>A separate default rule handles fallback behavior</li>
 * </ul>
 * 
 * <h2>Thread Safety</h2>
 * <p>
 * This implementation is immutable and thread-safe once constructed. The internal map 
 * and default rule cannot be modified after creation, making it safe for concurrent use
 * across multiple threads.
 * </p>
 * 
 * <h2>Usage Example</h2>
 * <pre>
 * // Create a strategy provider that maps MIME types to icons
 * StrategyProviderImpl&lt;String, MimeTypeIcon&gt; mimeTypeResolver = 
 *     new StrategyProviderImpl.Builder&lt;String, MimeTypeIcon&gt;()
 *         .add(Rule.create("application/pdf", MimeTypeIcon.PDF))
 *         .add(Rule.create("image/jpeg", MimeTypeIcon.JPEG))
 *         .add(Rule.create("text/plain", MimeTypeIcon.TEXT))
 *         .defineDefaultRule(Rule.createDefaultRule(MimeTypeIcon.UNDEFINED))
 *         .build();
 *         
 * // Resolve icons based on MIME types
 * MimeTypeIcon pdfIcon = mimeTypeResolver.actOnCondition("application/pdf");
 * MimeTypeIcon unknownIcon = mimeTypeResolver.actOnCondition("unknown/type");
 * </pre>
 *
 * @author Eugen Fischer
 * @param <K> The type of condition to evaluate (must be serializable)
 * @param <V> The type of result to return (must be serializable)
 * 
 * @see IStrategyProvider The interface implemented by this class
 * @see Rule The condition-result pairs used by this provider
 * @see Builder The builder class for creating instances of this provider
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class StrategyProviderImpl<K extends Serializable, V extends Serializable> implements IStrategyProvider<K, V> {

    /** serial Version UID */
    @Serial
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
     * <p>
     * A builder class that facilitates the construction of immutable {@link StrategyProviderImpl} instances
     * using a fluent API. The builder collects rules and the default rule, then constructs
     * an immutable strategy provider upon calling {@link #build()}.
     * </p>
     * 
     * <h2>Key Features</h2>
     * <ul>
     *   <li>Fluent API for intuitive construction</li>
     *   <li>Rule validation during construction</li>
     *   <li>Prevention of duplicate rule conditions</li>
     *   <li>Enforcement of default rule presence</li>
     *   <li>Reusable - can build multiple StrategyProvider instances</li>
     * </ul>
     * 
     * <h2>Usage Pattern</h2>
     * <p>
     * The typical pattern for using this builder is:
     * </p>
     * <ol>
     *   <li>Create a new Builder instance</li>
     *   <li>Add one or more rules with {@link #add(Rule)}</li>
     *   <li>Define a default rule with {@link #defineDefaultRule(Rule)}</li>
     *   <li>Call {@link #build()} to create the StrategyProviderImpl</li>
     * </ol>
     * 
     * <h2>Example</h2>
     * <pre>
     * StrategyProviderImpl&lt;String, MimeTypeIcon&gt; provider = 
     *     new StrategyProviderImpl.Builder&lt;String, MimeTypeIcon&gt;()
     *         .add(Rule.create("application/pdf", MimeTypeIcon.PDF))
     *         .add(Rule.create("image/jpeg", MimeTypeIcon.JPEG))
     *         .defineDefaultRule(Rule.createDefaultRule(MimeTypeIcon.UNDEFINED))
     *         .build();
     * </pre>
     * 
     * <h2>Builder Reuse</h2>
     * <p>
     * Builder instances can be reused to create multiple StrategyProvider instances:
     * </p>
     * <pre>
     * Builder&lt;String, MimeTypeIcon&gt; builder = 
     *     new StrategyProviderImpl.Builder&lt;String, MimeTypeIcon&gt;()
     *         .add(Rule.create("application/pdf", MimeTypeIcon.PDF))
     *         .defineDefaultRule(Rule.createDefaultRule(MimeTypeIcon.UNDEFINED));
     *         
     * // Create first provider
     * StrategyProviderImpl&lt;String, MimeTypeIcon&gt; provider1 = builder.build();
     * 
     * // Add more rules and create a second provider (superset of the first)
     * builder.add(Rule.create("image/jpeg", MimeTypeIcon.JPEG));
     * StrategyProviderImpl&lt;String, MimeTypeIcon&gt; provider2 = builder.build();
     * </pre>
     *
     * @author Eugen Fischer
     * @param <K> The type of condition to evaluate (must be serializable)
     * @param <V> The type of result to return (must be serializable)
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
