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
package de.cuioss.jsf.bootstrap.widget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBeanProperty;
import jakarta.faces.event.ActionEvent;
import org.junit.jupiter.api.Test;

@VerifyBeanProperty(exclude = {"primaryActionTitle", "notificationBoxState", "primaryAction", "notificationBoxValue",
        "title", "content", "disableCoreAction", "disablePrimaryAction", "rendered", "compositeComponentId",
        "coreAction", "renderPrimaryAction", "initialized", "renderContent", "id", "titleIcon", "titleValue",
        "noItemsMessage", "renderShowMoreButton", "items"})
class BaseDeferredLoadingListItemWidgetTest extends ValueObjectTest<BaseDeferredLoadingListItemWidgetMock> {

    @Test
    void shouldHandleInitialized() {
        var sut = anyValueObject();
        assertFalse(sut.isInitialized());
        assertFalse(sut.isRenderShowMoreButton());
        sut.processAction(new ActionEvent(new DummyComponent()));
        assertTrue(sut.isInitialized());
        assertTrue(sut.isRenderShowMoreButton());
    }

}
