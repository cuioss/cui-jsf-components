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

import java.io.Serial;
import java.io.Serializable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * The Rule class represents a condition-result mapping within the Strategy design pattern.
 * Each rule defines a specific condition when the rule should be applied, and the corresponding
 * result to return when that condition is met. Rules are the building blocks for
 * {@link IStrategyProvider} implementations.
 * </p>
 * 
 * <h2>Key Characteristics</h2>
 * <ul>
 *   <li>Immutable value object with condition and result</li>
 *   <li>Type-safe through generic parameters</li>
 *   <li>Specialized support for default rules (null condition)</li>
 *   <li>Factory methods for clear, intention-revealing instantiation</li>
 * </ul>
 * 
 * <h2>Rule Types</h2>
 * <p>
 * The class supports two distinct types of rules:
 * </p>
 * <ol>
 *   <li><b>Regular Rule</b> - Has a non-null condition and a result to return when that condition matches</li>
 *   <li><b>Default Rule</b> - Has a null condition and represents the fallback behavior when no other rule matches</li>
 * </ol>
 * 
 * <h2>Creation Pattern</h2>
 * <p>
 * Rules should always be created using the provided factory methods rather than constructors:
 * </p>
 * <ul>
 *   <li>{@link #create(Serializable, Serializable)} - Creates a regular rule with a specified condition</li>
 *   <li>{@link #createDefaultRule(Serializable)} - Creates a default rule with only a result value</li>
 * </ul>
 * 
 * <h2>Equality and Comparison</h2>
 * <p>
 * Rules are compared based on their condition values only. Two rules with the same condition
 * are considered equal even if they have different result values. This is important to consider 
 * when using rules in collections.
 * </p>
 * 
 * <h2>Usage Example</h2>
 * <pre>
 * // Creating regular rules
 * Rule&lt;String, MimeTypeIcon&gt; pdfRule = Rule.create("application/pdf", MimeTypeIcon.PDF);
 * Rule&lt;String, MimeTypeIcon&gt; jpegRule = Rule.create("image/jpeg", MimeTypeIcon.JPEG);
 * 
 * // Creating a default rule
 * Rule&lt;String, MimeTypeIcon&gt; defaultRule = Rule.createDefaultRule(MimeTypeIcon.UNDEFINED);
 * 
 * // Using rules in a strategy provider
 * IStrategyProvider&lt;String, MimeTypeIcon&gt; provider = new StrategyProviderImpl.Builder&lt;String, MimeTypeIcon&gt;()
 *     .add(pdfRule)
 *     .add(jpegRule)
 *     .defineDefaultRule(defaultRule)
 *     .build();
 * </pre>
 *
 * @author Eugen Fischer
 * @param <K> The type of condition to evaluate (must be serializable)
 * @param <V> The type of result to return (must be serializable)
 * 
 * @see IStrategyProvider The interface that uses rules for strategy implementation
 * @see StrategyProviderImpl Implementation of the strategy provider that consumes rules
 */
@ToString
@EqualsAndHashCode(of = {"condition", "serialVersionUID"})
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Rule<K extends Serializable, V extends Serializable> implements Serializable {

    /** serial Version UID */
    @Serial
    private static final long serialVersionUID = -6667079433559908595L;

    @Getter
    private final K condition;

    @Getter
    private final V result;

    /**
     * Factory Method provide simple creation for regular rule
     *
     * @param condition which detect which rule need to be executed
     * @param result    result which should be handle default behavior
     * @return complete Rule
     */
    public static <X extends Serializable, Y extends Serializable> Rule<X, Y> create(X condition, Y result) {
        return new Rule<>(condition, result);
    }

    /**
     * Factory Method provide simple creation for default rule
     *
     * @param result which should be handle default behavior
     * @return Rule which can be used as default
     */
    public static <X extends Serializable, Y extends Serializable> Rule<X, Y> createDefaultRule(Y result) {
        return new Rule<>(null, result);
    }

}
