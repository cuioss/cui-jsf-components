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

import java.io.Serializable;

/**
 * Strategy Provider define a type safe interface for strategies.<br>
 * Contract : Implementation of StrategyProvider need to implement
 * {@link IStrategyProvider#actOnCondition(Serializable)} This method act on
 * condition forwarded with defined rule. If no fitting rule which match the
 * condition the default rule has to be used. Furthermore the implementation
 * care handling of rules.
 *
 * @see StrategyProviderImpl as implementation of StrategyProvider
 * @author Eugen Fischer
 * @param <K> bounded type for conditions must be serializable
 * @param <V> bounded type for results must be serializable
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
