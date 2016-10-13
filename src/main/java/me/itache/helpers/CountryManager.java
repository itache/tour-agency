package me.itache.helpers;

import me.itache.utils.db.pool.DataSourceConnectionPool;
import org.apache.log4j.Logger;
import me.itache.exception.ApplicationException;
import me.itache.model.meta.TourMeta;
import me.itache.utils.db.pool.ConnectionPool;
import me.itache.utils.db.querybuilder.QueryBuilder;

import java.sql.*;
import java.util.*;

/**
 * Provides methods to get country names in given locale
 */
public class CountryManager {
    private static final Logger LOG = Logger.getLogger(CountryManager.class);
    private static final String SELECT_COUNTRIES_QUERY = QueryBuilder.select(TourMeta.TABLE, TourMeta.COUNTRY_CODE.getColumnName()).getQuery();
    private static Set<String> availableCodes;

    /**
     * Returns map of all countries in which value - country code, key - country name in given locale.
     * @param locale
     * @return country map
     */
    public static Map<String, String> getAllCountries(Locale locale) {
        String[] locales = Locale.getISOCountries();
        return getCountryMap(locales, locale);
    }

    private static Map<String, String> getCountryMap(String[] codes, Locale locale) {
        TreeMap<String, String> countries = new TreeMap<>();
        for (String countryCode : codes) {
            Locale obj = new Locale("", countryCode);
            countries.put(obj.getDisplayCountry(locale), countryCode);
        }
        return countries;
    }

    /**
     * Returns map of countries to which tours available.
     * In map value - country code, key - country name in given locale.
     * @param locale
     * @return country map
     */
    public static Map<String, String> getAvailableCountries(Locale locale) {
        if(availableCodes == null) {
            fetchAvailableCodes();
        }
        return getCountryMap(availableCodes.toArray(new String[]{}), locale);
    }

    private static void fetchAvailableCodes() {
        ConnectionPool connectionPool = DataSourceConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(SELECT_COUNTRIES_QUERY)) {
            availableCodes = new TreeSet<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                availableCodes.add(rs.getString(1));
            }
        } catch (SQLException e) {
            LOG.error("Can not get countries");
            LOG.error(e);
            throw new ApplicationException(e);
        }
    }

    public static synchronized void refreshAvailable() {
        fetchAvailableCodes();
    }
}
