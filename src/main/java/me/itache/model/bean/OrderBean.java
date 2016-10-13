package me.itache.model.bean;

import me.itache.model.entity.Order;
import me.itache.model.entity.User;

public class OrderBean {
    private Order order;
    private User customer;
    private TourBean tour;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public TourBean getTour() {
        return tour;
    }

    public void setTour(TourBean tour) {
        this.tour = tour;
    }
}
