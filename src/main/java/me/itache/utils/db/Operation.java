package me.itache.utils.db;

/**
 * Database operation interface
 */
public interface Operation<T> {

    T execute();
}
