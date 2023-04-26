package de.cuioss.jsf.bootstrap.tag;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.jdom2.Element;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.CoreJsfTestConfiguration;
import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.css.ContextState;
import de.cuioss.jsf.api.components.css.StyleClassBuilder;
import de.cuioss.jsf.api.components.css.impl.StyleClassBuilderImpl;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.bootstrap.CssCuiBootstrap;
import de.cuioss.jsf.bootstrap.button.CloseCommandButton;
import de.cuioss.jsf.bootstrap.button.CloseCommandButtonRenderer;
import de.cuioss.jsf.bootstrap.tag.support.TagSize;
import de.cuioss.jsf.bootstrap.tag.support.TagState;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.tools.string.Joiner;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class TagRendererTest extends AbstractComponentRendererTest<TagRenderer> implements ComponentConfigurator {

    private static final String SOME_CONTENT_VALUE = "some.content.value";

    private static final String LABEL_PRIMARY_STYLECLASS = "cui-tag cui-tag-default";

    private static final String ESCAPE_CONTENT = "<>";

    private static final String SOME_KEY = "some.key";

    @Test
    void shouldRenderMinimal() {
        final var component = new TagComponent();
        component.setContentKey(SOME_KEY);
        final var expected =
            new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(LABEL_PRIMARY_STYLECLASS).withTextContent(SOME_KEY);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderTitleFromBundle() {
        final var component = new TagComponent();
        component.setContentValue(SOME_CONTENT_VALUE);
        component.setTitleKey(SOME_KEY);
        final var expected =
            new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(LABEL_PRIMARY_STYLECLASS)
                    .withAttribute(AttributeName.TITLE, SOME_KEY).withTextContent(SOME_CONTENT_VALUE);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderContentUnEscaped() {
        final var component = new TagComponent();
        component.setContentValue(ESCAPE_CONTENT);
        component.setContentEscape(false);
        assertEquals("<div class=\"cui-tag cui-tag-default\">" + ESCAPE_CONTENT + "</div>",
                assertDoesNotThrow(() -> renderToString(component)));
    }

    @Test
    void shouldRenderContentEscaped() {
        final var component = new TagComponent();
        component.setContentValue(ESCAPE_CONTENT);
        component.setContentEscape(true);
        assertEquals("<div class=\"cui-tag cui-tag-default\">&lt;&gt;</div>",
                assertDoesNotThrow(() -> renderToString(component)));
    }

    @Test
    void shouldRenderState() {
        final var component = new TagComponent();
        component.setContentValue(SOME_CONTENT_VALUE);
        component.setState(ContextState.DANGER.name());
        final var styleClassBuilder = CssCuiBootstrap.TAG.getStyleClassBuilder();
        styleClassBuilder.append(TagState.DANGER);
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(styleClassBuilder)
                .withTextContent(SOME_CONTENT_VALUE);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderSize() {
        final var component = new TagComponent();
        component.setContentValue(SOME_CONTENT_VALUE);
        component.setSize(ContextSize.LG.name());
        final StyleClassBuilder styleClassBuilder = new StyleClassBuilderImpl(LABEL_PRIMARY_STYLECLASS);
        styleClassBuilder.append(TagSize.LG);
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(styleClassBuilder)
                .withTextContent(SOME_CONTENT_VALUE);
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderDisposeButtonAndHiddenInput() {
        final var component = new TagComponent();
        component.setId("tagComp");
        component.setModel(SOME_CONTENT_VALUE);
        component.setContentValue(SOME_CONTENT_VALUE);
        component.setDisposable(true);
        simulatePostAddToView(component);
        final var clientId = component.getClientId();
        final StyleClassBuilder styleClassBuilder = new StyleClassBuilderImpl(LABEL_PRIMARY_STYLECLASS);
        final var expected = new HtmlTreeBuilder().withNode(Node.DIV).withStyleClass(styleClassBuilder)
                .withTextContent(SOME_CONTENT_VALUE).withAttribute(AttributeName.ID, clientId)
                .withAttribute(AttributeName.NAME, clientId);
        final var current = expected.getCurrent();
        // Close button
        final var closeButton = new Element(Node.BUTTON.getContent());
        closeButton.setAttribute("aria-label", "Close");
        closeButton.setAttribute("class", "cui-icon cui-tag-dispose-button");
        closeButton.setAttribute("id", "closeButton");
        closeButton.setAttribute("name", "closeButton");
        final var buttonText = new Element(Node.SPAN.getContent());
        buttonText.setAttribute("aria-hidden", "true");
        buttonText.setAttribute("class", "btn-text");
        buttonText.setText("&#xD7;");
        closeButton.getChildren().add(buttonText);
        current.getChildren().add(closeButton);
        final var hiddenInput = new Element(Node.INPUT.getContent());
        hiddenInput.setAttribute("id", "disposedInfo");
        hiddenInput.setAttribute("name", "disposedInfo");
        hiddenInput.setAttribute("value", "false");
        current.getChildren().add(hiddenInput);
        // Hidden input
        /*
         * final String inputId = computeSuffixedAttribute(component,
         * TagComponent.DISPOSE_INFO_SUFFIX);
         * expected.withNode(Node.INPUT)
         * .withAttribute(AttributeName.TYPE, AttributeValue.HIDDEN)
         * .withAttribute(AttributeName.ID, inputId)
         * .withAttribute(AttributeName.NAME, inputId)
         * .withAttribute(AttributeName.VALUE, Boolean.FALSE.toString());
         */
        assertRenderResult(component, expected.getDocument());
    }

    private static String computeSuffixedAttribute(final UIComponent component, final String suffix) {
        return Joiner.on('_').skipNulls().join(component.getClientId(), suffix);
    }

    @Test
    void shouldNotRenderIfDisposed() {
        final var component = new TagComponent();
        component.setModel(SOME_CONTENT_VALUE);
        component.setDisposable(true);
        component.setParent(getFacesContext().getViewRoot());
        simulatePostAddToView(component);
        simulateEmitValueChangeOnHiddenInput(component);
        assertEmptyRenderResult(component);
    }

    private void simulateEmitValueChangeOnHiddenInput(final TagComponent component) {
        final var hiddenInput = getHiddenInput(component);
        hiddenInput.setSubmittedValue("true");
        hiddenInput.setValue("true");
        final List<ValueChangeListener> valueChangeListeners = Arrays.asList(hiddenInput.getValueChangeListeners());
        for (final ValueChangeListener listener : valueChangeListeners) {
            listener.processValueChange(new ValueChangeEvent(hiddenInput, "false", "true"));
        }
    }

    private static UIInput getHiddenInput(final TagComponent component) {
        return (UIInput) component.getChildren().stream().filter(child -> child instanceof UIInput).findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("Hidden input is missing but should be available as child"));
    }

    private void simulatePostAddToView(final TagComponent component) {
        component.processEvent(new PostAddToViewEvent(getFacesContext().getViewRoot()));
    }

    @Test
    void shouldNotQueueEventOnDecodeIfNotDisposed() {
        final var component = new TagComponent();
        component.setModel(SOME_CONTENT_VALUE);
        component.setDisposable(true);
        component.setParent(getFacesContext().getViewRoot());
        // Hidden input
        final var inputId = computeSuffixedAttribute(component, TagComponent.DISPOSE_INFO_SUFFIX);
        getRequestConfigDecorator().setRequestParameter(inputId, Boolean.FALSE.toString());
        getRenderer().decode(getFacesContext(), component);
        assertTrue(extractEventsFromViewRoot().isEmpty());
    }

    @Test
    void shouldNotQueueEventOnDecodeOnInvalidClientId() {
        final var component = new TagComponent();
        component.setModel(SOME_CONTENT_VALUE);
        component.setDisposable(true);
        component.setParent(getFacesContext().getViewRoot());
        // Hidden input
        final var inputId = "x" + computeSuffixedAttribute(component, TagComponent.DISPOSE_INFO_SUFFIX);
        getRequestConfigDecorator().setRequestParameter(inputId, Boolean.TRUE.toString());
        getRenderer().decode(getFacesContext(), component);
        assertTrue(extractEventsFromViewRoot().isEmpty());
    }

    @Override
    protected UIComponent getComponent() {
        return new TagComponent();
    }

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerUIComponent(CloseCommandButton.class).registerRenderer(CloseCommandButtonRenderer.class);
    }
}
