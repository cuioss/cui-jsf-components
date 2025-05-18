/**
 * <p>
 * This package provides a type-safe, flexible implementation of the Strategy design pattern
 * as an alternative to using {@link java.lang.Enum} for complex conditional logic.
 * </p>
 *
 * <h2>Package Components</h2>
 * <p>
 * The strategy implementation consists of three main components:
 * </p>
 * <ul>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.strategy.IStrategyProvider} - The core interface that defines
 *       the contract for strategy resolution. It provides a method to perform actions based on a condition.</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.strategy.Rule} - Represents a condition-result pair that defines
 *       when a specific strategy should be applied and what result it should return.</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.strategy.StrategyProviderImpl} - A concrete implementation of
 *       the strategy provider interface with an immutable, builder-based approach.</li>
 * </ul>
 *
 * <h2>Design Pattern Implementation</h2>
 * <p>
 * This package implements the Strategy pattern with these key characteristics:
 * </p>
 * <ul>
 *   <li>Type-safety through generics</li>
 *   <li>Immutable implementation for thread safety</li>
 *   <li>Builder pattern for intuitive construction</li>
 *   <li>Default-rule fallback mechanism</li>
 *   <li>Rule-based condition matching</li>
 * </ul>
 *
 * <h2>Primary Use Cases</h2>
 * <p>
 * This strategy implementation is primarily used for:
 * </p>
 * <ul>
 *   <li>MIME type to icon mapping in the icon components</li>
 *   <li>Gender to icon mapping for gender-specific icon display</li>
 *   <li>Any conditional mapping that benefits from a rule-based approach with default behavior</li>
 * </ul>
 *
 * @author Eugen Fischer
 *
 * @see de.cuioss.jsf.bootstrap.icon.strategy.IStrategyProvider
 * @see de.cuioss.jsf.bootstrap.icon.strategy.Rule
 * @see de.cuioss.jsf.bootstrap.icon.strategy.StrategyProviderImpl
 */
@java.lang.Deprecated
package de.cuioss.jsf.bootstrap.icon.strategy;
