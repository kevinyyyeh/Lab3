package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    private final Map<String, String> languageCodeMap;

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(getClass()
                    .getClassLoader().getResource(filename)).toURI()));

            languageCodeMap = new HashMap<>();

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                String[] parts = line.split("\t");
                String countryCode = parts[1];
                String country = parts[0];
                languageCodeMap.put(countryCode, country);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        return languageCodeMap.get(code);
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        for (String value : languageCodeMap.values()) {
            if (value.equals(language)) {
                return value;
            }
        }
        return language;
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return languageCodeMap.size();
    }
}
