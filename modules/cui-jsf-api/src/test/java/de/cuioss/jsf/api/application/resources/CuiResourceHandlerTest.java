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
package de.cuioss.jsf.api.application.resources;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.ProjectStage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.cuioss.jsf.api.application.resources.impl.CuiResourceConfigurationImpl;
import de.cuioss.test.generator.internal.net.java.quickcheck.generator.support.IntegerGenerator;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import de.cuioss.test.jsf.junit5.JsfEnabledTestEnvironment;
import de.cuioss.test.jsf.mocks.CuiMockResource;
import de.cuioss.test.jsf.mocks.CuiMockResourceHandler;
import de.cuioss.tools.string.MoreStrings;

@EnableGeneratorController
class CuiResourceHandlerTest extends JsfEnabledTestEnvironment {

    private static final String CSS = "css";

    private static final String STYLE_CSS = "style.css";

    private static final String STYLE_MIN_CSS = "style.min.css";

    private static final String ANY_UNKNOWN_JPG = "any_unknown.jpg";

    private static final String ALIEN_LIB = "alien_lib";

    private static final String APPLICATION_CSS = "application.css";

    private CuiResourceHandler underTest;

    private CuiResourceConfigurationImpl cuiResourceConfiguration;

    private CuiResourceManager cuiResourceManager;

    private CuiMockResourceHandler mockResourceHandler;

    @BeforeEach
    void setUpHandlerTest() {
        setupResourceHandlerMock();
        getApplicationConfigDecorator().setProjectStage(ProjectStage.Production);
        underTest = new CuiResourceHandler(mockResourceHandler);
        createResourceConfiguration();
        createResourceManager();
    }

    void setupResourceHandlerMock() {
        mockResourceHandler = new CuiMockResourceHandler();
        Map<String, CuiMockResource> availableResources = new HashMap<>();
        availableResources.put("css-style.css", prepareResource(STYLE_CSS, CSS));
        availableResources.put("css-style.min.css", prepareResource(STYLE_MIN_CSS, CSS));
        availableResources.put("css-application.css", prepareResource(APPLICATION_CSS, CSS, "1.0.0"));
        availableResources.put("alien_lib-any_unknown.jpg", prepareResource(ANY_UNKNOWN_JPG, ALIEN_LIB));
        mockResourceHandler.setAvailableResouces(availableResources);
    }

    private static CuiMockResource prepareResource(String name, String lib) {
        return prepareResource(name, lib, null);
    }

    private static CuiMockResource prepareResource(String name, String lib, String versionInfo) {
        var mockResource = new CuiMockResource();
        mockResource.setResourceName(name);
        mockResource.setLibraryName(lib);
        if (MoreStrings.isBlank(versionInfo)) {
            mockResource.setRequestPath("/javax.faces.resource/" + name + "?ln=" + lib);
        } else {
            mockResource.setRequestPath("/javax.faces.resource/" + name + "?ln=" + lib + "&v=" + versionInfo);
        }
        return mockResource;
    }

    @Test
    void testCreateResourceProduction() {
        var resource = underTest.createResource(STYLE_CSS, CSS);
        assertNotNull(resource);
        assertEquals(CSS, resource.getLibraryName());
        assertEquals(STYLE_MIN_CSS, resource.getResourceName());
    }

    @Test
    void testCreateResourceDevelopment() {
        getApplicationConfigDecorator().setProjectStage(ProjectStage.Development);
        underTest = new CuiResourceHandler(mockResourceHandler);
        var resource = underTest.createResource(STYLE_CSS, CSS);
        assertNotNull(resource);
        assertEquals(CSS, resource.getLibraryName());
        assertEquals(STYLE_CSS, resource.getResourceName());
    }

    @Test
    void shouldUpdateVersionInformation() {
        cuiResourceConfiguration.setVersion(new IntegerGenerator(1, 100).next().toString());
        var expectedVersionInfo = "&v=" + cuiResourceConfiguration.getVersion();
        var resource = underTest.createResource(STYLE_CSS, CSS);
        assertTrue(resource.getRequestPath().endsWith(expectedVersionInfo), "version information was not found");

        var applicationCssResource = underTest.createResource(APPLICATION_CSS, CSS);
        assertTrue(applicationCssResource.getRequestPath().endsWith("1.0.0"), "version information was not found");
        assertFalse(applicationCssResource.getRequestPath().endsWith(expectedVersionInfo),
                "unexpected version information found");

        var alienResource = underTest.createResource(ANY_UNKNOWN_JPG, ALIEN_LIB);
        assertFalse(alienResource.getRequestPath().endsWith(expectedVersionInfo),
                "unexpected version information found");
    }

    private void createResourceManager() {
        cuiResourceManager = new CuiResourceManager();
        getBeanConfigDecorator().register(cuiResourceManager, CuiResourceManager.BEAN_NAME);
    }

    private void createResourceConfiguration() {
        cuiResourceConfiguration = new CuiResourceConfigurationImpl();
        cuiResourceConfiguration.setVersion("1.0");
        cuiResourceConfiguration.setHandledLibraries(immutableList(CSS, "fonts", "javascript"));
        cuiResourceConfiguration.setHandledSuffixes(immutableList(CSS, "js"));
        getBeanConfigDecorator().register(cuiResourceConfiguration, CuiResourceConfigurationImpl.BEAN_NAME);
    }
}
