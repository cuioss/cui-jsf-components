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
 * Renders an Gender Icon regarding to the cui-icon contract. The icon is
 * rendered within a span with the according classes.
 * </p>
 * <h2>Usage</h2>
 *
 * <pre>
 * &lt;cui:genderIcon genderString="male" size="xl" state="info"/&gt;
 * </pre>
 *
 * *
 * <h2>Attributes</h2>
 * <ul>
 * <li>{@link TitleProvider}</li>
 * <li>{@link ContextSizeProvider}</li>
 * <li>{@link ComponentStyleClassProvider}</li>
 * <li>{@link StyleAttributeProvider}</li>
 * <li>{@link ContextStateProvider}</li>
 * <li>genderString: String representation of a gender:
 * <ul>
 * <li>if String is null or empty: {@link Gender#UNKNOWN}</li>
 * <li>if String is "m" or "male": {@link Gender#MALE}</li>
 * <li>if String is "f" or "female": {@link Gender#FEMALE}</li>
 * <li>if String is "o" or "other": {@link Gender#OTHER}</li>
 * <li>if String is "d" or "diverse": {@link Gender#DIVERSE}</li>
 * <li>if String is "x" or "undefined": {@link Gender#UNDEFINED}</li>
 * <li>For all other strings it returns {@link Gender#UNKNOWN}</li>
 * </ul>
 * In case #gender and #genderString is defined #gender takes precedence</li>
 * <li>gender: A representation on a concrete gender icon. In case #gender and
 * #genderString is defined #gender takes precedence.</li>
 * </ul>
 * <h2>Translation</h2> The keys for the title are implicitly defined:
 * <ul>
 * <li>cui.model.gender.male.title</li>
 * <li>cui.model.gender.female.title</li>
 * <li>cui.model.gender.undefined.title</li>
 * <li>cui.model.gender.other.title</li>
 * <li>cui.model.gender.diverse.title</li>
 * <li>cui.model.gender.unknown.title</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
@FacesComponent(BootstrapFamily.GENDER_ICON_COMPONENT)
public class GenderIconComponent extends IconComponent {

    private static final String GENDER_KEY = "gender";
    private static final String GENDER_STRING_KEY = "genderString";

    private final CuiState state;

    /**
     *
     */
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
