/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.dev.ui.components;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

import de.cuioss.jsf.api.application.navigation.NavigationUtils;
import de.cuioss.jsf.api.components.base.BaseCuiNamingContainer;
import de.cuioss.portal.common.util.PortalResourceLoader;
import de.cuioss.tools.base.Preconditions;
import de.cuioss.tools.collect.CollectionBuilder;
import de.cuioss.tools.collect.CollectionLiterals;
import de.cuioss.tools.io.IOStreams;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.Splitter;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.StateHelper;
import lombok.Getter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.support.AbstractXMLOutputProcessor;
import org.jdom2.output.support.FormatStack;
import org.jdom2.output.support.XMLOutputProcessor;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.omnifaces.util.State;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import javax.xml.XMLConstants;

/**
 * <p>
 * A JSF component for displaying source code within documentation pages with proper
 * syntax highlighting. This component uses the Google Code Prettify library to
 * render formatted source code in various languages.
 * </p>
 *
 * <p>
 * The component offers three ways to specify the source code to be displayed:
 * </p>
 * <ol>
 * <li>Directly via the {@code source} attribute for inline code snippets</li>
 * <li>Loading from a file via the {@code sourcePath} attribute</li>
 * <li>Extracting from the current view via the {@code sourceContainerId} attribute</li>
 * </ol>
 *
 * <p>
 * The component will try to determine the appropriate syntax highlighting based on the file
 * extension (if using {@code sourcePath}), or you can explicitly set the language with
 * the {@code type} attribute.
 * </p>
 *
 * <p>
 * Sample usage in a Facelets page:
 * </p>
 *
 * <pre>
 * &lt;!-- Display code from an external file --&gt;
 * &lt;cui:sourceCode sourcePath="/META-INF/resources/examples/Button.xhtml"
 * description="Button Component Example"
 * type="lang-html" /&gt;
 *
 * &lt;!-- Display inline code --&gt;
 * &lt;cui:sourceCode source="public class HelloWorld {
 * public static void main(String[] args) {
 * System.out.println(\"Hello World\");
 * }
 * }" type="lang-java" /&gt;
 *
 * &lt;!-- Extract code from the current view --&gt;
 * &lt;cui:sourceCode sourceContainerId="myComponent" /&gt;
 * </pre>
 *
 * <p>
 * The component supports the following attributes:
 * </p>
 *
 * <ul>
 * <li><b>source</b>: Inline attribute for small amounts of source code. If set, takes precedence over other source attributes.</li>
 * <li><b>sourcePath</b>: Path to the source code file. Takes precedence over sourceContainerId. Can be absolute or relative.</li>
 * <li><b>sourceContainerId</b>: ID of a container on the current view containing source code to display.</li>
 * <li><b>enableClipboard</b>: Whether to display a copy-to-clipboard button. Defaults to true.</li>
 * <li><b>type</b>: The language type for syntax highlighting. Defaults to 'lang-html' if not specified.</li>
 * <li><b>description</b>: Optional description text to display with the source code.</li>
 * <li><b>maxLineLength</b>: Maximum number of characters per line. Defaults to 96.</li>
 * </ul>
 *
 * <p>
 * This component is not thread-safe as it uses JSF state management.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@ResourceDependency(library = "thirdparty.prettify", name = "prettify.css", target = "head")
@ResourceDependency(library = "thirdparty.prettify", name = "prettify.js", target = "head")
@ResourceDependency(library = "thirdparty.prettify", name = "lang-css.js", target = "head")
@ResourceDependency(library = "thirdparty.prettify", name = "lang-sql.js", target = "head")
@ResourceDependency(library = "thirdparty.prettify", name = "lang-yaml.js", target = "head")
@ResourceDependency(library = "thirdparty.prettify", name = "lang-properties.js", target = "head")
@ResourceDependency(library = "thirdparty.prettify", name = "run_prettify.js", target = "head")
@FacesComponent(SourceCodeComponent.COMPONENT_ID)
public class SourceCodeComponent extends BaseCuiNamingContainer {

    private static final CuiLogger log = new CuiLogger(SourceCodeComponent.class);

    /**
     * <p>
     * Enum representing supported syntax highlighting languages used with the
     * Google Code Prettify library.
     * </p>
     */
    protected enum LangStyle {

        LANG_HTML("lang-html"), LANG_JAVA("lang-java"), LANG_CSS("lang-css"), LANG_JS("lang-js"), LANG_SQL("lang-sql"),
        LANG_YAML("lang-yaml"), LANG_PROPERTIES("lang-properties");

        @Getter
        final String style;

        LangStyle(final String style) {
            this.style = style;
        }
    }

    /**
     * <p>
     * The component ID used for registration with the JSF framework.
     * </p>
     */
    public static final String COMPONENT_ID = "de.cuioss.jsf.dev.ui.components.SourceCode";

    /**
     * <p>
     * The renderer type ID used for lookup of the component renderer.
     * </p>
     */
    public static final String RENDERER_TYPE = "de.cuioss.jsf.dev.ui.renderer.SourceCodeComponentRenderer";

    /**
     * <p>
     * The component family identifier used for component type resolution.
     * </p>
     */
    public static final String COMPONENT_FAMILY_FIELD = "de.cuioss.jsf.dev.ui";

    private static final String META_INF_PREFIX = "META-INF/";

    /**
     * <p>
     * The key for the {@link StateHelper} used by {@link #getSource()}.
     * </p>
     */
    private static final String SOURCE_ATTRIBUTE_KEY = "source";

    /**
     * <p>
     * The key for the {@link StateHelper} used by {@link #getSourceContainerId()}.
     * </p>
     */
    private static final String SOURCE_CONTAINER_ID_ATTRIBUTE_KEY = "sourceContainerId";

    /**
     * <p>
     * The key for the {@link StateHelper} used by {@link #getSourcePath()}.
     * </p>
     */
    private static final String SOURCE_PATH_ATTRIBUTE_KEY = "sourcePath";

    /**
     * <p>
     * The key for the {@link StateHelper} used by {@link #isEnableClipboard()}.
     * </p>
     */
    private static final String ENABLE_CLIPBOARD_ATTRIBUTE_KEY = "enableClipboard";

    /**
     * <p>
     * The key for the {@link StateHelper} used by {@link #getDescription()}.
     * </p>
     */
    private static final String DESCRIPTION_ATTRIBUTE_KEY = "description";

    /**
     * <p>
     * The key for the {@link StateHelper} used by {@link #getType()}.
     * </p>
     */
    private static final String TYPE_ATTRIBUTE_KEY = "type";

    /**
     * <p>
     * The key for the {@link StateHelper} used by {@link #getMaxLineLength()}.
     * </p>
     */
    private static final String MAX_LINE_LENGTH_ATTRIBUTE_KEY = "maxLineLength";

    private static final Integer LINE_LENGTH_DEFAULT = 96;
    private static final int LINE_LENGTH_MINIMUM = 32;

    private final State state = new State(getStateHelper());

    /**
     * <p>
     * Custom XML output processor that suppresses namespace declarations.
     * Used for rendering XML/HTML content without namespace noise.
     * </p>
     */
    private static final XMLOutputProcessor NO_NAMESPACES = new AbstractXMLOutputProcessor() {

        @Override
        protected void printNamespace(final Writer out, final FormatStack fstack, final Namespace ns) {
            // NOOP by design
        }

    };

    /**
     * <p>
     * Default constructor. Sets the renderer type to {@link #RENDERER_TYPE}.
     * </p>
     */
    public SourceCodeComponent() {
        super.setRendererType(RENDERER_TYPE);
    }

    /**
     * <p>
     * Resolves and returns the source code to be displayed.
     * </p>
     *
     * <p>
     * The method tries three different ways to obtain the source code, in this order:
     * </p>
     * <ol>
     * <li>From the {@code source} attribute, if set</li>
     * <li>From the component with ID specified by {@code sourceContainerId}, if set</li>
     * <li>From the file specified by {@code sourcePath}, if set</li>
     * </ol>
     *
     * <p>
     * If none of these attributes are set, an error message is returned.
     * </p>
     *
     * @return The resolved source code as a String, or an error message if resolution fails
     */
    public String resolveSource() {
        if (!isEmpty(getSource())) {
            return getSource();
        }
        if (!isEmpty(getSourceContainerId())) {
            return resolveFromContainerId(getSourceContainerId());
        }
        if (!isEmpty(getSourcePath())) {
            return resolveFromSourcePath(getSourcePath());
        }
        return "You must configure at least one of the attributes 'source', 'sourcePath' or 'sourceContainerId'";
    }

    private String resolveFromSourcePath(final String sourcePath) {
        final var resolved = resolvePath(sourcePath);
        if (resolved.isEmpty()) {
            return "Unable lo load path from any of '%s', because the file can not be found or is not readable"
                    .formatted(determineViewRelativePath(sourcePath));
        }
        try {
            return IOStreams.toString(resolved.get().openStream(), StandardCharsets.UTF_8);
        } catch (final IOException e) {
            return "Unable lo load path '%s', due to '%s'".formatted(resolved, e.getMessage());
        }
    }

    /**
     * Checks whether it is a relative path or an absolute path. In case it starts
     * with '/' it is assumed that the requested resource can be found under
     * /META-INF
     *
     * @param path
     * @return
     */
    private Optional<URL> resolvePath(final String path) {
        if (!path.startsWith("/")) {
            for (var candidate : determineViewRelativePath(path)) {
                log.debug("Checking candidate '%s'", candidate);
                var found = PortalResourceLoader.getResource(candidate, getClass());
                if (found.isPresent()) {
                    log.debug("Found candidate '%s'", candidate);
                    return found;
                }
            }
            log.warn("%s", getClass().getResource(path));
            log.warn("No relative path found for '%s'", path);
            return Optional.empty();
        }
        log.debug("Assuming absolute path for '%s'", path);
        return Optional.ofNullable(getClass().getResource(path));
    }

    private Set<String> determineViewRelativePath(final String path) {
        final var requestPath = NavigationUtils.getCurrentView(getFacesContext()).getViewId();
        final var currentFolder = requestPath.substring(0, requestPath.lastIndexOf('/') + 1);
        var candidates = new CollectionBuilder<String>();
        log.debug("META-INF candidate (portal-default) for '%s'", path);
        candidates.add("/META-INF%s%s".formatted(currentFolder, path));
        log.debug("META-INF/resources candidate (myfaces/quarkus) for '%s'", path);
        candidates.add("/META-INF/resources%s%s".formatted(currentFolder, path));
        log.debug("direct candidate (not within META-INF) for '%s'", path);
        candidates.add("%s%s".formatted(currentFolder, path));
        return candidates.toImmutableSet();
    }

    @SuppressWarnings("java:S3655") // owolff: False positive ist present is checked
    private String resolveFromContainerId(final String sourceContainerId) {
        final var viewId = getFacesContext().getViewRoot().getViewId();
        final var loader = determineViewUrlResource();
        Preconditions.checkState(loader.isPresent(), "Unable to load '%s'", viewId);
        Document document;
        try (final var inputStream = loader.get().openStream()) {
            final var saxBuilder = new SAXBuilder();
            saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            saxBuilder.setExpandEntities(false);
            saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            document = saxBuilder.build(new InputSource(inputStream));
        } catch (final JDOMException | IOException e) {
            throw new IllegalStateException("Unable to parse file " + viewId, e);
        }
        final var root = document.getRootElement();
        final var query = "//*[@id='%s']".formatted(sourceContainerId);
        final XPathExpression<Element> xpath = XPathFactory.instance().compile(query, Filters.element());
        log.debug("Created xPathExpression: {}", xpath);
        final var filteredElements = xpath.evaluate(root);
        if (filteredElements.isEmpty()) {
            throw new IllegalStateException("Did not find any element with id=" + sourceContainerId);
        }
        if (filteredElements.size() > 1) {
            log.warn("More than one element found on view='{}' with id='{}' found, choosing the first one",
                    viewId, sourceContainerId);
        }

        final var outputter = new XMLOutputter();
        outputter.setXMLOutputProcessor(NO_NAMESPACES);
        outputter.setFormat(Format.getPrettyFormat());
        if (filteredElements.iterator().next().getChildren().isEmpty()) {
            return Joiner.on(System.lineSeparator())
                    .join(Splitter.on('\n').splitToList(filteredElements.iterator().next().getText()));
        }
        return outputter.outputString(filteredElements.iterator().next().getChildren());
    }

    private Optional<URL> determineViewUrlResource() {
        var viewId = getFacesContext().getViewRoot().getViewId();
        if (viewId.startsWith(("/"))) {
            viewId = viewId.substring(1);
        }
        List<String> candidates = CollectionLiterals.mutableList(META_INF_PREFIX + "resources/" + viewId, META_INF_PREFIX + viewId, viewId);
        for (var candidate : candidates) {
            var found = PortalResourceLoader.getResource(candidate, getClass());
            if (found.isPresent()) {
                return found;
            }
        }
        log.warn("No view found, candidates='{}", candidates);
        return Optional.empty();
    }

    /**
     * <p>
     * Returns the inline source code content.
     * </p>
     * 
     * <p>
     * This property allows directly specifying source code to be displayed without 
     * loading from an external file or referencing another component. If this attribute
     * is set, it takes precedence over {@code sourcePath} and {@code sourceContainerId}.
     * </p>
     * 
     * @return The inline source code string, or null if not set
     * @see #resolveSource()
     */
    public String getSource() {
        return state.get(SOURCE_ATTRIBUTE_KEY);
    }

    /**
     * <p>
     * Sets the inline source code content.
     * </p>
     * 
     * @param source The source code to display
     */
    public void setSource(final String source) {
        state.put(SOURCE_ATTRIBUTE_KEY, source);
    }

    /**
     * <p>
     * Returns the ID of the container in the current view containing source code to display.
     * </p>
     * 
     * <p>
     * This is the ID of an HTML/JSF element on the current page (not the clientId) whose
     * content should be extracted and displayed as source code. The component will use
     * XPath to find the element in the current view.
     * </p>
     * 
     * <p>
     * This attribute is used if {@code source} is not set, but takes lower precedence
     * than {@code sourcePath}.
     * </p>
     * 
     * @return The ID of the container element, or null if not set
     * @see #resolveSource()
     */
    public String getSourceContainerId() {
        return state.get(SOURCE_CONTAINER_ID_ATTRIBUTE_KEY);
    }

    /**
     * <p>
     * Sets the ID of the container in the current view containing source code to display.
     * </p>
     * 
     * @param sourceContainerId The ID of the container element
     */
    public void setSourceContainerId(final String sourceContainerId) {
        state.put(SOURCE_CONTAINER_ID_ATTRIBUTE_KEY, sourceContainerId);
    }

    /**
     * <p>
     * Returns the path to the source code file.
     * </p>
     * 
     * <p>
     * This can be either a fully qualified path starting with '/' (e.g.,
     * '/META-INF/pages/documentation/portal/portal_templates.xhtml') or a
     * relative path (e.g., 'portal_templates.xhtml'). Relative paths are
     * resolved relative to the current view's location.
     * </p>
     * 
     * <p>
     * This attribute is used if {@code source} is not set, and takes precedence
     * over {@code sourceContainerId}.
     * </p>
     * 
     * @return The path to the source code file, or null if not set
     * @see #resolveSource()
     */
    public String getSourcePath() {
        return state.get(SOURCE_PATH_ATTRIBUTE_KEY);
    }

    /**
     * <p>
     * Sets the path to the source code file.
     * </p>
     * 
     * @param sourcePath The path to the source code file
     */
    public void setSourcePath(final String sourcePath) {
        state.put(SOURCE_PATH_ATTRIBUTE_KEY, sourcePath);
    }

    /**
     * <p>
     * Sets whether to enable the copy-to-clipboard button.
     * </p>
     * 
     * @param enableClipboard {@code true} to enable the copy-to-clipboard button,
     *                        {@code false} to disable it
     */
    public void setEnableClipboard(final boolean enableClipboard) {
        state.put(ENABLE_CLIPBOARD_ATTRIBUTE_KEY, enableClipboard);
    }

    /**
     * <p>
     * Returns whether the copy-to-clipboard button is enabled.
     * </p>
     * 
     * <p>
     * When enabled, a button will be displayed that allows users to copy the
     * source code to their clipboard with a single click.
     * </p>
     * 
     * @return {@code true} if the copy-to-clipboard button is enabled (default),
     *         {@code false} otherwise
     */
    public boolean isEnableClipboard() {
        return state.get(ENABLE_CLIPBOARD_ATTRIBUTE_KEY, Boolean.TRUE);
    }

    /**
     * <p>
     * Sets the description text to display with the source code.
     * </p>
     * 
     * @param description The description text
     */
    public void setDescription(final String description) {
        state.put(DESCRIPTION_ATTRIBUTE_KEY, description);
    }

    /**
     * <p>
     * Returns the description text to display with the source code.
     * </p>
     * 
     * <p>
     * This is an optional text that can be used to describe or provide context for
     * the displayed source code.
     * </p>
     * 
     * @return The description text, or null if not set
     */
    public String getDescription() {
        return state.get(DESCRIPTION_ATTRIBUTE_KEY);
    }

    /**
     * <p>
     * Sets the maximum number of characters per line.
     * </p>
     * 
     * @param maxLineLength The maximum line length (must be at least 32)
     */
    public void setMaxLineLength(final Integer maxLineLength) {
        state.put(MAX_LINE_LENGTH_ATTRIBUTE_KEY, maxLineLength);
    }

    /**
     * <p>
     * Returns the maximum number of characters per line.
     * </p>
     * 
     * <p>
     * This property controls line wrapping for the displayed source code. The default
     * value is 96, and the minimum allowed value is 32.
     * </p>
     * 
     * @return The maximum line length (at least 32, defaults to 96)
     */
    public Integer getMaxLineLength() {
        final var actual = state.<Integer>get(MAX_LINE_LENGTH_ATTRIBUTE_KEY, LINE_LENGTH_DEFAULT);
        return Math.max(actual, LINE_LENGTH_MINIMUM);
    }

    /**
     * <p>
     * Sets the language type for syntax highlighting.
     * </p>
     * 
     * @param type The language type, one of the values defined in {@link LangStyle}
     *            (e.g., "lang-html", "lang-java")
     */
    public void setType(final String type) {
        state.put(TYPE_ATTRIBUTE_KEY, type);
    }

    /**
     * <p>
     * Returns the language type for syntax highlighting.
     * </p>
     * 
     * <p>
     * If not explicitly set, the component will try to determine the correct syntax
     * highlighting based on the file extension of the {@link #getSourcePath() sourcePath}
     * attribute. If the type cannot be determined, it defaults to "lang-html".
     * </p>
     * 
     * <p>
     * Supported types are:
     * </p>
     * <ul>
     *   <li>lang-html - For HTML/XML content</li>
     *   <li>lang-java - For Java source code</li>
     *   <li>lang-css - For CSS stylesheets</li>
     *   <li>lang-js - For JavaScript code</li>
     *   <li>lang-sql - For SQL queries</li>
     *   <li>lang-yaml - For YAML/YML files</li>
     *   <li>lang-properties - For Java properties files</li>
     * </ul>
     * 
     * @return The language type for syntax highlighting, never null
     */
    public String getType() {
        final var defaultValue = LangStyle.LANG_HTML.getStyle();
        var type = state.<String>get(TYPE_ATTRIBUTE_KEY);
        if (null == type) {
            if (null == getSourcePath()) {
                return defaultValue;
            }
            final var path = getSourcePath().toLowerCase(Locale.ENGLISH);
            final var extension = path.substring(0, path.lastIndexOf(".") + 1);
            return switch (extension) {
                case "properties" -> LangStyle.LANG_PROPERTIES.getStyle();
                case "yaml", "yml" -> LangStyle.LANG_YAML.getStyle();
                case "java" -> LangStyle.LANG_JAVA.getStyle();
                case "sql" -> LangStyle.LANG_SQL.getStyle();
                case "js" -> LangStyle.LANG_JS.getStyle();
                case "css" -> LangStyle.LANG_CSS.getStyle();
                default -> defaultValue;
            };
        }
        return type;
    }

    /**
     * <p>
     * Returns the component family of this component.
     * </p>
     * 
     * <p>
     * This method is required by the {@link jakarta.faces.component.UIComponent} contract
     * and is used by the JSF framework to categorize components.
     * </p>
     * 
     * @return The component family identifier {@link #COMPONENT_FAMILY_FIELD}
     */
    @Override
    public String getFamily() {
        return COMPONENT_FAMILY_FIELD;
    }
}
