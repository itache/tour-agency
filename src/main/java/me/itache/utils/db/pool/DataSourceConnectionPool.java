package me.itache.utils.db.pool;

import org.apache.log4j.Logger;
import me.itache.constant.Message;
import me.itache.exception.ApplicationException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection pool based on DataSource object
 */
public class DataSourceConnectionPool implements TransactionalConnectionPool {
    protected static final Logger LOG = Logger.getLogger(DataSourceConnectionPool.class);
    private static TransactionalConnectionPool instance;

    /**
     * Holds connection to provide transaction
     */
    private ThreadLocal<Connection> transactionConnection;

    private DataSource dataSource;

    private DataSourceConnectionPool(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    static synchronized public TransactionalConnectionPool getInstance() {
        if (instance == null) {
            LOG.error(Message.ERR_POOL_NOT_INIT_ALREADY);
            throw new ApplicationException(Message.ERR_POOL_NOT_INIT_ALREADY);
        }
        return instance;
    }

    public static synchronized void init(DataSource dataSource) {
        if (instance != null) {
            LOG.error(Message.ERR_POOL_INIT_ALREADY);
            throw new ApplicationException(Message.ERR_POOL_INIT_ALREADY);
        }
        instance = new DataSourceConnectionPool(dataSource);
    }

    public static synchronized boolean isInit() {
        return instance != null;
    }

    @Override
    public synchronized Connection getConnection() {
        if (transactionConnection != null) {
            return transactionConnection.get();
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("Unable get connection");
            throw new ApplicationException(e);
        }
    }

    @Override
    public synchronized void releaseConnection(Connection con) {
        if (transactionConnection != null) {
            return;
        }
        try {
            con.close();
        } catch (SQLException e) {
            LOG.error("Unable close connection");
            throw new ApplicationException(e);
        }
    }

    @Override
    public void beginTransaction() {
        LOG.debug("Transaction begin");
        if (transactionConnection != null) {
            LOG.error(Message.ERR_TRANSACTION_EXISTS);
            throw new IllegalStateException(Message.ERR_TRANSACTION_EXISTS);
        }
        Connection connection = getConnection();
        transactionConnection = new ThreadLocal<>();
        transactionConnection.set(connection);
    }

    @Override
    public void endTransaction() {
        if (transactionConnection == null) {
            LOG.error(Message.ERR_TRANSACTION_NOT_EXISTS);
            throw new IllegalStateException(Message.ERR_TRANSACTION_NOT_EXISTS);
        }
        releaseConnection(transactionConnection.get());
        transactionConnection = null;
        LOG.debug("Transaction end");
    }

    @Override
    public boolean isTransactionStart() {
        return transactionConnection != null;
    }
}

