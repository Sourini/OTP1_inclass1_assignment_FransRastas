package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test void parseAcceptsDotCommaAndSpaces() {
        assertEquals(12.5, Main.parse(" 12.5 "), 1e-9);
        assertEquals(12.5, Main.parse("12,5"), 1e-9);
    }
    @Test void parseRejectsGarbage() { assertThrows(NumberFormatException.class, () -> Main.parse("abc")); }
    @Test void fmtTwoDecimals() { assertEquals("3,14", Main.fmt(3.14159)); }
    @Test void launcherClassExists() { assertNotNull(new Launcher()); }
}
