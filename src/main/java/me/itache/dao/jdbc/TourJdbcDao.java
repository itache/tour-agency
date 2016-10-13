package me.itache.dao.jdbc;

import me.itache.constant.Message;
import me.itache.exception.DAOException;
import me.itache.helpers.LocaleManager;
import me.itache.model.entity.Tour;
import me.itache.model.meta.Meta;
import me.itache.model.meta.TourDescriptionMeta;
import me.itache.model.meta.TourMeta;
import me.itache.utils.db.pool.ConnectionPool;
import me.itache.utils.db.querybuilder.QueryBuilder;
import me.itache.utils.db.querybuilder.SelectQueryBuilder;

import java.util.Locale;

public class TourJdbcDao extends AbstractJdbcDao<Tour> {

    private static final String TOUR_DESCRIPTION_TABLE = TourDescriptionMeta.TABLE;

    private Locale locale;
    private Meta<Tour> metaObject = TourMeta.instance;

    public TourJdbcDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    /**
     * Creates DAO object for tour objects.
     * Locale uses for selection and ordering features by tour description fields.
     *
     * @param connectionPool
     * @param locale
     */
    public TourJdbcDao(ConnectionPool connectionPool, Locale locale) {
        super(connectionPool);
        if(locale == null) {
            this.locale = LocaleManager.instance().getDefault();
        }
        else if (!LocaleManager.instance().isSupported(locale)) {
            LOG.error(Message.ERR_LOCALE_NOT_SUPPORTED + ": " + locale);
            throw new DAOException(Message.ERR_LOCALE_NOT_SUPPORTED);
        } else {
            this.locale = locale;
        }
    }

    @Override
    protected Meta<Tour> getMeta() {
        return metaObject;
    }

    @Override
    protected Tour getNew() {
        return new Tour();
    }

    /**
     * @return select query builder joined to localized tour description
     * for searching and select purposes.
     */
    protected SelectQueryBuilder getSelectQueryBuilder() {
        SelectQueryBuilder queryBuilder = QueryBuilder.select(getTableName());
        if (locale != null) {
            queryBuilder.join(TOUR_DESCRIPTION_TABLE + "_" + locale.toLanguageTag(), metaObject.getIdField().getColumnName(), "tour_id");
        }
        return queryBuilder;
    }
}
