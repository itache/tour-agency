package me.itache.dao.jdbc;

import me.itache.utils.db.pool.ConnectionPool;
import me.itache.model.entity.Token;
import me.itache.model.meta.Meta;
import me.itache.model.meta.TokenMeta;

public class TokenJdbcDao extends  AbstractJdbcDao<Token> {
    public static final Meta<Token> meta= TokenMeta.instance;

    public TokenJdbcDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    protected Meta<Token> getMeta() {
        return meta;
    }

    @Override
    protected Token getNew() {
        return new Token();
    }
}
