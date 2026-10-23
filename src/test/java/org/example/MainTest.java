package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    @Test
    void parsesAndFormatsNumbers() {
        assertEquals(
                12.5,
                Main.parse(" 12,5 "),
                0.001
        );

        assertEquals(
                "3.14",
                Main.format(3.14159)
        );

        assertThrows(
                NumberFormatException.class,
                () -> Main.parse("not a number")
        );
    }
}