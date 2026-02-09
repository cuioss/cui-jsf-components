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
 * <h2>Overview</h2>
 * <p>
 * This package provides specialized JSF converters for various name provider implementations,
 * primarily focused on converting display name objects to their string representations for UI rendering.
 * 
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.api.converter.nameprovider.DisplayNameConverter} - Basic converter for 
 *       {@link de.cuioss.uimodel.nameprovider.DisplayName} objects</li>
 *   <li>{@link de.cuioss.jsf.api.converter.nameprovider.I18nDisplayNameProviderConverter} - Converter for
 *       internationalized display names</li>
 *   <li>{@link de.cuioss.jsf.api.converter.nameprovider.CodeTypeDisplayNameProviderConverter} - Converter for
 *       code-based display names</li>
 *   <li>{@link de.cuioss.jsf.api.converter.nameprovider.DisplayMessageProviderConverter} - Converter for
 *       message-based display names</li>
 *   <li>{@link de.cuioss.jsf.api.converter.nameprovider.LabeledKeyConverter} - Converter for
 *       labeled key objects</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * <pre>
 * &lt;!-- Basic DisplayName conversion --&gt;
 * &lt;h:outputText value="#{bean.displayName}" converter="#{displayNameConverter}" /&gt;
 * 
 * &lt;!-- I18n display name conversion --&gt;
 * &lt;h:outputText value="#{bean.i18nDisplayName}" converter="#{i18nDisplayNameProviderConverter}" /&gt;
 * 
 * &lt;!-- Code type display name conversion --&gt;
 * &lt;h:outputText value="#{bean.codeType}" converter="#{codeTypeDisplayNameProviderConverter}" /&gt;
 * </pre>
 * 
 * <h2>Design Principles</h2>
 * <p>
 * All converters in this package follow these principles:
 * <ul>
 *   <li>They are formatting-only converters (they don't support String to Object conversion)</li>
 *   <li>They handle null values gracefully by returning empty strings</li>
 *   <li>They leverage the display capabilities built into the corresponding model objects</li>
 *   <li>They follow the JSF converter contract and integrate with the standard JSF lifecycle</li>
 * </ul>
 * 
 * <h2>Related Concepts</h2>
 * <p>
 * These converters work closely with the display name provider interfaces and implementations
 * in the {@code de.cuioss.uimodel.nameprovider} package, providing the bridge between those
 * model objects and JSF rendering.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see de.cuioss.uimodel.nameprovider.DisplayName
 * @see de.cuioss.jsf.api.converter.AbstractConverter
 */
package de.cuioss.jsf.api.converter.nameprovider;
