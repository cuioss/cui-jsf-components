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

import java.io.IOException;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.CompositeFaceletHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.model.widget.DashboardWidgetModel;
import de.cuioss.jsf.api.components.support.DummyComponent;

class DashboardTagHandlerTest {

    private final TagAttribute tagAttribute = EasyMock.niceMock(TagAttribute.class);
    private Tag tag;
    private final ComponentConfig componentConfig = EasyMock.niceMock(ComponentConfig.class);
    private final TagAttributes tagAttributes = EasyMock.niceMock(TagAttributes.class);
    private final FaceletContext faceletContext = EasyMock.niceMock(FaceletContext.class);
    private final DashboardWidgetModel dashboardWidgetModel = EasyMock.niceMock(DashboardWidgetModel.class);

    @Test
    void testEmpty() throws IOException {
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
        underTest.apply(faceletContext, new DummyComponent());
    }

    @Test
    void test() throws IOException {
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
        var fc = EasyMock.niceMock(FaceletContext.class);
        underTest.apply(faceletContext, new DummyComponent());
    }
}
