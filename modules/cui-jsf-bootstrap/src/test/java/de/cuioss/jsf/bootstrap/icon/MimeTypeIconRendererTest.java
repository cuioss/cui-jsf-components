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
package de.cuioss.jsf.bootstrap.icon;

import de.cuioss.jsf.api.components.css.ContextSize;
import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.api.converter.StringIdentConverter;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import org.junit.jupiter.api.Test;

class MimeTypeIconRendererTest extends AbstractComponentRendererTest<MimeTypeIconRenderer> {

    private static final String TITLE = "someTitle";

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack");
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                .currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC)
                .withStyleClass(
                        "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                .currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldRenderCustomDecorator() {
        var component = new MimeTypeIconComponent();
        component.setDecoratorClass(TITLE);
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack");
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x " + TITLE).currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC)
                .withStyleClass(
                        "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                .currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderMimeType() {
        var component = new MimeTypeIconComponent();
        component.setMimeTypeIcon(MimeTypeIcon.AUDIO_BASIC);
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack");
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                .currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder "
                + MimeTypeIcon.AUDIO_BASIC.getPlaceholder()).currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC)
                .withStyleClass("cui-mime-type cui-icon-stack-1x " + MimeTypeIcon.AUDIO_BASIC.getIconClass());
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderCustomSize() {
        var component = new MimeTypeIconComponent();
        component.setSize(ContextSize.LG.name());
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack cui-icon-lg");
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                .currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC)
                .withStyleClass(
                        "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                .currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");
        assertRenderResult(component, expected.getDocument());
    }

    @Test
    void shouldRenderTitle() {
        var component = new MimeTypeIconComponent();
        component.setTitleValue(TITLE);
        component.setTitleConverter(new StringIdentConverter());
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass("cui-icon-stack")
                .withAttribute(AttributeName.TITLE, TITLE);
        // Layer 1
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-folder")
                .currentHierarchyUp();
        // Layer 2
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-no-decorator")
                .currentHierarchyUp();
        // Layer 3
        expected.withNode(Node.ITALIC)
                .withStyleClass(
                        "cui-mime-type cui-icon-stack-1x cui-mime-type-placeholder cui-mime-type-undefined-placeholder")
                .currentHierarchyUp();
        // Layer 4
        expected.withNode(Node.ITALIC).withStyleClass("cui-mime-type cui-icon-stack-1x cui-mime-type-undefined");
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new MimeTypeIconComponent();
    }
}
