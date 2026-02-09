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
package de.cuioss.jsf.bootstrap.icon;

import de.cuioss.jsf.api.components.css.IconLibrary;
import de.cuioss.jsf.api.components.partial.*;
import de.cuioss.jsf.api.components.support.LabelResolver;
import de.cuioss.jsf.api.components.util.CuiState;
import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.uimodel.model.Gender;
import jakarta.faces.component.FacesComponent;

/**
 * <p>
 * Specialized icon component for displaying gender-specific icons from the CUI icon library.
 * This component extends the base {@link IconComponent} and provides simplified attribute handling
 * for displaying gender indicators with consistent styling and localized tooltips.
 * </p>
 * 
 * <h2>Features</h2>
 * <ul>
 *   <li>Automatic rendering of gender-specific icons (male, female, diverse, other, etc.)</li>
 *   <li>Built-in mapping from string representations to appropriate icon CSS classes</li>
 *   <li>Automatic tooltip generation with proper i18n support</li>
 *   <li>Support for all standard icon styling attributes (size, state, etc.)</li>
 *   <li>Consistent visual representation across the application</li>
 * </ul>
 * 
 * <h2>Component Structure</h2>
 * <p>
 * The component renders a styled gender icon using the CUI icon system. It utilizes the 
 * base {@link IconComponent} rendering logic but provides specialized handling for:
 * </p>
 * <ul>
 *   <li>CSS class resolution based on gender value</li>
 *   <li>Automatic title/tooltip resolution using gender-specific resource bundle keys</li>
 * </ul>
 * 
 * <h2>Gender Resolution</h2>
 * <p>
 * Gender is resolved in the following order:
 * </p>
 * <ol>
 *   <li>If the <code>gender</code> attribute is set with a {@link Gender} enum value, that is used</li>
 *   <li>Otherwise, the <code>genderString</code> attribute is resolved to a {@link Gender} enum value</li>
 *   <li>If neither is available, or if an unrecognized string is provided, {@link Gender#UNKNOWN} is used</li>
 * </ol>
 * 
 * <h2>Attributes</h2>
 * <ul>
 *   <li>{@link TitleProvider} - For tooltip/title support (automatically populated)</li>
 *   <li>{@link ContextSizeProvider} - For icon size configuration (xs, sm, md, lg, xl)</li>
 *   <li>{@link ComponentStyleClassProvider} - For additional CSS styling</li>
 *   <li>{@link StyleAttributeProvider} - For inline CSS styling</li>
 *   <li>{@link ContextStateProvider} - For contextual styling (info, warning, danger, etc.)</li>
 *   <li><b>gender</b> - A {@link Gender} enum value that determines which icon to display</li>
 *   <li><b>genderString</b> - String representation of gender (alternative to gender attribute)</li>
 * </ul>
 * 
 * <h2>String Mappings</h2>
 * The <code>genderString</code> attribute accepts the following values:
 * <ul>
 *   <li>"m" or "male": Maps to {@link Gender#MALE}</li>
 *   <li>"f" or "female": Maps to {@link Gender#FEMALE}</li>
 *   <li>"o" or "other": Maps to {@link Gender#OTHER}</li>
 *   <li>"d" or "diverse": Maps to {@link Gender#DIVERSE}</li>
 *   <li>"x" or "undefined": Maps to {@link Gender#UNDEFINED}</li>
 *   <li>null or empty or any other value: Maps to {@link Gender#UNKNOWN}</li>
 * </ul>
 * 
 * <h2>Resource Bundle Integration</h2>
 * <p>
 * Tooltips are automatically resolved from the resource bundle using the following keys:
 * </p>
 * <ul>
 *   <li>cui.model.gender.male.title</li>
 *   <li>cui.model.gender.female.title</li>
 *   <li>cui.model.gender.undefined.title</li>
 *   <li>cui.model.gender.other.title</li>
 *   <li>cui.model.gender.diverse.title</li>
 *   <li>cui.model.gender.unknown.title</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * 
 * <p><b>Basic usage with string representation:</b></p>
 * <pre>
 * &lt;boot:genderIcon genderString="male" size="md" /&gt;
 * </pre>
 * 
 * <p><b>Using enum in backing bean:</b></p>
 * <pre>
 * &lt;boot:genderIcon gender="#{backingBean.gender}" state="info" size="lg" /&gt;
 * </pre>
 * 
 * <p><b>With custom styling:</b></p>
 * <pre>
 * &lt;boot:genderIcon genderString="female" styleClass="custom-style" style="margin: 4px;" /&gt;
 * </pre>
 *
 * @author Oliver Wolff
 * @see Gender
 * @see IconComponent
 */
@FacesComponent(BootstrapFamily.GENDER_ICON_COMPONENT)
public class GenderIconComponent extends IconComponent {

    private static final String GENDER_KEY = "gender";
    private static final String GENDER_STRING_KEY = "genderString";

    private final CuiState state;

    public GenderIconComponent() {
        state = new CuiState(getStateHelper());
    }

    /**
     * @return the gender
     */
    public Gender getGender() {
        return state.get(GENDER_KEY);
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(final Gender gender) {
        state.put(GENDER_KEY, gender);
    }

    /**
     * @return the genderString
     */
    public String getGenderString() {
        return state.get(GENDER_STRING_KEY);
    }

    /**
     * @param genderString the genderString to set
     */
    public void setGenderString(final String genderString) {
        state.put(GENDER_STRING_KEY, genderString);
    }

    @Override
    public String resolveIconCss() {
        return IconLibrary.resolveCssString(resolveGender().getCssClass());
    }

    @Override
    public String resolveTitle() {
        return LabelResolver.builder().withLabelKey(resolveGender().getLabelKey()).build().resolve(getFacesContext());
    }

    @Override
    public String getTitleKey() {
        return resolveGender().getLabelKey();
    }

    /**
     * @return the resolved gender, never null
     */
    public Gender resolveGender() {
        var gender = getGender();
        if (null == gender) {
            gender = Gender.fromString(getGenderString());
        }
        return gender;
    }

}
