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
package de.cuioss.jsf.api.components.renderer.partial;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static java.util.Objects.requireNonNull;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.components.renderer.DecoratingResponseWriter;
import de.cuioss.uimodel.model.code.CodeType;
import jakarta.faces.component.UIComponent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
