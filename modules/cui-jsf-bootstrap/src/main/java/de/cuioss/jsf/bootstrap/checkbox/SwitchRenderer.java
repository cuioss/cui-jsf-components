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
package de.cuioss.jsf.bootstrap.checkbox;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.BaseDecoratorRenderer;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.jsf.api.components.util.ComponentWrapper;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.layout.ColumnComponent;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.FacesRenderer;

import java.io.IOException;

/**
 * <p>
 * Renderer implementation for the {@link SwitchComponent} that creates a Bootstrap-styled
 * toggle switch interface. This renderer transforms a standard boolean checkbox into a
 * sliding switch control with on/off text labels.
 * </p>
 * 
 * <h2>Rendered HTML Structure</h2>
 * <pre>
 * &lt;div id="componentId_container" class="[component-classes]" style="[component-style]"&gt;
 *   &lt;div class="col-md-[size] switch-placing"&gt;
 *     &lt;label class="switch" title="[title]"&gt;
 *       &lt;input type="checkbox" id="componentId" name="componentId" [standard-checkbox-attributes]/&gt;
 *       &lt;span class="slider round"&gt;&lt;/span&gt;
 *     &lt;/label&gt;
 *     &lt;span class="switch-text [hidden?]" data-item-active="true"&gt;ON Text&lt;/span&gt;
 *     &lt;span class="switch-text [hidden?]" data-item-active="false"&gt;OFF Text&lt;/span&gt;
 *   &lt;/div&gt;
 * &lt;/div&gt;
 * </pre>
 * 
 * <h2>Resource Dependencies</h2>
 * <p>
 * This renderer includes a JavaScript resource dependency on "enabler.switch.js" which
 * provides the client-side behavior for the switch component.
 * </p>
 * 
 * <h2>CSS Classes</h2>
 * <ul>
 *   <li>"switch-placing" - Applied to the column container</li>
 *   <li>"switch" - Applied to the label element that wraps the checkbox</li>
 *   <li>"slider round" - Applied to the visual sliding element</li>
 *   <li>"switch-text" - Applied to the text labels for on/off states</li>
 * </ul>
 * 
 * <p>
 * The renderer automatically handles AJAX updates by redirecting render targets from the
 * checkbox itself to its container element, ensuring the entire switch is updated correctly.
 * </p>
 * 
 * @author Oliver Wolff
 * @author Sven Haag
 * @see SwitchComponent
 */
@FacesRenderer(componentFamily = BootstrapFamily.COMPONENT_FAMILY, rendererType = BootstrapFamily.SWITCH_RENDERER)
@ResourceDependency(library = "javascript.enabler", name = "enabler.switch.js", target = "head")
public class SwitchRenderer extends BaseDecoratorRenderer<SwitchComponent> {

    private static final String SWITCH_PLACING_STYLE_CLASS = "switch-placing";
    private static final String SWITCH_STYLE_CLASS = "switch";
    private static final String SLIDER_STYLE_CLASS = "slider round";
    private static final String TEXT_STYLE_CLASS = "switch-text";

    /**
     *
     */
    public SwitchRenderer() {
        super(false);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<SwitchComponent> writer,
            final SwitchComponent component) throws IOException {
        renderContainerBegin(context, writer, component);
        renderColumnBegin(context, component);
        renderLabelBegin(writer, component);

        JsfHtmlComponent.CHECKBOX.renderer(context).encodeBegin(context, component);
        JsfHtmlComponent.CHECKBOX.renderer(context).encodeChildren(context, component);
        JsfHtmlComponent.CHECKBOX.renderer(context).encodeEnd(context, component);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<SwitchComponent> writer,
            final SwitchComponent component) throws IOException {
        renderSlider(writer);
        renderLabelEnd(writer);
        renderText(writer, component);
        renderColumnEnd(context);
        renderContainerEnd(writer);
    }

    @Override
    protected void doDecode(final FacesContext context, final ComponentWrapper<SwitchComponent> componentWrapper) {
        JsfHtmlComponent.CHECKBOX.renderer(context).decode(context, componentWrapper.getWrapped());
    }

    @Override
    protected void decodeClientBehavior(final FacesContext context,
            final ComponentWrapper<SwitchComponent> componentWrapper) {
        // Decoding must take place only in JsfHtmlComponent.CHECKBOX. Otherwise, it would be redundant.
    }

    private void renderContainerBegin(final FacesContext context,
            final DecoratingResponseWriter<SwitchComponent> writer, final SwitchComponent component)
            throws IOException {
        writer.withStartElement(Node.DIV);
        writer.withClientId(SwitchComponent.CONTAINER_SUFFIX);
        writer.withStyleClass(component.getStyleClassBuilder());
        writer.withAttributeStyle(component.resolveStyle());
        writer.withPassThroughAttributes(context, component.resolvePassThroughAttributes());
    }

    private void renderColumnBegin(final FacesContext context, final SwitchComponent component) throws IOException {
        var col = ColumnComponent.createComponent(context);
        col.setSize(component.getSize());
        col.setOffsetSize(component.getOffsetSize());
        col.setStyleClass(SWITCH_PLACING_STYLE_CLASS);
        col.encodeBegin(context);
    }

    private void renderLabelBegin(final DecoratingResponseWriter<SwitchComponent> writer,
            final SwitchComponent component) throws IOException {
        writer.withStartElement(Node.LABEL);
        writer.withStyleClass(SWITCH_STYLE_CLASS);
        writer.withAttribute(AttributeName.TITLE, component.resolveTitle());
    }

    private void renderSlider(final DecoratingResponseWriter<SwitchComponent> writer) throws IOException {
        writer.withStartElement(Node.SPAN);
        writer.withStyleClass(SLIDER_STYLE_CLASS);
        writer.withEndElement(Node.SPAN);
    }

    private void renderLabelEnd(final DecoratingResponseWriter<SwitchComponent> writer) throws IOException {
        writer.withEndElement(Node.LABEL);
    }

    private void renderText(final DecoratingResponseWriter<SwitchComponent> writer, final SwitchComponent component)
            throws IOException {
        writer.withStartElement(Node.SPAN);
        writer.withAttribute(AttributeName.CLASS, getTextStyleClass(!component.isSelected()));
        writer.withAttribute(AttributeName.DATA_ITEM_ACTIVE, "true");
        writer.withTextContent(component.resolveOnText(), true);
        writer.withEndElement(Node.SPAN);

        writer.withStartElement(Node.SPAN);
        writer.withAttribute(AttributeName.CLASS, getTextStyleClass(component.isSelected()));
        writer.withAttribute(AttributeName.DATA_ITEM_ACTIVE, "false");
        writer.withTextContent(component.resolveOffText(), true);
        writer.withEndElement(Node.SPAN);
    }

    private String getTextStyleClass(final boolean hide) {
        final StyleClassBuilder builder = new StyleClassBuilderImpl(TEXT_STYLE_CLASS);
        if (hide) {
            builder.append(CssBootstrap.HIDDEN);
        }
        return builder.getStyleClass();
    }

    private void renderColumnEnd(final FacesContext context) throws IOException {
        ColumnComponent.createComponent(context).encodeEnd(context);
    }

    private void renderContainerEnd(final DecoratingResponseWriter<SwitchComponent> writer) throws IOException {
        writer.withEndElement(Node.DIV);
    }
}
