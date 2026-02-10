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
package de.cuioss.jsf.bootstrap.waitingindicator;

import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class WaitingIndicatorRendererTest extends AbstractComponentRendererTest<WaitingIndicatorRenderer> {

    @BeforeEach
    void configureCuiComponents(ComponentConfigDecorator decorator) {
        CoreJsfTestConfiguration.configureComponents(decorator);
    }

    @Test
    void shouldRenderMinimal(FacesContext facesContext) throws Exception {
        var expected = new HtmlTreeBuilder().withNode(Node.DIV).withNode(Node.DIV)
                .withStyleClass("waiting-indicator waiting-indicator-size-md").withNode(Node.DIV)
                .withStyleClass("item-1 item-size-md").currentHierarchyUp().withNode(Node.DIV)
                .withStyleClass("item-2 item-size-md").currentHierarchyUp().withNode(Node.DIV)
                .withStyleClass("item-3 item-size-md").currentHierarchyUp().withNode(Node.DIV)
                .withStyleClass("item-4 item-size-md").currentHierarchyUp().withNode(Node.DIV)
                .withStyleClass("item-5 item-size-md").currentHierarchyUp().currentHierarchyUp().currentHierarchyUp();
        assertRenderResult(getComponent(), expected.getDocument(), facesContext);
    }

    @Override
    protected UIComponent getComponent() {
        return new WaitingIndicatorComponent();
    }
}
