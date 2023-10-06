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
package de.cuioss.jsf.api.components.renderer;

import java.io.IOException;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

import de.cuioss.jsf.api.components.html.Node;

public class MockDecoratorRenderer extends BaseDecoratorRenderer<HtmlInputText> {

    public MockDecoratorRenderer(final boolean renderChildren) {
        super(renderChildren);
    }

    public MockDecoratorRenderer() {
        this(true);
    }

    @Override
    protected void doEncodeBegin(final FacesContext context, final DecoratingResponseWriter<HtmlInputText> writer,
            final HtmlInputText component) throws IOException {
        writer.withStartElement(Node.DIV);
    }

    @Override
    protected void doEncodeEnd(final FacesContext context, final DecoratingResponseWriter<HtmlInputText> writer,
            final HtmlInputText component) throws IOException {
        writer.withEndElement(Node.DIV);
    }

}
