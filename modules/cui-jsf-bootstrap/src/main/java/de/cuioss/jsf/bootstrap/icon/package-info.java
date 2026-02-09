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
 * <p>
 * Provides a comprehensive set of icon components and renderers for the CUI JSF Bootstrap framework.
 * This package implements various specialized icon components that follow consistent rendering patterns
 * and styling conventions, enabling a unified visual language across applications.
 * </p>
 * 
 * <h2>Main Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.IconComponent} - Base component for rendering simple icons</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.LabeledIconComponent} - Component for icons with associated text labels</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.MimeTypeIconComponent} - Component for displaying file type specific icons</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.GenderIconComponent} - Component for displaying gender-specific icons</li>
 * </ul>
 * 
 * <h2>Renderers</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.IconRenderer} - Handles basic icon rendering</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.LabeledIconRenderer} - Specializes in rendering icons with labels</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.MimeTypeIconRenderer} - Creates complex layered structure for file type icons</li>
 * </ul>
 * 
 * <h2>Supporting Types</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.bootstrap.icon.MimeTypeIcon} - Enum defining available file type icons</li>
 * </ul>
 *
 * <h2>CSS Integration</h2>
 * <p>
 * All icon components use CSS classes from the CUI icon library, providing a wide range of customization
 * options including size variations, contextual styling, and special effects. The CSS classes are defined
 * in the related support packages and follow a consistent naming pattern.
 * </p>
 *
 * <h2>Related Packages</h2>
 * <ul>
 *   <li>{@code de.cuioss.jsf.bootstrap.icon.support} - Provides CSS class definitions and style constants</li>
 *   <li>{@code de.cuioss.jsf.bootstrap.icon.strategy} - Provides strategy implementations for MIME type resolution</li>
 * </ul>
 *
 * <h2>Usage Examples</h2>
 * <p>Basic icon:</p>
 * <pre>
 * &lt;boot:icon icon="cui-icon-info" /&gt;
 * </pre>
 * 
 * <p>Labeled icon:</p>
 * <pre>
 * &lt;boot:labeledIcon icon="cui-icon-warning" labelValue="Warning" iconAlign="left" /&gt;
 * </pre>
 * 
 * <p>MIME type icon:</p>
 * <pre>
 * &lt;boot:mimeTypeIcon mimeTypeString="PDF" size="lg" titleValue="PDF Document" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 *
 */
package de.cuioss.jsf.bootstrap.icon;
