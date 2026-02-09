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
/**
 * <h2>HTML Component Generation Package</h2>
 * <p>
 * Provides common helper classes and utilities that can be used
 * for the creation of partial HTML trees. This package offers a fluent API
 * for building HTML structures programmatically, ensuring proper nesting and escaping.
 * </p>
 * 
 * <h3>Key Components</h3>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.components.html.HtmlTreeBuilder} - Central builder class with fluent methods for creating HTML trees</li>
 *   <li>{@link de.cuioss.jsf.api.components.html.Node} - Enum representing HTML element types</li>
 *   <li>{@link de.cuioss.jsf.api.components.html.AttributeName} - Standard HTML attribute names</li>
 *   <li>{@link de.cuioss.jsf.api.components.html.AttributeValue} - Common HTML attribute values</li>
 *   <li>NodeElement - Representation of a concrete HTML node element</li>
 *   <li>HtmlFragment - Represents a structured HTML fragment for JSF rendering</li>
 * </ul>
 * 
 * <h3>Usage Example</h3>
 * <pre>
 * {@code
 * new HtmlTreeBuilder()
 *   .withNode(Node.DIV)
 *   .withStyleClass("alert alert-info")
 *   .withAttribute(AttributeName.ROLE, "alert")
 *   .withTextContent("Some Text Child");
 * }
 * </pre>
 * 
 * <h3>Advanced Example</h3>
 * <pre>
 * {@code
 * new HtmlTreeBuilder()
 *   .withNode(Node.DIV)
 *   .withStyleClass("container")
 *   .withNode(Node.SPAN)
 *     .withStyleClass("label")
 *     .withTextContent("Label:")
 *   .currentHierarchyUp()
 *   .withNode(Node.INPUT)
 *     .withAttribute(AttributeName.TYPE, "text")
 *     .withAttribute(AttributeName.VALUE, "Hello World")
 *   .currentHierarchyUp()
 *   .build();
 * }
 * </pre>
 * 
 * <h3>Integration with JSF</h3>
 * <p>
 * The result can be directly passed to a
 * {@link jakarta.faces.context.ResponseWriter} in order to integrate with the
 * creation of {@link jakarta.faces.render.Renderer} implementations.
 * </p>
 * 
 * <p>
 * Classes in this package are generally immutable and therefore thread-safe.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.api.components.html;
