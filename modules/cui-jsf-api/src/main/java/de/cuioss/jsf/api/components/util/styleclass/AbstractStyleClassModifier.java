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
package de.cuioss.jsf.api.components.util.styleclass;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;

import de.cuioss.tools.string.Joiner;
import de.cuioss.tools.string.MoreStrings;
import de.cuioss.tools.string.Splitter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Base implementation for dealing with style class attribute.
 *
 * @author Oliver Wolff
 */
@ToString
@EqualsAndHashCode
public abstract class AbstractStyleClassModifier implements StyleClassModifier {

    private static final char SEPARATOR = ' ';

    /** {@inheritDoc} */
    @Override
    public String toggleStyleClass(String styleClass) {
        requireNotEmpty(styleClass);
        var classList = readListOfClasses();
        if (classList.isEmpty() || !classList.contains(styleClass)) {
            classList.add(styleClass);
        } else {
            classList.remove(styleClass);
        }
        return writeStyleClass(classList);
    }

    /** {@inheritDoc} */
    @Override
    public String addStyleClass(String styleClass) {
        requireNotEmpty(styleClass);
        var classList = readListOfClasses();
        if (!classList.contains(styleClass)) {
            classList.add(styleClass);
        }
        return writeStyleClass(classList);
    }

    /** {@inheritDoc} */
    @Override
    public String removeStyleClass(String styleClass) {
        requireNotEmpty(styleClass);
        var classList = readListOfClasses();
        classList.remove(styleClass);
        return writeStyleClass(classList);
    }

    protected String writeStyleClass(List<String> classList) {
        var result = MoreStrings.emptyToNull(Joiner.on(SEPARATOR).skipNulls().join(classList));
        setStyleClass(result);
        return result;
    }

    protected List<String> readListOfClasses() {
        var styleClass = getStyleClass();
        if (null == styleClass || styleClass.isEmpty()) {
            return new ArrayList<>();
        }
        return Splitter.on(SEPARATOR).splitToList(styleClass);
    }

    /**
     * The actual writing / storing of the StyleClass
     *
     * @param styleClass to be set
     */
    public abstract void setStyleClass(String styleClass);

    /**
     * @return actual stored StyleClass
     */
    public abstract String getStyleClass();
}
