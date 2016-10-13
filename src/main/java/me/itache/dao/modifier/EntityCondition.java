package me.itache.dao.modifier;

import me.itache.dao.Identified;
import me.itache.model.meta.EntityField;

/**
 * Represents object to restrict entity objects by certain conditions
 */
public class EntityCondition<T extends Identified> {
    private Operator operator;
    private EntityField<T> field;
    private Object value;


    private EntityCondition(Operator operator, EntityField<T> field, Object value) {
        this.operator = operator;
        this.field = field;
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public EntityField getField() {
        return field;
    }

    public String getValue() {
        return value.toString();
    }

    /**
     * @param field field of entity object to restrict by
     * @param value value of field
     * @param <T>   type of entity
     * @return equals condition (field == value)
     */
    public static <T extends Identified> EntityCondition<T> eq(EntityField<T> field, Object value) {
        return new EntityCondition<>(Operator.EQ, field, value);
    }

    /**
     * @param field field of entity object to restrict by
     * @param value value of field
     * @param <T>   type of entity
     * @return greater than inclusively condition( field >= value)
     */
    public static <T extends Identified> EntityCondition<T> greaterThan(EntityField<T> field, Object value) {
        return new EntityCondition<>(Operator.GREATER, field, value);
    }

    /**
     * @param field field of entity object to restrict by
     * @param value value of field
     * @param <T>   type of entity
     * @return less than inclusively condition( field <= value)
     */
    public static <T extends Identified> EntityCondition<T> lessThan(EntityField<T> field, Object value) {
        return new EntityCondition<>(Operator.LESS, field, value);
    }

    /**
     * @param field field of entity object to restrict by
     * @param value value of field
     * @param <T>   type of entity
     * @return like condition( field like *value*)
     */
    public static <T extends Identified> EntityCondition<T> like(EntityField<T> field, Object value) {
        return new EntityCondition<>(Operator.LIKE, field, "%" + value + "%");
    }

    /**
     * @param field field of entity object to restrict by
     * @param value value of field
     * @param <T>   type of entity
     * @return not equals condition (field != value)
     */
    public static <T extends Identified> EntityCondition<T> neq(EntityField<T> field, Object value) {
        return new EntityCondition<>(Operator.NEQ, field, value);
    }

    public enum Operator {
        LESS("<="),
        GREATER(">="),
        LIKE("LIKE"),
        EQ("="),
        NEQ("<>");

        protected String operator;

        Operator(String operation) {
            this.operator = operation;
        }

        @Override
        public String toString() {
            return operator;
        }
    }
}
