package me.itache.utils;

/**
 * Holder for mail info.
 */
public class Mail {
    private String topic;
    private String message;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }
}
