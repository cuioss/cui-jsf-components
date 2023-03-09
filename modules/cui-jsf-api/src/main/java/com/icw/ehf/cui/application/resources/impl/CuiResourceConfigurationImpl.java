package com.icw.ehf.cui.application.resources.impl;

import java.util.List;

import com.icw.ehf.cui.application.resources.CuiResourceConfiguration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Simple default implementation for {@link CuiResourceConfiguration} to be
 * managed completely within facesConfig
 *
 * @author Oliver Wolff
 *
 */
@EqualsAndHashCode
@ToString
public class CuiResourceConfigurationImpl implements CuiResourceConfiguration {

    private static final long serialVersionUID = -6989079897199012765L;

    /**
     * Bean name for looking up instances.
     */
    public static final String BEAN_NAME = "cuiResourceConfiguration";

    @Getter
    @Setter
    private String version;

    @Getter
    @Setter
    private List<String> handledLibraries;

    @Getter
    @Setter
    private List<String> handledSuffixes;
}
