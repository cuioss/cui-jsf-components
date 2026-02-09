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
package de.cuioss.jsf.bootstrap.lazyloading;

import static de.cuioss.jsf.bootstrap.lazyloading.LazyLoadingComponent.*;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.notification.NotificationBoxComponent;
import de.cuioss.tools.logging.CuiLogger;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * Renderer for {@link LazyLoadingComponent} that handles component lifecycle
 * and produces the HTML structure based on the component's state.
 *
 * <h3>Rendering States</h3>
 * <ul>
 *   <li>Initial: Shows waiting indicator with JavaScript to trigger loading</li>
 *   <li>Loading: Handles AJAX updates and processes content loading</li>
 *   <li>Error: Displays notification messages</li>
 *   <li>Complete: Renders child components according to configuration</li>
 * </ul>
 *
 * <h3>Generated Structure</h3>
 * <pre>
 * &lt;div class="cui-lazy-loading" data-async="..." data-initialized="..."&gt;
 *   &lt;!-- Optional notification box for messages --&gt;
 *   &lt;!-- Optional waiting indicator during loading --&gt;
 *   &lt;!-- Content area for lazy-loaded components --&gt;
 * &lt;/div&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see LazyLoadingComponent
 */
@FacesRenderer(rendererType = BootstrapFamily.LAZYLOADING_RENDERER, componentFamily = BootstrapFamily.COMPONENT_FAMILY)
public class LazyLoadingRenderer extends BaseDecoratorRenderer<LazyLoadingComponent> {

    private static final CuiLogger LOGGER = new CuiLogger(LazyLoadingRenderer.class);
    static final String DATA_LAZY_LOADING_CONTENT = "data-lazy_loading-content";

    private static final String LAZY_LOADING_CONTENT_ID = "content";

    private static final String DATA_IGNORE_AUTO_UPDATE = "data-ignore-auto-update";

    private static final String DATA_ASYNC = "data-async";

    static final String DATA_WAITING_INDICATOR_ID = "data-lazy_loading-waiting-indicator-id";

    public LazyLoadingRenderer() {
        super(true);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context,
            final DecoratingResponseWriter<LazyLoadingComponent> writer, final LazyLoadingComponent component)
            throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withClientId();
        if (!component.shouldRenderWaitingIndicator(context)) {
            writer.withAttribute(AttributeName.DATA_CONTENT_LOADED, "true");
        }
        writer.writeAttribute(DATA_IGNORE_AUTO_UPDATE, component.isIgnoreAutoUpdate(), DATA_IGNORE_AUTO_UPDATE);
        writer.writeAttribute(DATA_ASYNC, component.isAsync(), DATA_ASYNC);
        var waitingIndicatorComponentResult = component.retrieveWaitingIndicator();
        if (waitingIndicatorComponentResult.isEmpty()) {
            throw new IllegalStateException("Waiting indicator not found!");
        }
        writer.writeAttribute(DATA_WAITING_INDICATOR_ID, waitingIndicatorComponentResult.get().getClientId(),
                DATA_WAITING_INDICATOR_ID);
        writer.withStyleClass(CssCuiBootstrap.CUI_LAZY_LOADING.getStyleClassBuilder().append(component.getStyleClass())
                .append(CssCuiBootstrap.UI_HIDDEN_CONTAINER));
        writer.withAttributeStyle(component.getStyle());
        writer.withPassThroughAttributes();
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<LazyLoadingComponent> writer,
            final LazyLoadingComponent component) throws IOException {
        writer.withEndElement(Node.DIV);
    }

    @SuppressWarnings("java:S3776")
    // Complexity from debug logging in JSF renderer
    @Override
    protected void doEncodeChildren(final FacesContext context,
            final DecoratingResponseWriter<LazyLoadingComponent> writer, final LazyLoadingComponent component)
            throws IOException {

        var waitingIndicatorComponentResult = component.retrieveWaitingIndicator();
        if (waitingIndicatorComponentResult.isEmpty()) {
            throw new IllegalStateException("Waiting indicator not found!");
        }
        var waitingIndicatorComponent = waitingIndicatorComponentResult.get();
        waitingIndicatorComponent.getAttributes().put("style",
                "display: " + (component.shouldRenderWaitingIndicator(context) ? "block;" : "none;"));

        if (!component.shouldRenderWaitingIndicator(context)) { // render all children including
            // waiting indicator
            component.setChildrenLoaded(true);
            writer.withStartElement(Node.DIV);
            writer.withClientId(LAZY_LOADING_CONTENT_ID);
            writer.writeAttribute(DATA_LAZY_LOADING_CONTENT, DATA_LAZY_LOADING_CONTENT, DATA_LAZY_LOADING_CONTENT);
            var resultNotificationBoxComponent = (NotificationBoxComponent) component.retrieveNotificationBox()
                    .orElseThrow(IllegalStateException::new);
            if (null != component.evaluateNotificationBoxValue()) {
                resultNotificationBoxComponent.setState(component.evaluateNotificationBoxState().name());
                resultNotificationBoxComponent.setContentValue(component.evaluateNotificationBoxValue());
                resultNotificationBoxComponent.setRendered(true);
            } else {
                resultNotificationBoxComponent.setRendered(false);
            }

            resultNotificationBoxComponent.encodeAll(context);
            if (component.evaluateRenderContent()) {
                for (final UIComponent child : component.getChildren()) {
                    if (child.isRendered()
                            && !child.getPassThroughAttributes().containsKey(DATA_RESULT_NOTIFICATION_BOX)
                            && !WAITING_INDICATOR_ID.equals(child.getId())) {
                        child.encodeAll(context);
                    }
                }
            }
            writer.withEndElement(Node.DIV);
        }
        waitingIndicatorComponent.encodeAll(context);
    }

    /**
     * Update loaded state from request.
     */
    @Override
    protected void doDecode(final FacesContext context, final ComponentWrapper<LazyLoadingComponent> componentWrapper) {
        final var component = componentWrapper.getWrapped();
        LOGGER.debug("Decoding lazy loading component for %s", component);

        final var map = context.getExternalContext().getRequestParameterMap();
        final var isLoadedValue = map.get(componentWrapper.getSuffixedClientId(ID_SUFFIX_IS_LOADED));
        LOGGER.debug("isLoadedValue: %s", isLoadedValue);
        if (isLoadedValue != null) {
            var loadedValue = Boolean.parseBoolean(isLoadedValue);
            if (loadedValue && !component.getChildrenLoaded()) {
                component.setChildrenLoaded(true);
                LOGGER.debug("Queuing ActionEvent for %s", component);
                component.queueEvent(new ActionEvent(component));
            }
        }
    }

}
