package me.itache.utils.db;

/**
 * Provides interface to manage transaction
 */
public interface TransactionManager {

    /**
     * All actions inside operation committed in one transaction and
     * rollback on failure.
     *
     * @param operation
     */
    <T> T execute(Operation<T> operation);
}
