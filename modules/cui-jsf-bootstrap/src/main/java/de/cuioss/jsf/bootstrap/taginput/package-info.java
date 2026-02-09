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
 * Provides components for interactive tag input fields that allow users to select, 
 * create, and manage tags as form inputs.
 * 
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.bootstrap.taginput.TagInputComponent}: Main component for tag input fields</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.taginput.TagInputRenderer}: Renderer for tag input components</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.taginput.ConceptKeyStringConverter}: Converter for tag collections</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.taginput.PassthroughAttributes}: HTML5 attributes for Selectize.js</li>
 * </ul>
 * 
 * <h2>Usage Example</h2>
 * <pre>
 * &lt;boot:tagInput id="tags" 
 *               value="#{bean.selectedTags}" 
 *               sourceSet="#{bean.availableTags}"
 *               allowUserValues="true" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @author Sven Haag
 * @since 1.0
 * @see de.cuioss.jsf.bootstrap.tag
 * @see de.cuioss.jsf.bootstrap.selectize.Selectize
 */
package de.cuioss.jsf.bootstrap.taginput;
