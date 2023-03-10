package de.cuioss.jsf.api.components.model.datalist.impl.support;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@SuppressWarnings({ "javadoc", "serial" })
@RequiredArgsConstructor
public class ExplodingModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Getter
    private final String name;

    public ExplodingModel(@SuppressWarnings("unused") final ExplodingModel other) {
        throw new RuntimeException();
    }

    public ExplodingModel() {
        throw new RuntimeException();
    }
}
