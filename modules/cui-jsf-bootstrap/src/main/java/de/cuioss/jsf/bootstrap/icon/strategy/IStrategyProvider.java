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

import java.io.Serializable;

/**
 * <p>
 * A generic strategy provider interface that defines a type-safe mechanism for implementing
 * the Strategy design pattern. This interface is primarily used in icon resolution systems to
 * determine the appropriate icon based on various conditions (such as MIME types, file extensions,
 * or format codes).
 * </p>
 *
 * <h2>Design Pattern</h2>
 * <p>
 * This interface implements the Strategy design pattern with the following characteristics:
 * </p>
 * <ul>
 *   <li>Strategy selection based on input condition</li>
 *   <li>Type-safe implementation through generic parameters</li>
 *   <li>Default fallback behavior when no specific strategy matches</li>
 *   <li>Rule-based condition matching</li>
 * </ul>
 *
 * <h2>Contract</h2>
 * <p>
 * Implementations of this interface must:
 * </p>
 * <ol>
 *   <li>Implement {@link #actOnCondition(Serializable)} to process the input condition</li>
 *   <li>Apply appropriate rules to match conditions to results</li>
 *   <li>Fall back to a default rule when no specific rule matches</li>
 *   <li>Handle null conditions gracefully</li>
 * </ol>
 *
 * <h2>Usage Patterns</h2>
 * <p>
 * This interface is typically used in the following contexts:
 * </p>
 * <ul>
 *   <li>Icon resolution systems (e.g., MIME type to icon mapping)</li>
 *   <li>Format code resolution (e.g., HL7 format code to document type)</li>
 *   <li>File extension to document type mapping</li>
 *   <li>Any conditional mapping that requires a default fallback</li>
 * </ul>
 *
 * <h2>Implementation Example</h2>
 * <pre>
 * IStrategyProvider&lt;String, MimeTypeIcon&gt; provider = new StrategyProviderImpl.Builder&lt;String, MimeTypeIcon&gt;()
 *     .add(Rule.create("application/pdf", MimeTypeIcon.PDF))
 *     .add(Rule.create("image/jpeg", MimeTypeIcon.JPEG))
 *     .defineDefaultRule(Rule.createDefaultRule(MimeTypeIcon.UNDEFINED))
 *     .build();
 *
 * MimeTypeIcon icon = provider.actOnCondition("application/pdf"); // Returns MimeTypeIcon.PDF
 * MimeTypeIcon fallback = provider.actOnCondition("unknown/type"); // Returns MimeTypeIcon.UNDEFINED
 * </pre>
 *
 * @see StrategyProviderImpl Implementation of this interface
 * @see Rule The condition-to-result mapping used by strategy providers
 *
 * @author Eugen Fischer
 * @param <K> The type of condition to evaluate (must be serializable)
 * @param <V> The type of result to return (must be serializable)
 */
@FunctionalInterface
public interface IStrategyProvider<K extends Serializable, V extends Serializable> extends Serializable {

    /**
     * Act on forwarded condition by using rules. If no known rule condition fits,
     * fallback to default.
     *
     * @param condition of strategy
     * @return result defined in rule which fitting to the condition
     */
    V actOnCondition(K condition);

}
