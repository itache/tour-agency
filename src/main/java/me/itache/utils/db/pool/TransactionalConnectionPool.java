package me.itache.utils.db.pool;

/**
 * Provides interface to connection pool with transaction support
 */
public interface TransactionalConnectionPool extends ConnectionPool {

    void beginTransaction();

    void endTransaction();

    boolean isTransactionStart();
}
