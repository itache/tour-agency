package me.itache.dao;

import me.itache.dao.modifier.EntityCondition;
import me.itache.dao.modifier.Pagination;
import me.itache.dao.modifier.Sorter;
import me.itache.exception.DAOException;

import java.util.List;

/**
 * The unified state of persistent object management interface.
 * @param <T> type of object of persistent
 */
public interface GenericDAO<T extends Identified> {

    /**
     * Persists object in DB
     *
     * @param object object to persist
     * @return persisted object
     * @throws DAOException if object already exists in DB
     */
    T persist(T object);

    /**
     * Find object by primary key
     *
     * @param key object primary key
     * @return object with given key or null if object not found
     */
    T getByPK(long key);


    /**
     * Persist object state in DB
     *
     * @param object object to persist
     */
    boolean update(T object);

    /**
     * Delete object from DB.
     *
     * @param id object to delete
     */
    boolean delete(long id);

    /**
     * Return list of all object of type T from DB.
     *
     * @return all T objects
     */
    List<T> getAll();

    /**
     * Return list of all object that satisfy given conditions.
     * * In case controversial conditions use first one and ignore others.
     * (e.g. Condition.lessThan("ID", 5), Condition.lessThan("ID", 10)
     * will be used only first)
     *
     * @param conditions
     * @return list of objects
     */
    List<T> get(EntityCondition<T>... conditions);

    /**
     * Return count of all object that satisfy given conditions.
     * In case controversial conditions use first one and ignore others.
     * (e.g. Condition.lessThan("ID", 5), Condition.lessThan("ID", 10)
     * will be used only first)
     *
     * @param conditions
     * @return count of objects
     */
    int count(EntityCondition<T>... conditions);

    /**
     * Return list of all object that satisfy given conditions and sorted in given order.
     * * In case controversial conditions use first one and ignore others.
     * (e.g. Condition.lessThan("ID", 5), Condition.lessThan("ID", 10)
     * will be used only first)
     *
     * @param conditions
     * @param sorter
     * @return list of objects
     */
    List<T> get(Sorter sorter, EntityCondition<T>... conditions);

    /**
     * Return list of all object that satisfy given conditions, sorted in given order related to given page.
     * In case controversial conditions use first one and ignore others.
     * (e.g. Condition.lessThan("ID", 5), Condition.lessThan("ID", 10)
     * will be used only first)
     *
     * @param conditions
     * @param sorter
     * @param pagination
     * @return list of objects
     */
    List<T> get(Sorter sorter, Pagination pagination, EntityCondition<T>... conditions);

    /**
     * Delete all objects that satisfied given conditions
     * In case controversial conditions use first one and ignore others.
     * (e.g. Condition.lessThan("ID", 5), Condition.lessThan("ID", 10)
     * will be used only first)
     *
     * @param conditions
     * @return count deleted objects
     */
    int delete(EntityCondition<T>... conditions);
}
