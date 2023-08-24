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
package de.cuioss.jsf.api.converter;

import de.cuioss.test.jsf.converter.AbstractConverterTest;
import de.cuioss.test.jsf.converter.TestItems;

class ComplexHtmlSanitizingConverterTest extends AbstractConverterTest<ComplexHtmlSanitizingConverter, String> {

    static final String SIMPLE_HTML = "<div><p>Test</p></div>";
    static final String MALICIOS_HTML = "<div><p>Test<script>alert('Hallo');</script></p></div>";

    @Override
    public void populate(final TestItems<String> testItems) {
        testItems.addValidObjectWithStringResult(SIMPLE_HTML, SIMPLE_HTML).addValidStringWithObjectResult(MALICIOS_HTML,
                SIMPLE_HTML);

    }
}
