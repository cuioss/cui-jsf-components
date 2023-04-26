package de.cuioss.jsf.bootstrap.common.partial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ColumnCssResolverTest {

    @Test
    void test() {
        var underTest = new ColumnCssResolver(1, 3, true, null);
        Assertions.assertEquals("col-md-1 col-md-offset-3", underTest.resolveColumnCss().getStyleClass());
    }
}
