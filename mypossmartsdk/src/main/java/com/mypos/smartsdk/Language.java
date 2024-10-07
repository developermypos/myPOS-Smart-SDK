package com.mypos.smartsdk;

/**
 * Describes supported currencies
 */

public enum Language {
    ENGLISH("EN"),
    BULGARIAN("BG"),
    CROATIAN("HR"),
    CZECH("CZ"),
    DANISH("DK"),
    DUTCH("DU"),
    FRENCH("FR"),
    GERMAN("DE"),
    GREEK("GR"),
    HUNGARIAN("HU"),
    ICELANDIC("IS"),
    ITALIAN("IT"),
    LATVIAN("LV"),
    LITHUANIAN("LT"),
    NORWEGIAN("NO"),
    POLISH("PL"),
    PORTUGUESE("PT"),
    ROMANIAN("RO"),
    SLOVENIAN("SL"),
    SPANISH("ES"),
    SWEDISH("SE");

    private final String lang;

    Language(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public static Language byTAG(String tag) {
        for (Language language : Language.values()) {
            if (language.getLang().equals(tag))
                return language;
        }

        return null;
    }
}
