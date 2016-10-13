package me.itache.dao;

import me.itache.model.entity.*;
import me.itache.dao.jdbc.JdbcDaoFactory;

import java.util.Locale;

/**
 * Factory of objects for working with database
 */
public abstract class DAOFactory {
    private static DAOFactory factory;
    public static DAOFactory instance() {
        return factory;
    }

    public static void init(Factory factoryName) {
        factory = factoryName.getFactory();
    }

    public abstract GenericDAO<User> getUserDao();
    public abstract GenericDAO<Tour> getTourDao();
    public abstract GenericDAO<Tour> getTourDao(Locale locale);
    public abstract GenericDAO<Order> getOrderDao();
    public abstract GenericDAO<TourDescription> getTourDescriptionDao(Locale locale);
    public abstract GenericDAO<Token> getTokenDao();


    public enum Factory{
        JDBC() {
            @Override
            DAOFactory getFactory() {
                return new JdbcDaoFactory();
            }
        };

        abstract DAOFactory getFactory();

    }
}
