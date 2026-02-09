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
package de.cuioss.jsf.bootstrap.taginput;

import lombok.experimental.UtilityClass;

/**
 * Defines HTML5 data attributes for configuring the Selectize.js library 
 * on the client side for the {@link TagInputComponent}.
 * 
 * <p>These attributes connect JSF component configuration with the 
 * client-side Selectize.js initialization. They are primarily used within 
 * the {@link TagInputRenderer} to render appropriate HTML markup.</p>
 * 
 * <p>Example generated HTML:</p>
 * <pre>
 * &lt;input type="text" id="form:tagInput" 
 *        data-selectize="true" 
 *        data-cancreate="true"
 *        data-maxitems="5"
 *        data-options="[{value:'key1',label:'Option 1'}]" /&gt;
 * </pre>
 *
 * @author Sven Haag
 * @since 1.0
 * @see TagInputComponent
 * @see de.cuioss.jsf.bootstrap.selectize.Selectize
 */
@UtilityClass
public class PassthroughAttributes {

    /**
     * Data attribute that indicates whether Selectize.js should be initialized on the element.
     * <p>
     * Corresponds to the basic Selectize.js initialization.
     * </p>
     * <p>
     * Example: <code>data-selectize="true"</code>
     * </p>
     */
    static final String SELECTIZE = "data-selectize";

    /**
     * Data attribute that controls whether users can create new items not in the predefined options.
     * <p>
     * Corresponds to Selectize.js <code>create</code> option.
     * </p>
     * <p>
     * Example: <code>data-cancreate="true"</code>
     * </p>
     * 
     * @see de.cuioss.jsf.bootstrap.selectize.Selectize#OPTION_KEY_CREATE
     */
    static final String CAN_CREATE = "data-cancreate";

    /**
     * Data attribute that specifies the maximum number of items that can be selected.
     * <p>
     * Corresponds to Selectize.js <code>maxItems</code> option.
     * </p>
     * <p>
     * Example: <code>data-maxitems="5"</code>
     * </p>
     * 
     * @see de.cuioss.jsf.bootstrap.selectize.Selectize#OPTION_KEY_MAX_ITEMS
     */
    static final String MAX_ITEMS = "data-maxitems";

    /**
     * Data attribute that defines the delimiter character used to separate items.
     * <p>
     * Corresponds to Selectize.js <code>delimiter</code> option.
     * </p>
     * <p>
     * Example: <code>data-delimiter=","</code>
     * </p>
     * 
     * @see de.cuioss.jsf.bootstrap.selectize.Selectize#OPTION_KEY_DELIMITER
     */
    static final String DELIMITER = "data-delimiter";

    /**
     * Data attribute that specifies CSS classes for the Selectize wrapper element.
     * <p>
     * Corresponds to Selectize.js <code>wrapperClass</code> option.
     * </p>
     * <p>
     * Example: <code>data-wrapperclass="my-selectize-wrapper"</code>
     * </p>
     * 
     * @see de.cuioss.jsf.bootstrap.selectize.Selectize#OPTION_KEY_WRAPPER_CLASS
     */
    static final String WRAPPER_CLASS = "data-wrapperclass";

    /**
     * Data attribute that indicates whether to show a remove button for each selected item.
     * <p>
     * Controls inclusion of the Selectize.js <code>remove_button</code> plugin.
     * </p>
     * <p>
     * Example: <code>data-removebutton="true"</code>
     * </p>
     * 
     * @see de.cuioss.jsf.bootstrap.selectize.Selectize#OPTION_VALUE_PLUGIN_REMOVE_BUTTON
     */
    static final String REMOVE_BUTTON = "data-removebutton";

    /**
     * Data attribute that controls whether items created by the user persist in the dropdown
     * after they are unselected.
     * <p>
     * Corresponds to Selectize.js <code>persist</code> option.
     * </p>
     * <p>
     * Example: <code>data-persist="true"</code>
     * </p>
     * 
     * @see de.cuioss.jsf.bootstrap.selectize.Selectize#OPTION_KEY_PERSIST
     */
    static final String PERSIST = "data-persist";

    /**
     * Data attribute that provides JSON-formatted options for Selectize.js.
     * <p>
     * Corresponds to Selectize.js <code>options</code> array. This attribute contains
     * a JSON array of objects with value and label properties.
     * </p>
     * <p>
     * Example: <code>data-options="[{value:'key1',label:'Option 1'},{value:'key2',label:'Option 2'}]"</code>
     * </p>
     * 
     * @see de.cuioss.jsf.bootstrap.selectize.Selectize#OPTION_KEY_OPTIONS
     */
    static final String OPTIONS = "data-options";
}
