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

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Single {@link Rule} define a condition when this rule has to be chosen and
 * the corresponding action result.<br>
 * Instead create a rule using constructor the class provide needed factory
 * methods.<br>
 * As there are {@linkplain #create(Serializable, Serializable)} for a regular
 * rule and {@linkplain #createDefaultRule(Serializable)} which is needed if no
 * other rule conditions maps.
 *
 * @author Eugen Fischer
 * @param <K> bounded type for condition must be serializable
 * @param <V> bounded type for result must be serializable
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
