package com.icw.ehf.cui.core.api.components.model.widget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.faces.event.ActionEvent;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.core.api.components.support.DummyComponent;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBeanProperty;

@VerifyBeanProperty(exclude = { "primaryActionTitle", "notificationBoxState", "primaryAction", "notificationBoxValue",
    "title", "content", "disableCoreAction", "disablePrimaryAction", "rendered", "compositeComponentId", "coreAction",
    "renderPrimaryAction", "initialized", "renderContent", "id", "titleIcon", "titleValue" })
class BaseDeferredLoadingWidgetTest extends ValueObjectTest<DeferredLoadingWidgetMock> {

    @Test
    public void shouldHandleInitialized() {
        var sut = anyValueObject();
        assertFalse(sut.isInitialized());
        sut.processAction(new ActionEvent(new DummyComponent()));
        assertTrue(sut.isInitialized());
    }

}
