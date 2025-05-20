/**
 * <h2>Logging Infrastructure for JSF API Module</h2>
 * <p>
 * This package provides structured logging infrastructure for the JSF API module,
 * including centralized log message definitions and logging utilities specific to
 * JSF applications.
 * 
 * <h3>Core Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.common.logging.JsfApiLogMessages}: Defines the log message
 *       constants for the JSF API module with structured identifiers and message templates</li>
 * </ul>
 * 
 * <h3>Key Features</h3>
 * <ul>
 *   <li>Structured log messages with unique identifiers</li>
 *   <li>Consistent log level categorization (INFO, WARN, ERROR, FATAL)</li>
 *   <li>Standardized message templates for common scenarios</li>
 *   <li>Integration with CUI logging framework</li>
 * </ul>
 * 
 * <h3>Usage Scenarios</h3>
 * <p>
 * The logging infrastructure is particularly useful for:
 * <ul>
 *   <li>Consistent error reporting across the JSF API module</li>
 *   <li>Centralized management of log messages</li>
 *   <li>Categorization of log messages by severity level</li>
 *   <li>Standardized formatting of common log scenarios</li>
 * </ul>
 * 
 * <h3>Usage Example</h3>
 * <pre>
 * // Using a log message constant with CuiLogger
 * private static final CuiLogger log = new CuiLogger(MyClass.class);
 * 
 * public void someMethod() {
 *     try {
 *         // Some operation that might fail
 *     } catch (Exception e) {
 *         log.error(JsfApiLogMessages.ERROR.SILENT_ERROR, e);
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see de.cuioss.tools.logging.CuiLogger
 * @see de.cuioss.tools.logging.LogRecord
 */
package de.cuioss.jsf.api.common.logging;