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
package de.cuioss.jsf.bootstrap.button;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cuioss.jsf.api.components.html.AttributeName;
import de.cuioss.jsf.api.components.html.AttributeValue;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import org.junit.jupiter.api.Test;

@VerifyComponentProperties(of = {"titleKey", "titleValue"})
@JsfTestConfiguration(CoreJsfTestConfiguration.class)
class CloseCommandButtonTest extends AbstractComponentTest<CloseCommandButton> {

    @Test
    void shouldProvidePassThroughAttributes() {
        var map = anyComponent().getPassThroughAttributes();
        assertTrue(map.containsKey(AttributeName.ARIA_LABEL.getContent()));
        assertEquals(AttributeValue.ARIA_CLOSE.getContent(), map.get(AttributeName.ARIA_LABEL.getContent()));
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.COMPONENT_FAMILY, anyComponent().getFamily());
        assertEquals(BootstrapFamily.CLOSE_COMMAND_BUTTON_RENDERER, anyComponent().getRendererType());
    }
}
