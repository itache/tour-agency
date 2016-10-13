package me.itache.dao.jdbc;

import me.itache.dao.DAOFactory;
import me.itache.model.entity.*;
import me.itache.utils.db.pool.ConnectionPool;
import me.itache.utils.db.pool.DataSourceConnectionPool;
import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import me.itache.dao.GenericDAO;
import me.itache.exception.ApplicationException;

import java.util.Locale;

/**
 * Factory of objects for working with database via JDBC driver
 */
public class JdbcDaoFactory extends DAOFactory {
    protected static final Logger LOG = Logger.getLogger(JdbcDaoFactory.class);
    private static final String resName = "jdbc/DB";
    private ConnectionPool connectionPool;

    public JdbcDaoFactory() {
        BasicDataSource dataSource;
        try {
            LOG.info("Trying to initialize DataSource object");
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7136825?characterEncoding=UTF-8");
            dataSource.setUsername("sql7136825");
            dataSource.setPassword("xHP73hcJJl");
        } catch (Exception e) {
            LOG.error("Can not initialize DataSource object");
            throw new ApplicationException(e);
        }
        DataSourceConnectionPool.init(dataSource);
        LOG.info("DataSource was successfully initialized.");
        connectionPool = DataSourceConnectionPool.getInstance();
    }

    @Override
    public GenericDAO<User> getUserDao() {
        return new UserJdbcDao(connectionPool);
    }

    @Override
    public GenericDAO<Tour> getTourDao() {
        return new TourJdbcDao(connectionPool);
    }

    @Override
    public GenericDAO<Tour> getTourDao(Locale locale) {
        return new TourJdbcDao(connectionPool, locale);
    }

    @Override
    public GenericDAO<Order> getOrderDao() {
        return new OrderJdbcDao(connectionPool);
    }

    @Override
    public GenericDAO<TourDescription> getTourDescriptionDao(Locale locale) {
        return new TourDescriptionJdbcDao(connectionPool, locale);
    }

    @Override
    public GenericDAO<Token> getTokenDao() {
        return new TokenJdbcDao(connectionPool);
    }
}
