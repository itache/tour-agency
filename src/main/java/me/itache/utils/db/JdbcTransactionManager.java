package me.itache.utils.db;

import me.itache.utils.db.pool.DataSourceConnectionPool;
import org.apache.log4j.Logger;
import me.itache.constant.Message;
import me.itache.exception.ApplicationException;
import me.itache.utils.db.pool.TransactionalConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * TransactionManager realization for JDBC
 */
public class JdbcTransactionManager implements TransactionManager {
    private static final Logger LOG = Logger.getLogger(JdbcTransactionManager.class);

    private TransactionalConnectionPool pool;
    private int isolationLevel = -1;

    public JdbcTransactionManager() {
        pool = DataSourceConnectionPool.getInstance();
    }

    @Override
    public <T> T execute(Operation<T> operation) {
        Connection connection = null;
        try {
            pool.beginTransaction();
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            int isolationLevel = (this.isolationLevel == -1) ? connection.getTransactionIsolation() : this.isolationLevel;
            connection.setTransactionIsolation(isolationLevel);
            T result = operation.execute();
            connection.commit();
            return result;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOG.error(ex.getMessage());
                throw new ApplicationException(ex);
            }
            LOG.error(Message.ERR_TRANSACTION_ROLLBACK + ". " + e.getMessage());
            throw new ApplicationException(e);
        } finally {
            pool.endTransaction();
        }
    }

    public void setIsolationLevel(int isolationLevel) {
        this.isolationLevel = isolationLevel;
    }
}
