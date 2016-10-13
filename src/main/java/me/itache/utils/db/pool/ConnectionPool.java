package me.itache.utils.db.pool;

import java.sql.Connection;

/**
 * Provides interface to JDBC connection pool
 */
public interface ConnectionPool {
    /**
     * @return ready to use connection
     */
    Connection getConnection();

    /**
     * @param con connection to return to pool
     */
    void releaseConnection(Connection con);
}
