package me.itache.model.meta;

import me.itache.model.entity.Order;

/**
 * Represents Order entity object data.
 */
public class OrderMeta implements Meta<Order> {
    private static final String TABLE = "order";
    public static final Meta<Order> instance = new OrderMeta();
    public static final EntityField<Order> ID = new EntityField<>("id", instance);
    public static final EntityField<Order> CUSTOMER_ID = new EntityField<>("customerId", instance);
    public static final EntityField<Order> STATUS = new EntityField<>("status", instance);
    public static final EntityField<Order> DATE = new EntityField<>("date", instance);
    public static final EntityField<Order> TOUR_ID = new EntityField<>("tourId", instance);
    public static final EntityField<Order> DISCOUNT = new EntityField<>("discount", instance);
    public static final EntityField<Order> PRICE = new EntityField<>("price", instance);

    private OrderMeta() {
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public EntityField<Order>[] getFields() {
        return new EntityField[]{
                TOUR_ID, CUSTOMER_ID, STATUS, DATE, DISCOUNT, PRICE
        };
    }

    @Override
    public EntityField<Order> getIdField() {
        return ID;
    }

}
