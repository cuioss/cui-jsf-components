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
package de.cuioss.jsf.api.components.partial;

/**
 * <h2>Summary</h2>
 * <p>
 * Implementors of this class manage the state and resolving of the
 * style-attribute. The implementation relies on the correct use of attribute
 * names, saying they must exactly match the accessor methods.
 * </p>
 * <h2>style</h2>
 * <p>
 * CSS style(s) to be applied when this component is rendered. <em>Caution</em>:
 * The styleClass attribute is always to be preferred.
 * </p>
 *
 * @author Oliver Wolff
 */
public interface StyleAttributeProvider {

    /**
     * @param style the style to set
     */
    void setStyle(String style);

    /**
     * @return the style-attribute
     */
    String getStyle();
}
