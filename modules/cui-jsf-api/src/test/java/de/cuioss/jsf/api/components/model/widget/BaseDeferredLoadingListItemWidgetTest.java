package de.cuioss.jsf.api.components.model.widget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.event.ActionEvent;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.components.support.DummyComponent;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBeanProperty;

@VerifyBeanProperty(exclude = { "primaryActionTitle", "notificationBoxState", "primaryAction", "notificationBoxValue",
        "title", "content", "disableCoreAction", "disablePrimaryAction", "rendered", "compositeComponentId",
        "coreAction", "renderPrimaryAction", "initialized", "renderContent", "id", "titleIcon", "titleValue",
        "noItemsMessage", "renderShowMoreButton", "items" })
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
