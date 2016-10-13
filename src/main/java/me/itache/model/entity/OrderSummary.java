package me.itache.model.entity;

/**
 *
 */
public class OrderSummary {
    private String login;
    private int paid;
    private int reserved;

    public OrderSummary(String login, Integer paid, Integer reserved) {
        this.login = login;
        this.paid = paid == null ? 0 : paid;
        this.reserved = reserved == null ? 0 : reserved;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }
}
