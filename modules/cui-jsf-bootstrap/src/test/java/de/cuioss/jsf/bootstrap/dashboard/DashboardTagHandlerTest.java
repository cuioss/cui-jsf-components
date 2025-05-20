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
package de.cuioss.jsf.bootstrap.dashboard;

import static de.cuioss.tools.collect.CollectionLiterals.mutableList;

import de.cuioss.jsf.api.components.model.widget.DashboardWidgetModel;
import de.cuioss.jsf.api.components.support.DummyComponent;
import jakarta.faces.view.facelets.*;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@DisplayName("Tests for DashboardTagHandler")
class DashboardTagHandlerTest {

    private TagAttribute tagAttribute;
    private Tag tag;
    private ComponentConfig componentConfig;
    private TagAttributes tagAttributes;
    private FaceletContext faceletContext;
    private DashboardWidgetModel dashboardWidgetModel;

    @BeforeEach
    void setUp() {
        // Initialize mocks for each test
        tagAttribute = EasyMock.niceMock(TagAttribute.class);
        componentConfig = EasyMock.niceMock(ComponentConfig.class);
        tagAttributes = EasyMock.niceMock(TagAttributes.class);
        faceletContext = EasyMock.niceMock(FaceletContext.class);
        dashboardWidgetModel = EasyMock.niceMock(DashboardWidgetModel.class);
    }

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Should handle empty widget list")
        void shouldHandleEmptyWidgetList() throws IOException {
            // Arrange
            EasyMock.expect(tagAttribute.getObject(EasyMock.anyObject(FaceletContext.class))).andReturn(mutableList());
            EasyMock.replay(tagAttribute);

            EasyMock.expect(tagAttributes.get("widgets")).andReturn(tagAttribute);
            EasyMock.replay(tagAttributes);

            tag = new Tag(null, null, null, null, tagAttributes);

            EasyMock.expect(componentConfig.getTagId()).andReturn("test");
            EasyMock.expect(componentConfig.getTag()).andReturn(tag);
            EasyMock.expect(componentConfig.getNextHandler()).andReturn(new CompositeFaceletHandler(new FaceletHandler[0]));
            EasyMock.replay(componentConfig);

            var underTest = new DashboardTagHandler(componentConfig);

            // Act & Assert - No exception should be thrown
            underTest.apply(faceletContext, new DummyComponent());
        }

        @Test
        @DisplayName("Should handle widget list with one item")
        void shouldHandleWidgetListWithOneItem() throws IOException {
            // Arrange
            EasyMock.expect(dashboardWidgetModel.getCompositeComponentId()).andReturn("abc:def").anyTimes();
            EasyMock.replay(dashboardWidgetModel);

            EasyMock.expect(tagAttribute.getObject(EasyMock.anyObject(FaceletContext.class)))
                    .andReturn(mutableList(dashboardWidgetModel));
            EasyMock.expect(tagAttribute.getValue()).andReturn("{abc}").anyTimes();
            EasyMock.replay(tagAttribute);

            EasyMock.expect(tagAttributes.get("widgets")).andReturn(tagAttribute);
            EasyMock.replay(tagAttributes);

            tag = new Tag(null, null, null, null, tagAttributes);

            EasyMock.expect(componentConfig.getTagId()).andReturn("test");
            EasyMock.expect(componentConfig.getTag()).andReturn(tag);
            EasyMock.expect(componentConfig.getNextHandler()).andReturn(new CompositeFaceletHandler(new FaceletHandler[0]));
            EasyMock.replay(componentConfig);

            var underTest = new DashboardTagHandler(componentConfig);

            // Act & Assert - No exception should be thrown
            underTest.apply(faceletContext, new DummyComponent());
        }
    }
}
