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
 * Provides components for displaying and interacting with tags in JSF applications.
 * 
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.bootstrap.tag.TagComponent} - Main component for rendering tags</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.tag.TagHandler} - Handler for tags at build time</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.tag.TagRenderer} - Renderer for tags</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.tag.DisposeBehavior} - Behavior for tag disposal</li>
 * </ul>
 * 
 * <h2>Related Packages</h2>
 * <ul>
 *   <li>{@link de.cuioss.jsf.bootstrap.tag.support} - Tag support classes</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.taginput} - Tag input components</li>
 *   <li>{@link de.cuioss.jsf.bootstrap.taglist} - Tag collection components</li>
 * </ul>
 * 
 * <h2>Usage Example</h2>
 * <pre>
 * &lt;boot:tag contentValue="Critical" state="DANGER" disposable="true" /&gt;
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.bootstrap.tag;
