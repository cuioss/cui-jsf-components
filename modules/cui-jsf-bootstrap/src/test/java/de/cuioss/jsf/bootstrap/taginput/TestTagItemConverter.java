package de.cuioss.jsf.bootstrap.taginput;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;

@SuppressWarnings("javadoc")
@FacesConverter(TestTagItemConverter.ID)
public class TestTagItemConverter implements Converter<ConceptKeyType> {

    public static final String ID = "test.TagItemConverter";

    @Override
    public ConceptKeyType getAsObject(final FacesContext context, final UIComponent component,
            final String value) {
        return new TestConceptKey();
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component,
            final ConceptKeyType value) {
        return "test";
    }
}
