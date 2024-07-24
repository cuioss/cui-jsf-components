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

import static de.cuioss.jsf.bootstrap.icon.GenderIconComponentTest.GENDER_MALE_TITLE;
import static de.cuioss.jsf.bootstrap.icon.GenderIconComponentTest.GENDER_UNKNOWN_TITLE;

import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.HtmlTreeBuilder;
import de.cuioss.jsf.api.components.html.Node;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.jsf.test.EnableJSFCDIEnvironment;
import de.cuioss.jsf.test.EnableResourceBundleSupport;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.renderer.AbstractComponentRendererTest;
import de.cuioss.uimodel.model.Gender;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@EnableJSFCDIEnvironment
@EnableResourceBundleSupport
class GenderIconRendererTest extends AbstractComponentRendererTest<IconRenderer> {

    private static final String ICON_PREFIX = "cui-icon ";

    private static final String GENDER_UNKNOWN_CSS = ICON_PREFIX + Gender.UNKNOWN.getCssClass();

    private static final String GENDER_MALE_CSS = ICON_PREFIX + Gender.MALE.getCssClass();

    @Test
    void shouldRenderMinimal() {
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass(GENDER_UNKNOWN_CSS)
                .withAttribute(AttributeName.TITLE, GENDER_UNKNOWN_TITLE);
        assertRenderResult(getComponent(), expected.getDocument());
    }

    @Test
    void shouldRenderGenderIcon() {
        var component = new GenderIconComponent();
        component.setGender(Gender.MALE);
        var expected = new HtmlTreeBuilder().withNode(Node.SPAN).withStyleClass(GENDER_MALE_CSS)
                .withAttribute(AttributeName.TITLE, GENDER_MALE_TITLE);
        assertRenderResult(component, expected.getDocument());
    }

    @Override
    protected UIComponent getComponent() {
        return new GenderIconComponent();
    }
}
