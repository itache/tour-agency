package me.itache.utils.db.pool;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import me.itache.TestDataSourceInitializer;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 *
 */
public class DataSourceConnectionPoolTest {
    private static TransactionalConnectionPool connectionPool;
    @BeforeClass
    public static void init() {
        DataSource dataSource = TestDataSourceInitializer.getInstance().getDatasource();
        if(!DataSourceConnectionPool.isInit()) {
            DataSourceConnectionPool.init(dataSource);
        }
        connectionPool = DataSourceConnectionPool.getInstance();
    }

    @Test
    public void shouldAlwaysReturnSameConnectionOnTransaction() throws Exception {
        for(int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    connectionPool.beginTransaction();
                    Connection connection = connectionPool.getConnection();
                    for(int i = 0; i < 10; i++) {
                        Assert.assertTrue(connection == connectionPool.getConnection());
                    }
                    connectionPool.endTransaction();
                }
            }.start();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionOnCallBeginTransactionTwice() {
        connectionPool.beginTransaction();
        connectionPool.beginTransaction();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionOnCallEndTransactionBeforeBegin() {
        connectionPool.endTransaction();
    }

    @After
    public void endTransaction(){
        if(connectionPool.isTransactionStart()) {
            connectionPool.endTransaction();
        }
    }
}
