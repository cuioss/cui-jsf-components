/**
 * <p>
 * This package contains the core classes for JSF tag library metadata extraction and representation
 * in the CUI JSF component development framework. It provides the foundation for component
 * documentation, development tools, and metadata inspection.
 * </p>
 * 
 * <p>
 * The metadata classes in this package enable developers to:
 * </p>
 * <ul>
 *   <li>Parse JSF tag libraries from XML files</li>
 *   <li>Access metadata about components, converters, validators, and behaviors</li>
 *   <li>Inspect component attributes, types, and documentation</li>
 *   <li>Generate documentation and development tools for JSF components</li>
 * </ul>
 * 
 * <h2>Key Classes</h2>
 * 
 * <h3>TagLib</h3>
 * <p>
 * The core class for parsing and extracting metadata from JSF taglib XML files. It provides
 * methods to access component, converter, validator, and behavior metadata from a specific
 * tag library file.
 * </p>
 * <pre>{@code
 * // Load a JSF 4.0 taglib
 * TagLib taglib = new TagLib("/META-INF/my-taglib.xml",
 *                         TagLib.JSF_4_0_FACELET_TAGLIB_NAMESPACE);
 * 
 * // Access metadata
 * List<UIComponentMetadata> components = taglib.getComponentMetadata().getCollected();
 * }</pre>
 * 
 * <h3>LibraryTagLib</h3>
 * <p>
 * An enum providing predefined constants for commonly used JSF tag libraries. Each constant
 * encapsulates the path and namespace for a specific tag library, simplifying the process
 * of loading tag libraries.
 * </p>
 * <pre>{@code
 * // Load the CUI Bootstrap taglib
 * TagLib bootstrapTagLib = LibraryTagLib.CUI_BOOTSTRAP.load();
 * 
 * // Access components from the taglib
 * List<UIComponentMetadata> components = bootstrapTagLib.getComponentMetadata().getCollected();
 * }</pre>
 * 
 * <h2>Related Packages</h2>
 * <p>
 * This core metadata package is used by and provides foundation for:
 * </p>
 * <ul>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite} - Composite component metadata</li>
 *   <li>{@link de.cuioss.jsf.dev.metadata.composite.attributes} - Component attribute wrappers</li>
 *   <li>{@link de.cuioss.jsf.dev.metadata.model} - Metadata model classes</li>
 *   <li>{@link de.cuioss.jsf.dev.ui.components} - UI components for displaying metadata</li>
 * </ul>
 * 
 * <h2>Architecture</h2>
 * <p>
 * The metadata package follows a hierarchical structure:
 * </p>
 * <ul>
 *   <li>TagLib parses XML files and extracts raw metadata</li>
 *   <li>TagStorage classes store collections of metadata objects by type</li>
 *   <li>Model classes (UIComponentMetadata, ConverterMetadata, etc.) represent specific metadata types</li>
 *   <li>LibraryTagLib provides convenient access to common tag libraries</li>
 * </ul>
 * 
 * <p>
 * This structure allows for efficient metadata extraction, storage, and retrieval for
 * development tools and documentation generators.
 * </p>
 *
 * @since 1.0
 */
package de.cuioss.jsf.dev.metadata;
