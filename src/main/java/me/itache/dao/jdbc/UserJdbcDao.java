package me.itache.dao.jdbc;

import me.itache.model.entity.User;
import me.itache.model.meta.Meta;
import me.itache.model.meta.UserMeta;
import me.itache.utils.db.pool.ConnectionPool;

public class UserJdbcDao extends AbstractJdbcDao<User> {

    private static final Meta<User> meta = UserMeta.instance;

    public UserJdbcDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected User getNew() {
        return new User();
    }

    @Override
    protected Meta<User> getMeta() {
        return meta;
    }

}
