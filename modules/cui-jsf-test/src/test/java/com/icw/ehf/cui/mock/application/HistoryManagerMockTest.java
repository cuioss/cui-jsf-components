package com.icw.ehf.cui.mock.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.icw.ehf.cui.application.history.HistoryManager;
import com.icw.ehf.cui.application.history.impl.HistoryManagerImpl;

import de.cuioss.test.jsf.config.decorator.BeanConfigDecorator;
import de.cuioss.test.jsf.junit5.AbstractBeanTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(of = "pageReload")
class HistoryManagerMockTest extends AbstractBeanTest<HistoryManagerMock> {

    @Test
    void shouldRegisterAsBean() {
        assertNull(BeanConfigDecorator.getBean(HistoryManagerImpl.BEAN_NAME, getFacesContext(), HistoryManager.class));
        new HistoryManagerMock().configureBeans(getBeanConfigDecorator());
        assertNotNull(
                BeanConfigDecorator.getBean(HistoryManagerImpl.BEAN_NAME, getFacesContext(), HistoryManager.class));
    }
}
