package me.itache.utils.db.querybuilder;

import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Provides fabric methods to create builder for specific purpose.
 */
public abstract class QueryBuilder {
    protected static final Logger LOG = Logger.getLogger(QueryBuilder.class);
    protected final String table;

    private StringBuilder whereSB;

    protected QueryBuilder(String table) {
        this.table = table;
    }

    public static SelectQueryBuilder select(String table) {
        return new SelectQueryBuilder(table);
    }

    public static SelectQueryBuilder select(String table, String... columns) {
        SelectQueryBuilder builder = new SelectQueryBuilder(table, columns);
        return builder;
    }

    public static UpdateQueryBuilder update(String table, Map<String, String> columns) {
        UpdateQueryBuilder qb = new UpdateQueryBuilder(table, columns);
        return qb;
    }

    public static DeleteQueryBuilder delete(String table) {
        return new DeleteQueryBuilder(table);
    }

    public static InsertQueryBuilder insert(String table, Map<String, String> columns) {
        InsertQueryBuilder qb = new InsertQueryBuilder(table, columns);
        return qb;
    }

    public QueryBuilder where(String column, String operator, String placeholder) {
        String conjunct;
        if (whereSB == null) {
            whereSB = new StringBuilder();
            conjunct = " WHERE";
        } else {
            conjunct = "AND";
        }
        whereSB.append(conjunct + " " + column + " " + operator + " @" + placeholder + " ");
        return this;
    }

    protected StringBuilder parseQuery() {
        StringBuilder sb = new StringBuilder(getBaseQuery());
        parseWhere(sb);
        return sb;
    }

    public String getQuery() {
        StringBuilder sb = parseQuery();
        sb.append(";");
        LOG.debug("Query constructed:" + sb.toString());
        return sb.toString();
    }

    protected void parseWhere(StringBuilder sb) {
        if (whereSB != null) {
            sb.append(whereSB);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    protected abstract String getBaseQuery();
}

