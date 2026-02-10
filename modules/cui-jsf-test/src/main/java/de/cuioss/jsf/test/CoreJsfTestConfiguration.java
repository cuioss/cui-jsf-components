/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.jsf.test;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.converter.ObjectToStringConverter;
import de.cuioss.jsf.api.converter.StringIdentConverter;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.defaults.BasicApplicationConfiguration;
import de.cuioss.test.jsf.mocks.CuiMockRenderer;
import jakarta.faces.convert.NumberConverter;

/**
 * Defines a base setup for JSF testing that provides standard CUI configuration elements.
 * <p>
 * This configuration extends {@link BasicApplicationConfiguration} and adds CUI-specific
 * components, converters, and renderers for comprehensive JSF unit testing.
 * </p>
 * <p>
 * Core configurations provided:
 * <ul>
 * <li>Registers essential converters: {@link StringIdentConverter}, {@link ObjectToStringConverter},
 * and {@link NumberConverter}</li>
 * <li>Registers all standard JSF HTML components defined in {@link JsfHtmlComponent}</li>
 * <li>Sets up appropriate mock renderers for each component</li>
 * <li>Provides access to test and core resource bundles</li>
 * </ul>
 * <p>
 * Usage example:
 * <pre>
 * {@code
 * @JsfTestConfiguration(CoreJsfTestConfiguration.class)
 * class MyComponentTest extends AbstractComponentTest<MyComponent> {
 *     // Test methods
 * }
 * }
 * </pre>
 * <p>
 * This class is thread-safe as it contains no mutable state.
 * </p>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public class CoreJsfTestConfiguration extends BasicApplicationConfiguration {

    /**
     * Path to test resource bundle.
     * <p>
     * Used for locating test-specific message resources.
     * </p>
     */
    public static final String TEST_BUNDLE_BASE_PATH = "de.cuioss.jsf.components.bundle.";

    /**
     * Path to cui-messages resource bundle.
     * <p>
     * Used for accessing core CUI framework messages and labels.
     * </p>
     */
    public static final String CUI_BUNDLE_BASE_PATH = "de.cuioss.jsf.api.core.l18n.";

    /**
     * Configures the JSF test environment with CUI components and converters.
     * <p>
     * This method registers all standard converters and components needed for
     * testing CUI JSF components, providing mocked renderers for each component
     * that render the appropriate HTML element.
     * </p>
     *
     * @param decorator The component configuration decorator used to register
     *                  components, converters, and renderers with the test environment.
     *                  Must not be null.
     */
    public static void configureComponents(final ComponentConfigDecorator decorator) {
        decorator.registerConverter(StringIdentConverter.class);
        decorator.registerConverter(NumberConverter.class, NumberConverter.CONVERTER_ID);
        decorator.registerConverter(ObjectToStringConverter.class);
        for (JsfHtmlComponent<?> component : JsfHtmlComponent.VALUES) {
            decorator.registerUIComponent(component.getComponentType(), component.getComponentClass()).registerRenderer(
                    component.getFamily(), component.getRendererType(),
                    new CuiMockRenderer(component.getDefaultHtmlElement().getContent()));
        }
    }
}
