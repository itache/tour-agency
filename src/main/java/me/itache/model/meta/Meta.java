package me.itache.model.meta;

import org.apache.log4j.Logger;
import me.itache.dao.Identified;

import java.util.HashMap;

/**
 * Holds meta information about entity of T class.
 */
public interface Meta<T extends Identified> {

    Logger LOG = Logger.getLogger(Meta.class);

    /**
     * @return all T entity fields except id field
     */
    EntityField<T>[] getFields();

    /**
     * @return field that identify T entity
     */
    EntityField<T> getIdField();

    /**
     * @return name of table that corresponds to T entity
     */
    String getTableName();

    /**
     * @param object to map
     * @return map of given object like "propertyName" => "value"
     */
    default HashMap<String, Object> getObjectMap(T object){
        HashMap<String, Object> objectMap = new HashMap<>();
        for (EntityField field : getFields()) {
            objectMap.put(field.getFieldName(), field.extractValue(object));
        }
        LOG.debug("Object map created:" + objectMap);
        return objectMap;
    }
}
