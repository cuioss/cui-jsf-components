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
 * This package contains utility classes that support the display and extraction of 
 * JSF composite component metadata and sample code in the CUI JSF component framework.
 * </p>
 * 
 * <p>
 * The utilities in this package provide essential support for the developer tools and 
 * documentation features of the CUI JSF component library. They are primarily designed to:
 * </p>
 * <ul>
 *   <li>Display metadata about component attributes, facets, and properties</li>
 *   <li>Extract sample code from XHTML files for documentation and demonstration</li>
 *   <li>Support a standardized format for displaying label-value pairs of component information</li>
 * </ul>
 * 
 * <h2>Key Classes</h2>
 * 
 * <h3>LabelValueDisplay</h3>
 * <p>
 * A simple data structure for representing label-value pairs in component metadata displays.
 * This class is used throughout the metadata display system to consistently represent 
 * properties, attributes, and configuration options in a user-friendly format.
 * </p>
 * <pre>{@code
 * // Example usage:
 * LabelValueDisplay nameDisplay = new LabelValueDisplay("component.name.label", "Button");
 * }</pre>
 * 
 * <h3>SampleSourceFinder</h3>
 * <p>
 * A utility for extracting sample source code from XHTML files containing component demos.
 * This class parses XHTML files and extracts code snippets from facets named "sample", 
 * making it possible to show developers the exact code needed to implement a component.
 * </p>
 * <pre>{@code
 * // Example usage:
 * File xhtmlFile = new File("/path/to/demo-page.xhtml");
 * SampleSourceFinder finder = new SampleSourceFinder(xhtmlFile, "buttonDemo");
 * String sourceCode = finder.getSampleSource();
 * }</pre>
 * 
 * <h2>Related Packages</h2>
 * <p>
 * This utility package is used by the following related packages:
 * </p>
 * <ul>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite} - Core composite component metadata</li>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite.attributes} - Component attribute metadata</li>
 *   <li>{@link de.cuioss.jsf.dev.ui.components} - UI components for displaying metadata</li>
 * </ul>
 * 
 * <h2>Integration with CUI JSF Components</h2>
 * <p>
 * These utilities support the developer tools and documentation features of the CUI JSF component
 * framework. They are primarily used in the metadata display pages for components, allowing
 * developers to easily understand component structure, requirements, and implementation patterns.
 * </p>
 *
 * @since 1.0
 */
package de.cuioss.jsf.dev.metadata.composite.util;
