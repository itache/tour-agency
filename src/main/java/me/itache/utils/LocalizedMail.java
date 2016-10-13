package me.itache.utils;

import me.itache.helpers.LocaleManager;

import java.util.ResourceBundle;

/**
 * Contains all topic & message combination for users for current locale
 *
 * @author karpachev vitaliy
 */
public class LocalizedMail extends Mail {
    private static final String MAIL_BUNDLE = "mail";

    private ResourceBundle bundle;
    private Type type;
    private Object[] values;

    /**
     * Creates mail of given type and fill it with values
     *
     * @param type
     * @param values
     */
    public LocalizedMail(Type type, Object... values) {
        bundle = ResourceBundle.getBundle(MAIL_BUNDLE, LocaleManager.instance().getCurrentLocale());
        this.type = type;
        this.values = values;
    }

    public String getTopic() {
        return bundle.getString(type.toString() + ".topic");
    }

    public String getMessage() {
        return String.format(bundle.getString(type.toString() + ".message"), values);
    }

    public enum Type {
        ORDER_STATUS_CHANGED,
        ORDER_CREATED,
        USER_SING_UP,
        RESEND_CONFIRMATION,
        RESET_PASSWORD;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
