package me.itache.utils;

import javax.servlet.http.HttpSession;

import static me.itache.constant.Common.NOTIFICATION_SESSION_PARAM;

/**
 * Holder for notification data
 */
public class Notification {
    private String code;
    private Type type;
    private String value;

    /**
     * Factory method to create Notification
     * @param session session within notification creates
     * @param code code of action
     * @param type type of Notification
     * @param value value to fill notification message
     */
    public static void create(HttpSession session, String code, Type type, String value) {
        session.setAttribute(NOTIFICATION_SESSION_PARAM, new Notification(code, type, value));
    }

    /**
     * Factory method to create Notification
     * @param session session within notification creates
     * @param code code of action
     * @param type type of Notification
     */
    public static void create(HttpSession session, String code, Type type) {
        create(session, code, type, null);
    }

    /**
     * @param code code of localized notification message
     * @param type type of notification
     * @param value value to format notification message
     */
    public Notification(String code, Type type, String value) {
        this.code = code;
        this.type = type;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public enum Type {
        ERROR,
        SUCCESS;
    }
}
