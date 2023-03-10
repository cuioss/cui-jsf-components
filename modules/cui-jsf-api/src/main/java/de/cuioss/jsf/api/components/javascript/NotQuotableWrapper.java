package de.cuioss.jsf.api.components.javascript;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Helper class to be used in conjunction with {@link JavaScriptOptions}. The
 * problem: Each value will automatically put into double quotes. If you wrap
 * the corresponding element in an instance of this type it will be taken
 * directly and not quoted.
 *
 * @author Oliver Wolff
 *
 */
@RequiredArgsConstructor
@ToString
public class NotQuotableWrapper implements Serializable {

    private static final long serialVersionUID = -6239824476027054699L;

    @Getter
    private final Serializable value;

}
