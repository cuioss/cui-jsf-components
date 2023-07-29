package de.cuioss.jsf.api.common;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;

class ManagedBeansProviderTest extends JsfEnabledTestEnvironment {

    private static final String BEAN_KEY = "ClientInformationImpl";

    private static final String BEAN_KEY_EL = "#{ClientInformationImpl}";

    private static final String EXT_BEAN_KEY_EL = "#{ClientInformationImplExt}";

    public interface TestInterfaceA {
        // dymmy test interface
    }

    public interface TestInterfaceB extends TestInterfaceA {
        // dymmy test interface
    }

    static class ClientInformationImplExt extends SomeTestBean implements TestInterfaceB {

        private static final long serialVersionUID = 7053420614897774823L;

        public ClientInformationImplExt() {
        }
    }

    @BeforeEach
    void setUpTest() {
        getBeanConfigDecorator().register(new SomeTestBean(), BEAN_KEY_EL);
        getBeanConfigDecorator().register(new ClientInformationImplExt(), EXT_BEAN_KEY_EL);
    }

    @Test
    void testGetManagedBeanInvalidBeanKeyNull() {
        assertThrows(NullPointerException.class,
                () -> ManagedBeansProvider.getManagedBean(null, SomeTestBean.class, getFacesContext()));
    }

    @Test
    void testGetManagedBeanValidELExpression() {
        assertNotNull(ManagedBeansProvider.getManagedBean(EXT_BEAN_KEY_EL, SomeTestBean.class, getFacesContext()));
    }

    @Test
    void shouldWorkForDerivative() {
        assertNotNull(ManagedBeansProvider.getManagedBean(BEAN_KEY_EL, SomeTestBean.class, getFacesContext()));
    }

    @Test
    void shouldWorkForInterfaceDerivative() {
        assertNotNull(ManagedBeansProvider.getManagedBean(EXT_BEAN_KEY_EL, TestInterfaceA.class, getFacesContext()));
    }

    @Test
    void testGetManagedBeanPlainExpression() {
        assertNotNull(ManagedBeansProvider.getManagedBean(BEAN_KEY, SomeTestBean.class, getFacesContext()));
    }

    @Test
    void testGetManagedBeanInvalidBeanKeyEmpty() {
        assertThrows(NullPointerException.class,
                () -> ManagedBeansProvider.getManagedBean("", SomeTestBean.class, getFacesContext()));
    }

    /**
     * Expected : IllegalArgumentException if ManagedBean can not be found
     */
    @Test
    void shouldFailIfBeanNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> ManagedBeansProvider.getManagedBean("unknonBean", SomeTestBean.class, getFacesContext()));
    }

    /**
     * Expected : IllegalArgumentException if ManagedBean doesn't implement the
     * expected interface
     */
    @Test
    void shouldFailIfBeanHasNoExpectedInterface() {
        assertThrows(IllegalArgumentException.class,
                () -> ManagedBeansProvider.getManagedBean(BEAN_KEY_EL, Date.class, getFacesContext()));
    }
}
