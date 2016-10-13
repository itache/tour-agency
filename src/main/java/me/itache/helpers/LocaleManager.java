package me.itache.helpers;

import java.util.HashMap;
import java.util.Locale;

/**
 * Manages application locales.
 */
public class LocaleManager {
    private static LocaleManager instance;

    private static HashMap<String, Locale> locales = new HashMap<String, Locale>();
    private static Locale defaultLocale = Locale.forLanguageTag("en");
    private ThreadLocal<Locale> currentLocale;

    static {
        locales.put("English", Locale.forLanguageTag("en"));
        locales.put("Русский", Locale.forLanguageTag("ru"));
    }

    /**
     * @return instance of LocaleManager
     */
    public synchronized static LocaleManager instance() {
        if (instance == null) {
            instance = new LocaleManager();
        }
        return instance;
    }

    /**
     * @return map of available locales. Key - language name, value - locale
     */
    public HashMap<String, Locale> getAvailableLocales() {
        return new HashMap<>(locales);
    }

    /**
     * @param locale
     * @return true if given locale supported by application, false - otherwise
     */
    public boolean isSupported(Locale locale) {
        return locales.containsValue(locale);
    }

    /**
     * @return application default locale
     */
    public Locale getDefault() {
        return defaultLocale;
    }

    /**
     * @return current locale if it is set or default - otherwise
     */
    public Locale getCurrentLocale() {
        if (currentLocale == null || currentLocale.get() == null) {
            return defaultLocale;
        }
        return currentLocale.get();
    }

    /**
     * Sets given locale as current for request.
     * @param locale
     */
    public synchronized void setCurrentLocale(Locale locale) {
        currentLocale = new ThreadLocal<>();
        currentLocale.set(locale);
    }
}
