package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LocalizationTest {

    private static final List<Locale> SUPPORTED_LOCALES = List.of(
            Locale.of("en", "US"),
            Locale.of("fi", "FI"),
            Locale.of("sv", "SE"),
            Locale.of("ja", "JP"),
            Locale.of("ar", "SA")
    );

    private static final List<String> REQUIRED_KEYS = List.of(
            "title",
            "language",
            "temperature",
            "from",
            "to",
            "distance",
            "time",
            "convertSave",
            "fromColumn",
            "toColumn",
            "distanceColumn",
            "timeColumn",
            "speedColumn",
            "saved",
            "error",
            "selectUnits"
    );

    @Test
    void allSupportedLanguagesContainRequiredTranslations() {
        for (Locale locale : SUPPORTED_LOCALES) {
            ResourceBundle bundle =
                    ResourceBundle.getBundle("MessagesBundle", locale);

            assertNotNull(bundle);

            for (String key : REQUIRED_KEYS) {
                String translation = bundle.getString(key);

                assertNotNull(translation);
                assertFalse(
                        translation.isBlank(),
                        () -> "Translation is empty: " + locale + ", key: " + key
                );
            }
        }
    }
}