package me.itache.dao.jdbc;

import me.itache.constant.Message;
import me.itache.exception.DAOException;
import me.itache.helpers.LocaleManager;
import me.itache.model.meta.Meta;
import me.itache.utils.db.pool.ConnectionPool;
import me.itache.model.entity.TourDescription;
import me.itache.model.meta.LocalizedMeta;
import me.itache.model.meta.TourDescriptionMeta;

import java.util.Locale;

public class TourDescriptionJdbcDao extends AbstractJdbcDao<TourDescription> {

    private static final LocalizedMeta<TourDescription> metaObject = TourDescriptionMeta.instance;
    private Locale locale;

    /**
     * Creates DAO object for given locale.
     *
     * @param connectionPool connections poll to database.
     * @param locale
     * @throws DAOException if location not supported.
     */
    public TourDescriptionJdbcDao(ConnectionPool connectionPool, Locale locale) {
        super(connectionPool);
        if( locale == null) {
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
    protected String getTableName() {
        return metaObject.getTableName(locale);
    }

    @Override
    protected Meta<TourDescription> getMeta() {
        return metaObject;
    }

    @Override
    protected TourDescription getNew() {
        return new TourDescription();
    }

    @Override
    protected Locale getLocale() {
        return locale;
    }
}
