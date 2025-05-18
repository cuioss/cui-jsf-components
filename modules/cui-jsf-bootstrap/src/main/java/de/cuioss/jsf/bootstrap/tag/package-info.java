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
 * &lt;cui:tag contentValue="Critical" state="DANGER" disposable="true" /&gt;
 * </pre>
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.jsf.bootstrap.tag;
