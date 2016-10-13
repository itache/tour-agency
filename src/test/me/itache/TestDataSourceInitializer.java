package me.itache;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;


/**
 *
 */
public class TestDataSourceInitializer {

    private static TestDataSourceInitializer instance;

    private DataSource datasource;

    private TestDataSourceInitializer() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/final_test?useUnicode=yes&characterEncoding=utf8");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        datasource = new DataSource();
        datasource.setPoolProperties(p);
    }

    public static TestDataSourceInitializer getInstance() {
        synchronized (TestDataSourceInitializer.class) {
            if (instance == null) {
                instance = new TestDataSourceInitializer();
            }
        }
        return instance;
    }

    public DataSource getDatasource(){
        return datasource;
    }

}
