package me.itache.model.meta;

import me.itache.dao.Identified;

import java.util.Locale;

/**
 * Holds meta information about entity of T class.
 */
public interface LocalizedMeta<T extends Identified> extends Meta<T> {
    /**
     * @return name of table that corresponds to T entity for given locale.
     */
    String getTableName(Locale locale);
}
