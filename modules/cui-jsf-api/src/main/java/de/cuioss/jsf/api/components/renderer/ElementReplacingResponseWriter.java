package de.cuioss.jsf.api.components.renderer;

import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import lombok.Getter;

/**
 * This response writer is used for replacing certain html elements, see javadoc of
 * {@link #ElementReplacingResponseWriter(ResponseWriter, String, String, boolean)}
 *
 * @author Oliver Wolff
 *
 */
public class ElementReplacingResponseWriter extends ResponseWriterWrapper {

    @Getter
    private final ResponseWriter wrapped;

    private final String filterElement;
    private final String replaceElement;
    private final boolean ignoreCloseElement;

    /**
     * Constructor.
     *
     * @param delegate the {@link ResponseWriter} to be delegated to
     * @param filterElement the Html element to be filtered, / replaced, must not be null nor empty.
     * @param replaceElement the replacement element, must not be null nor empty.
     * @param ignoreCloseElement indicates whether to filter / ignore the closing of an element
     */
    public ElementReplacingResponseWriter(final ResponseWriter delegate, final String filterElement,
            final String replaceElement, final boolean ignoreCloseElement) {
        super(delegate);
        wrapped = delegate;
        this.filterElement = requireNonNull(emptyToNull(filterElement));
        this.replaceElement = requireNonNull(emptyToNull(replaceElement));
        this.ignoreCloseElement = ignoreCloseElement;
    }

    @Override
    public void startElement(final String name, final UIComponent component) throws IOException {
        if (filterElement.equals(name)) {
            super.startElement(replaceElement, component);
        } else {
            super.startElement(name, component);
        }
    }

    @Override
    public void endElement(final String name) throws IOException {
        if (filterElement.equals(name)) {
            if (!ignoreCloseElement) {
                super.endElement(replaceElement);
            }
        } else {
            super.endElement(name);
        }
    }

    /**
     * @param context to be wrapped
     * @param filterElement the Html element to be filtered, / replaced, must not be null nor empty.
     * @param replaceElement the replacement element, must not be null nor empty.
     * @param ignoreCloseElement indicates whether to filter / ignore the closing of an element
     * @return a {@link FacesContextWrapper} providing an instance of
     *         {@link ElementReplacingResponseWriter} with the configured parameter
     */
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    public static FacesContext createWrappedReplacingResonseWriter(final FacesContext context,
            final String filterElement,
            final String replaceElement, final boolean ignoreCloseElement) {
        return new FacesContextWrapper(context) {

            @Override
            public ResponseWriter getResponseWriter() {
                return new ElementReplacingResponseWriter(context.getResponseWriter(), filterElement, replaceElement,
                        ignoreCloseElement);
            }

        };
    }
}
