package de.cuioss.jsf.bootstrap.icon.strategy;

import java.io.Serializable;

/**
 * Strategy Provider define a type safe interface for strategies.<br/>
 * Contract : Implementation of StrategyProvider need to implement
 * {@link IStrategyProvider#actOnCondition(Serializable)} This method act on
 * condition forwarded with defined rule. If no fitting rule which match the
 * condition the default rule has to be used. Furthermore the implementation
 * care handling of rules.
 *
 * @see StrategyProviderImpl as implementation of StrategyProvider
 * @author i000576
 * @param <K>
 *            bounded type for conditions must be serializable
 * @param <V>
 *            bounded type for results must be serializable
 */
@FunctionalInterface
public interface IStrategyProvider<K extends Serializable, V extends Serializable>
        extends Serializable {

    /**
     * Act on forwarded condition by using rules. If no known rule condition
     * fits, fallback to default.
     *
     * @param condition
     *            of strategy
     * @return result defined in rule which fitting to the condition
     */
    V actOnCondition(K condition);

}
