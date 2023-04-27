package de.cuioss.jsf.bootstrap.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.cuioss.jsf.bootstrap.BootstrapFamily;
import de.cuioss.jsf.test.CoreJsfTestConfiguration;
import de.cuioss.test.jsf.component.AbstractUiComponentTest;
import de.cuioss.test.jsf.config.JsfTestConfiguration;
import de.cuioss.test.jsf.config.component.VerifyComponentProperties;
import de.cuioss.uimodel.model.Gender;

@JsfTestConfiguration(CoreJsfTestConfiguration.class)
@VerifyComponentProperties(of = { "gender", "genderString", "size", "state", "titleValue" })
class GenderIconComponentTest extends AbstractUiComponentTest<GenderIconComponent> {

    private static final String ICON_PREFIX = "cui-icon ";

    private static final String GENDER_UNKNOWN = ICON_PREFIX + Gender.UNKNOWN.getCssClass();

    public static final String GENDER_UNKNOWN_TITLE = "cui.model.gender.unknown.title";

    private static final String GENDER_MALE_CSS = ICON_PREFIX + Gender.MALE.getCssClass();

    public static final String GENDER_MALE_TITLE = "cui.model.gender.male.title";

    @Test
    void shouldResolveToUnknownIfNoneIsSet() {
        assertEquals(GENDER_UNKNOWN, anyComponent().resolveIconCss());
        assertEquals(GENDER_UNKNOWN_TITLE, anyComponent().resolveTitle());
    }

    @Test
    void shouldResolveToGender() {
        var underTest = anyComponent();
        underTest.setGender(Gender.MALE);
        assertEquals(GENDER_MALE_CSS, underTest.resolveIconCss());
        assertEquals(GENDER_MALE_TITLE, underTest.resolveTitle());
    }

    @Test
    void shouldResolveToGenderString() {
        var underTest = anyComponent();
        underTest.setGenderString("m");
        assertEquals(GENDER_MALE_CSS, underTest.resolveIconCss());
        assertEquals(GENDER_MALE_TITLE, underTest.resolveTitle());
    }

    @Test
    void shouldDefaultToInvalidGenderString() {
        var underTest = anyComponent();
        underTest.setGenderString("not.there");
        assertEquals(GENDER_UNKNOWN, underTest.resolveIconCss());
        assertEquals(GENDER_UNKNOWN_TITLE, underTest.resolveTitle());
    }

    @Test
    void shouldReturnGenderOverGenderString() {
        var underTest = anyComponent();
        underTest.setGender(Gender.MALE);
        underTest.setGenderString("f");
        assertEquals(GENDER_MALE_CSS, underTest.resolveIconCss());
        assertEquals(GENDER_MALE_TITLE, underTest.resolveTitle());
    }

    @Test
    void shouldProvideCorrectMetadata() {
        assertEquals(BootstrapFamily.ICON_COMPONENT_RENDERER, anyComponent().getRendererType());
    }
}
