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
package de.cuioss.jsf.api;

import de.cuioss.jsf.api.components.JsfHtmlComponent;
import de.cuioss.jsf.api.converter.ObjectToStringConverter;
import de.cuioss.jsf.api.converter.StringIdentConverter;
import de.cuioss.test.jsf.config.ComponentConfigurator;
import de.cuioss.test.jsf.config.decorator.ComponentConfigDecorator;
import de.cuioss.test.jsf.defaults.BasicApplicationConfiguration;
import de.cuioss.test.jsf.mocks.CuiMockRenderer;
import jakarta.faces.convert.NumberConverter;
import jakarta.faces.render.Renderer;

/**
 * Defines a base setup for testing. Implicitly uses
 * {@link BasicApplicationConfiguration}. In addition, it:
 * <ul>
 * <li>Registers {@link StringIdentConverter} {@link ObjectToStringConverter}
 * and {@link NumberConverter}</li>
 * <li>Registers a number of default components and {@link Renderer} derived
 * from {@link JsfHtmlComponent}</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@SuppressWarnings("java:S1874") // ResultObject from external dependency; migration out of scope
public class CoreJsfTestConfiguration extends BasicApplicationConfiguration implements ComponentConfigurator {

    /** Test resource bundle path */
    public static final String TEST_BUNDLE_BASE_PATH = "de.cuioss.jsf.components.bundle.";

    /** cui-messages bundle path */
    public static final String CUI_BUNDLE_BASE_PATH = "de.cuioss.jsf.api.core.l18n.";

    @Override
    public void configureComponents(final ComponentConfigDecorator decorator) {
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
