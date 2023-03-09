package com.icw.ehf.cui.core.api.components.model.datalist.impl.support;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@SuppressWarnings({ "javadoc", "serial" })
@RequiredArgsConstructor
public class MissingDefaultConstructor implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Getter
    private final String name;
}
