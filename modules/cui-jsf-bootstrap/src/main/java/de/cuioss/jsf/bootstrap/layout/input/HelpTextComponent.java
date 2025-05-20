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
package de.cuioss.jsf.bootstrap.layout.input;

import de.cuioss.jsf.api.components.JsfComponentIdentifier;
import de.cuioss.jsf.api.components.base.BaseCuiHtmlHiddenInputComponent;
import de.cuioss.jsf.api.components.base.BaseCuiPanel;
import de.cuioss.jsf.api.components.partial.ContentProvider;
import de.cuioss.jsf.api.components.partial.TitleProvider;
import de.cuioss.jsf.api.components.partial.TitleProviderImpl;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.bootstrap.CssBootstrap;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.button.Button;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.tools.string.MoreStrings;
import jakarta.faces.application.ResourceDependency;
import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Provides contextual help functionality for form inputs within {@link LabeledContainerComponent}.
 * This component adds a help icon next to a form field that, when clicked, reveals
 * additional explanatory content below the field.
 * </p>
 * <p>
 * The component implements {@link ContainerPlugin}, allowing it to seamlessly integrate
 * with labeled containers while maintaining proper positioning and styling.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 * <li>Progressive disclosure of help content - initially hidden until needed</li>
 * <li>Supports both static text and complex content via child components</li>
 * <li>Configurable button position (append/prepend)</li>
 * <li>Preserves form layout integrity</li>
 * </ul>
 *
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider} - Tooltip for the help icon</li>
 * <li>{@link ContentProvider} - Simple text content (alternative to child components)</li>
 * <li>buttonAlign - Position of the help icon ('append'/'prepend', defaults to 'prepend')</li>
 * <li>renderButton - Whether to display the help button/icon (default: false)</li>
 * </ul>
 * 
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;boot:labeledContainer label="Field Name"&gt;
 *   &lt;h:inputText id="input" /&gt;
 *   &lt;boot:helpText contentValue="This is help text" titleValue="Help Information" renderButton="true" /&gt;
 * &lt;/boot:labeledContainer&gt;
 * 
 * &lt;!-- With complex content --&gt;
 * &lt;boot:labeledContainer label="Field Name"&gt;
 *   &lt;h:inputText id="input" /&gt;
 *   &lt;boot:helpText renderButton="true"&gt;
 *     &lt;h:panelGroup&gt;
 *       &lt;h5&gt;Important Information&lt;/h5&gt;
 *       &lt;p&gt;Detailed explanation...&lt;/p&gt;
 *     &lt;/h:panelGroup&gt;
 *   &lt;/boot:helpText&gt;
 * &lt;/boot:labeledContainer&gt;
 * </pre>
 *
 * @author Matthias Walliczek
 */
@ResourceDependency(library = "javascript.enabler", name = "enabler.help_text.js", target = "head")
@FacesComponent(BootstrapFamily.HELP_TEXT_COMPONENT)
@SuppressWarnings("java:S110") // owolff: artifact of jsf structure
public class HelpTextComponent extends BaseCuiHtmlHiddenInputComponent implements ContainerPlugin {

    private static final CuiLogger log = new CuiLogger(HelpTextComponent.class);

    /**
     * "data-help-input-button".
     */
    static final String DATA_HELP_BUTTON = "data-help-input-button";

    static final String DATA_HELP_BLOCK = "data-help-block";

    private static final String BUTTON_ALIGN_KEY = "buttonAlign";

    private static final String RENDER_BUTTON_KEY = "renderButton";

    /**
     * The fixed id for this component.
     */
    static final String FIXED_ID = "help_text";

    private static final String CHILDREN_KEY = "children";

    private final CuiState state;

    @Delegate
    private final TitleProvider titleProvider;

    @Delegate
    private final ContentProvider contentProvider;

    /**
     * Constructor.
     */
    public HelpTextComponent() {
        state = new CuiState(getStateHelper());
        titleProvider = new TitleProviderImpl(this);
        contentProvider = new ContentProvider(this);
    }

    @Override
    public void postAddToView(LabeledContainerComponent parent) {
        var children = getChildren();
        if (!children.isEmpty()) {
            state.put(CHILDREN_KEY, new ArrayList<>(children));
        }
    }

    @Override
    public Optional<UIComponent> provideFacetContent(ContainerFacets facet) {
        if (!isRendered()) {
            return Optional.empty();
        }
        var align = ContainerFacets.parseButtonAlign(getButtonAlign(), BUTTON_ALIGN_KEY);
        if (align == facet && getRenderButton()) {
            var button = Button.create(getFacesContext());
            button.setOnclick(null);
            button.setIcon("cui-icon-circle_question_mark");
            button.setStyleClass(CssCuiBootstrap.INPUT_HELP_TEXT_ACTION.getStyleClass());
            button.setTitleValue(titleProvider.resolveTitle());
            button.getPassThroughAttributes().put(DATA_HELP_BUTTON, DATA_HELP_BUTTON);
            return Optional.of(button);
        }
        if (ContainerFacets.HELP_TEXT == facet) {
            final var helpTextBlock = new BaseCuiPanel();
            if (getRenderButton()) {
                helpTextBlock.setStyle("display: none;");
            }
            helpTextBlock.setStyleClass(CssBootstrap.CUI_ADDITIONAL_MESSAGE.getStyleClass());
            helpTextBlock.getPassThroughAttributes().put(DATA_HELP_BLOCK, DATA_HELP_BLOCK);

            if (getChildCount() > 0) {
                List<UIComponent> children = new ArrayList<>(getChildren());
                children.forEach(child -> helpTextBlock.getChildren().add(child));
            } else if (null != state.get(CHILDREN_KEY)) {
                List<UIComponent> children = state.get(CHILDREN_KEY);
                children.forEach(child -> helpTextBlock.getChildren().add(child));
            } else if (!MoreStrings.isEmpty(contentProvider.resolveContent())) {
                var outputText = new HtmlOutputText();
                outputText.setValue(contentProvider.resolveContent());
                outputText.setEscape(contentProvider.getContentEscape());
                helpTextBlock.getChildren().add(outputText);
            } else {
                log.info("Portal-012: Neither children or a content is defined to be displayed as help text.");
            }
            return Optional.of(helpTextBlock);
        }
        return Optional.empty();
    }

    @Override
    public String getId() {
        return FIXED_ID;
    }

    @Override
    public String getFamily() {
        return JsfComponentIdentifier.INPUT_FAMILY;
    }

    /**
     * @return the buttonAlign, defaults to {@code ContainerFacets#PREPEND }
     */
    public String getButtonAlign() {
        return state.get(BUTTON_ALIGN_KEY, ContainerFacets.PREPEND.getName());
    }

    /**
     * @param buttonAlign to be set, expected is one of
     *                    {@code ContainerFacets#APPEND} or
     *                    {@code ContainerFacets#PREPEND}
     */
    public void setButtonAlign(String buttonAlign) {
        state.put(BUTTON_ALIGN_KEY, buttonAlign);
    }

    public boolean getRenderButton() {
        return state.get(RENDER_BUTTON_KEY, false);
    }

    public void setRenderButton(boolean renderButton) {
        state.put(RENDER_BUTTON_KEY, renderButton);
    }

    @Override
    public void encodeChildren(FacesContext context) {
        // do nothing
    }

    @Override
    public void encodeBegin(FacesContext context) {
        // do nothing
    }

    @Override
    public void encodeEnd(FacesContext context) {
        // do nothing
    }
}
