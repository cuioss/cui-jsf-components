package de.cuioss.jsf.api.components.util.modifier.support;

import java.io.Serializable;

import javax.faces.component.UIComponentBase;

import de.cuioss.jsf.api.components.partial.TitleProvider;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class TitleProviderImpl extends UIComponentBase implements TitleProvider {

    @Getter
    private String titleValue;

    @Getter
    @Setter
    private String title;

    @Override
    public String getFamily() {
        return null;
    }

    @Override
    public void setTitleKey(final String titleKey) {
    }

    @Override
    public String getTitleKey() {
        return null;
    }

    @Override
    public void setTitleValue(final Serializable titelValue) {
        this.titleValue = (String) titelValue;
    }

    @Override
    public Object getTitleConverter() {
        return null;
    }

    @Override
    public void setTitleConverter(final Object titelConverter) {
    }

    @Override
    public String resolveTitle() {
        return titleValue;
    }

    @Override
    public boolean isTitleSet() {
        return null != titleValue;
    }
}
