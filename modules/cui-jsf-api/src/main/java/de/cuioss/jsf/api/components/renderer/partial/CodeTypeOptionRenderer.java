package de.cuioss.jsf.api.components.renderer.partial;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.faces.component.UIComponent;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.uimodel.model.code.CodeType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Renders the given {@link CodeType} to an option node.
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor
@ToString
public class CodeTypeOptionRenderer {

    @NonNull
    private final List<CodeType> codeTypes;

    /**
     * @param codeType to be rendered, must not be null
     */
    public CodeTypeOptionRenderer(final CodeType codeType) {
        this(immutableList(requireNonNull(codeType)));
    }

    /**
     * Renders an option element for each contained {@link CodeType}
     *
     * @param writer to be written to
     * @param locale needed for looking up the correct display-String.
     * @param escape indicating whether or not escape the Test-child.
     * @throws IOException
     */
    @SuppressWarnings("resource") // owolff: No resource leak, because the actual response-writer is
                                  // controlled by JSF
    public void render(final DecoratingResponseWriter<? extends UIComponent> writer, final Locale locale,
            final boolean escape) throws IOException {
        for (CodeType codeType : codeTypes) {
            writer.withStartElement(Node.OPTION);
            writer.withAttribute(AttributeName.VALUE, codeType.getIdentifier());
            writer.withTextContent(codeType.getResolved(locale), escape);
            writer.withEndElement(Node.OPTION);
        }
    }
}
