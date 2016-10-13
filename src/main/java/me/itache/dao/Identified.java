package me.itache.dao;

/**
 * Shows that object has primary key to store in DB
 */
public interface Identified {
    /**
     * @return object primary key
     */
    Long getId();

    void setId(Long id);
}
