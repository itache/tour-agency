package me.itache.model.meta;

import me.itache.dao.Identified;
import me.itache.exception.ApplicationException;
import me.itache.utils.StringUtils;

import java.lang.reflect.Field;

/**
 * Represents entity object field and binds it to db table and column.
 * Forms entity column name by transformation entity field name from camelCase
 * to underscore_case.
 */
public class EntityField<T extends Identified> {
    protected String fieldName;
    private Meta<T> meta;

    /**
     * @param fieldName entity object field name
     * @param meta      meta object this field belongs to
     */
    EntityField(String fieldName, Meta<T> meta) {
        this.fieldName = fieldName;
        this.meta = meta;
    }

    public String getFieldName() {
        return fieldName;
    }

    /**
     * @return column name in format column_name
     */
    public String getColumnName() {
        return StringUtils.toUnderscore(fieldName);
    }

    /**
     * @return parent meta object
     */
    public Meta<T> getMeta() {
        return meta;
    }

    /**
     * Extract field value from given object.
     *
     * @param object to extract
     * @return field value
     * @throws ApplicationException if field not found in object
     */
    public Object extractValue(T object) {
        Field field;
        try {
            field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().isEnum()) {
                return field.get(object).toString();
            } else {
                return field.get(object);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Fill object field with given value.
     *
     * @param object to fill
     * @param value  field value
     * @throws ApplicationException if field not found in object
     */
    public void fillObject(T object, Object value) {
        Field field;
        try {
            Class clazz = object.getClass();
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().isEnum()) {
                field.set(object, Enum.valueOf((Class<Enum>) field.getType(), value.toString().toUpperCase()));
            } else {
                field.set(object, value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ApplicationException(e);
        }

    }
}
