package me.itache.utils.db.querybuilder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SelectQueryBuilder extends QueryBuilder {
    private int offset;
    private int limit = 100;
    private List<String[]> orderClauses = new ArrayList<>();
    private String joinString = "";
    private boolean isCountQuery;
    private String[] columns;

    public SelectQueryBuilder(String table, String... columns) {
        super(table);
        this.columns = columns;
    }

    @Override
    public SelectQueryBuilder where(String column, String operator, String placeholder) {
        super.where(column, operator, placeholder);
        return this;
    }

    @Override
    public StringBuilder parseQuery() {
        StringBuilder sb = new StringBuilder(getBaseQuery());
        parseJoin(sb);
        parseWhere(sb);
        parseOrder(sb);
        if (!isCountQuery) {
            parseLimit(sb);
        }
        return sb;
    }

    private void parseJoin(StringBuilder sb) {
        sb.append(joinString);
    }

    public SelectQueryBuilder orderBy(String key, String order) {
        orderClauses.add(new String[]{key, order});
        return this;
    }

    public SelectQueryBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    public SelectQueryBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public SelectQueryBuilder join(String joinTableName, String key, String foreignKey) {
        joinString = joinString + " JOIN `" + joinTableName
                + "` ON " + this.table + "." + key + " = " + joinTableName + "." + foreignKey;
        return this;
    }

    public void count() {
        isCountQuery = true;
    }

    protected void parseOrder(StringBuilder sb) {
        if (orderClauses.size() != 0) {
            sb.append(" ORDER BY ");
        } else {
            return;
        }
        for (String[] order : orderClauses) {
            sb.append(order[0] + " " + order[1] + ", ");
        }
        sb.deleteCharAt(sb.length() - 2);
    }

    private void parseLimit(StringBuilder sb) {
        if (offset == 0) {
            sb.append(" LIMIT " + limit);
        } else {
            sb.append(" LIMIT " + offset + ", " + limit);
        }
    }

    @Override
    protected String getBaseQuery() {
        return "SELECT " + getColumnsString() + " FROM `" + table + "`";
    }

    private String getColumnsString() {
        if (isCountQuery) {
            return "COUNT(*)";
        }
        if (columns.length == 0) {
            return "*";
        }
        StringBuilder sb = new StringBuilder();
        for (String columnName : columns) {
            sb.append(columnName + ", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString();
    }
}
