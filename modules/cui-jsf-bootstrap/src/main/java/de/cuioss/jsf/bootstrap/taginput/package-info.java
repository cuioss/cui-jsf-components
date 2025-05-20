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
