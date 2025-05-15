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
package de.cuioss.jsf.test;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.jsf.producer.JsfObjectsProducer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import org.jboss.weld.junit5.auto.ActivateScopes;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * JUnit 5 annotation that enables a fully configured JSF and CDI environment for testing.
 * <p>
 * This meta-annotation sets up the essential infrastructure for creating comprehensive
 * unit tests in the CUI JSF Components context. It combines JSF test utilities with
 * CDI capabilities, providing access to both frameworks within tests.
 * </p>
 * <p>
 * The annotation includes the following capabilities:
 * <ul>
 * <li>{@link EnableAutoWeld}: Activates the Weld CDI container for tests</li>
 * <li>{@link EnableJsfEnvironment}: Sets up a complete JSF environment with mock FacesContext and related objects</li>
 * <li>Registers essential producers: {@link ProjectStageProducerMock} and {@link JsfObjectsProducer}</li>
 * <li>Activates common CDI scopes: {@link RequestScoped}, {@link SessionScoped}, and {@link ViewScoped}</li>
 * </ul>
 * </p>
 * <p>
 * Usage example:
 * <pre>
 * {@code
 * @EnableJSFCDIEnvironment
 * class MyComponentTest {
 *     
 *     @Inject
 *     private FacesContext facesContext;
 *     
 *     @Test
 *     void shouldDoSomethingWithJsfAndCdi() {
 *         // Test using injected JSF and CDI resources
 *     }
 * }
 * }
 * </pre>
 * </p>
 * <p>
 * This annotation is thread-safe and can be applied to any JUnit 5 test class.
 * </p>
 * 
 * @author Oliver Wolff
 * @since 1.0
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@EnableAutoWeld
@EnableJsfEnvironment
@ActivateScopes({RequestScoped.class, SessionScoped.class, ViewScoped.class})
@AddBeanClasses({ProjectStageProducerMock.class, JsfObjectsProducer.class})
public @interface EnableJSFCDIEnvironment {
}
