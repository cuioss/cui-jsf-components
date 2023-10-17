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
package de.cuioss.jsf.api;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;

import org.jboss.weld.junit5.auto.ActivateScopes;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import de.cuioss.test.jsf.junit5.EnableJsfEnvironment;
import de.cuioss.test.jsf.producer.JsfObjectsProducer;

/**
 * Using this annotations at type-level of a junit 5 test defines the basic
 * infrastructure for creating unit-tests in the cui-jsf-components contexts. It
 * is a meta-annotation consisting of:
 *
 * <ul>
 * <li>{@link EnableAutoWeld}</li>
 * <li>{@link EnableJsfEnvironment}</li>
 * </ul>
 * <p>
 * <ul>
 * <li>{@link ProjectStageProducerMock}</li>
 * <li>{@link JsfObjectsProducer}</li>
 * </ul>
 * It explicitly activates the Scopes:
 * </p>
 * <ul>
 * <li>{@link RequestScoped}</li>
 * <li>{@link SessionScoped}</li>
 * <li>{@link ViewScoped}</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@EnableAutoWeld
@EnableJsfEnvironment
@ActivateScopes({ RequestScoped.class, SessionScoped.class, ViewScoped.class })
@AddBeanClasses({ ProjectStageProducerMock.class, JsfObjectsProducer.class })
public @interface EnableJSFCDIEnvironment {
}
