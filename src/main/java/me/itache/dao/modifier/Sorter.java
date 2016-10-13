package me.itache.dao.modifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents holder for order conditions
 */
public class Sorter {
    private List<OrderCondition> conditions;

    /**
     * Creates new sorter object with sorting by field in given order
     *
     * @param field to sort by
     * @param order
     */
    public Sorter(String field, Order order) {
        conditions = new ArrayList<>();
        conditions.add(new OrderCondition(field, order));
    }

    /**
     * Add sorting condition to end of conditions list.
     *
     * @param field
     * @param order
     */
    public void addCondition(String field, Order order) {
        conditions.add(new OrderCondition(field, order));
    }

    /**
     * Add sorting condition to top of conditions list.
     *
     * @param field
     * @param order
     */
    public void addPrevailCondition(String field, Order order) {
        conditions.add(0, new OrderCondition(field, order));
    }

    public List<OrderCondition> getConditions() {
        return new ArrayList<>(conditions);
    }

    public enum Order {
        ASC,
        DESC,
    }

    public class OrderCondition {
        private String field;
        private Order order;

        public OrderCondition(String field, Order order) {
            this.field = field;
            this.order = order;
        }

        public String getField() {
            return field;
        }

        public String getOrder() {
            return order.toString();
        }
    }

}
