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

import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.StateHelper;
import javax.xml.XMLConstants;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static de.cuioss.tools.string.MoreStrings.isEmpty;

/**
 * <h2>Summary</h2>
 * <p>
 * This class manages the state and resolving of the source code to be displayed
 * within documentation
 * </p>
 * <h2>source</h2>
 * <p>
 * Inline attribute to be used for small amounts of Source code. If it is set it
 * takes precedence over the other attributes for source.
 * </p>
 * <h2>sourcePath</h2>
 * <p>
 * The path to the source code. It takes precedence over #sourceContainerId. It
 * can be either a fully qualified path, like
 * '/META-INF/pages/documentation/portal/portal_templates.xhtml' or a relative
 * path like 'portal_templates.xhtml'. The implementation checks for '/' in
 * order to decide whether it is an relative path or not. All relative addressed
 * files are assumed to be found under '/META-INF/'.
 * </p>
 * <h2>sourceContainerId</h2>
 * <p>
 * The id of the container wrapping the source to be displayed. It is assumed
 * that it resides on the current view. <em>Caution:</em> It is not the clientId
 * but the actual id-Attribute on the view
 * </p>
 * <h2>enableClipboard</h2>
 * <p>
 * Indicating whether to display copy to clipboard button. Defaults to
 * {@code true}
 * </p>
 * <h2>type</h2>
 * <p>
 * The type of the source code, needed for proper formatting. Defaults to
 * 'lang-html'. Must be one of 'lang-html', 'lang-java', 'lang-sql', 'lang-js',
 * 'lang-css', 'lang-yaml', 'lang-properties'.
 * </p>
 * <h2>description</h2>
 * <p>
 * The (optional) description for the sourceCode to be displayed.
 * </p>
 * <h2>maxLineLength</h2>
 * <p>
 * The maximum number of characters to be displayed in a line. Defaults to '96',
 * minimum is '32' .
 * </p>
 *
 * @author Oliver Wolff
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
     * The component Id.
     */
    public static final String COMPONENT_ID = "de.cuioss.jsf.dev.ui.components.SourceCode";

    /**
     * Renderer type.
     */
    public static final String RENDERER_TYPE = "de.cuioss.jsf.dev.ui.renderer.SourceCodeComponentRenderer";

    /**
     * The component family.
     */
    public static final String COMPONENT_FAMILY_FIELD = "de.cuioss.jsf.dev.ui";

    private static final String META_INF_PREFIX = "META-INF/";

    /**
     * The key for the {@link StateHelper} used by {@link #getSource()}
     */
    private static final String SOURCE_ATTRIBUTE_KEY = "source";

    /**
     * The key for the {@link StateHelper} used by {@link #getSourceContainerId()}
     */
    private static final String SOURCE_CONTAINER_ID_ATTRIBUTE_KEY = "sourceContainerId";

    /**
     * The key for the {@link StateHelper} used by {@link #getSourcePath()}
     */
    private static final String SOURCE_PATH_ATTRIBUTE_KEY = "sourcePath";

    /**
     * The key for the {@link StateHelper} used by {@link #isEnableClipboard()}
     */
    private static final String ENABLE_CLIPBOARD_ATTRIBUTE_KEY = "enableClipboard";

    /**
     * The key for the {@link StateHelper} used by {@link #getDescription()}
     */
    private static final String DESCRIPTION_ATTRIBUTE_KEY = "description";

    /**
     * The key for the {@link StateHelper} used by {@link #getType()}
     */
    private static final String TYPE_ATTRIBUTE_KEY = "type";

    /**
     * The key for the {@link StateHelper} used by {@link #getMaxLineLength()}
     */
    private static final String MAX_LINE_LENGTH_ATTRIBUTE_KEY = "maxLineLength";

    private static final Integer LINE_LENGTH_DEFAULT = 96;
    private static final int LINE_LENGTH_MINIMUM = 32;

    private final State state = new State(getStateHelper());

    private static final XMLOutputProcessor NO_NAMESPACES = new AbstractXMLOutputProcessor() {

        @Override
        protected void printNamespace(final Writer out, final FormatStack fstack, final Namespace ns) {
            // NOOP by design
        }

    };

    /**
     *
     */
    public SourceCodeComponent() {
        super.setRendererType(RENDERER_TYPE);
    }

    /**
     * @return the resolved SourceCodeComponent
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
                var found = PortalResourceLoader.getRessource(candidate, getClass());
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
            var found = PortalResourceLoader.getRessource(candidate, getClass());
            if (found.isPresent()) {
                return found;
            }
        }
        log.warn("No view found, candidates='{}", candidates);
        return Optional.empty();
    }

    /**
     * @return the source
     */
    public String getSource() {
        return state.get(SOURCE_ATTRIBUTE_KEY);
    }

    /**
     * @param source
     */
    public void setSource(final String source) {
        state.put(SOURCE_ATTRIBUTE_KEY, source);
    }

    /**
     * @return the sourceContainerId
     */
    public String getSourceContainerId() {
        return state.get(SOURCE_CONTAINER_ID_ATTRIBUTE_KEY);
    }

    /**
     * @param sourceContainerId
     */
    public void setSourceContainerId(final String sourceContainerId) {
        state.put(SOURCE_CONTAINER_ID_ATTRIBUTE_KEY, sourceContainerId);
    }

    /**
     * @return sourcePath
     */
    public String getSourcePath() {
        return state.get(SOURCE_PATH_ATTRIBUTE_KEY);
    }

    /**
     * @param sourcePath
     */
    public void setSourcePath(final String sourcePath) {
        state.put(SOURCE_PATH_ATTRIBUTE_KEY, sourcePath);
    }

    /**
     * @param enableClipboard
     */
    public void setEnableClipboard(final boolean enableClipboard) {
        state.put(ENABLE_CLIPBOARD_ATTRIBUTE_KEY, enableClipboard);
    }

    /**
     * @return enableClipboard
     */
    public boolean isEnableClipboard() {
        return (Boolean) state.get(ENABLE_CLIPBOARD_ATTRIBUTE_KEY, Boolean.TRUE);
    }

    /**
     * @param description to be set
     */
    public void setDescription(final String description) {
        state.put(DESCRIPTION_ATTRIBUTE_KEY, description);
    }

    /**
     * @return description
     */
    public String getDescription() {
        return state.get(DESCRIPTION_ATTRIBUTE_KEY);
    }

    /**
     * @param maxLineLength to be set
     */
    public void setMaxLineLength(final Integer maxLineLength) {
        state.put(MAX_LINE_LENGTH_ATTRIBUTE_KEY, maxLineLength);
    }

    /**
     * @return maxLineLength to nre retrieved
     */
    public Integer getMaxLineLength() {
        final var actual = state.<Integer>get(MAX_LINE_LENGTH_ATTRIBUTE_KEY, LINE_LENGTH_DEFAULT);
        return Math.max(actual, LINE_LENGTH_MINIMUM);
    }

    /**
     * @param type to be set
     */
    public void setType(final String type) {
        state.put(TYPE_ATTRIBUTE_KEY, type);
    }

    /**
     * @return type being revoled depending on the file-extension
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

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY_FIELD;
    }
}
