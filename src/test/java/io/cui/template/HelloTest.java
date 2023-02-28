package io.cui.template;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class HelloTest {

    @Test
    void test() {
       assertEquals("Hello cui", new Hello().hello("cui"));
    }

}
