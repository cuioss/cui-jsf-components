package com.icw.ehf.cui.core.api.common.accessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class ManagedBeanAccessorTest extends JsfEnabledTestEnvironment {

    private static final String BEAN_IDENTIFIER = "#{someBean}";

    private static final String BEAN_IDENTIFIER_PLAIN_NAME = "someBean";

    private static final String FIRST_BEAN = "firstBean";

    private static final String SECOND_BEAN = "secondBean";

    @BeforeEach
    void setUpTest() {
        getBeanConfigDecorator().register(FIRST_BEAN, BEAN_IDENTIFIER);
    }

    void shouldAlwaysResolve() {
        var underTest = new ManagedBeanAccessor<>(BEAN_IDENTIFIER, String.class, true);
        assertEquals(FIRST_BEAN, underTest.getValue());
        // Because the Provider should always resolve, it should resolve the
        // second bean
        getBeanConfigDecorator().register(SECOND_BEAN, BEAN_IDENTIFIER);
        assertEquals(SECOND_BEAN, underTest.getValue());
    }

    @Test
    void shouldAlwaysResolveWithPlainName() {
        var underTest =
            new ManagedBeanAccessor<>(BEAN_IDENTIFIER_PLAIN_NAME, String.class, true);
        assertEquals(FIRST_BEAN, underTest.getValue());
        // Because the Provider should always resolve, it should resolve the
        // second bean
        getBeanConfigDecorator().register(SECOND_BEAN, BEAN_IDENTIFIER);
        assertEquals(SECOND_BEAN, underTest.getValue());
    }

    @Test
    void shouldResolveOnlyOnce() {
        var underTest = new ManagedBeanAccessor<>(BEAN_IDENTIFIER, String.class, false);
        assertEquals(FIRST_BEAN, underTest.getValue());
        // Because the Provider should not always resolve, it should resolve the
        // first bean
        getBeanConfigDecorator().register(SECOND_BEAN, BEAN_IDENTIFIER);
        assertEquals(FIRST_BEAN, underTest.getValue());
    }

    @SuppressWarnings("unused")
    @Test
    void shouldFailOnEmptyIdentifier() {
        assertThrows(NullPointerException.class, () -> {
            new ManagedBeanAccessor<>("", String.class, false);
        });
    }

    @SuppressWarnings("unused")
    @Test
    void testShouldFailOnNullClassParameter() {
        assertThrows(NullPointerException.class, () -> {
            new ManagedBeanAccessor<>(BEAN_IDENTIFIER, null, false);
        });
    }
}
