package me.itache.utils.db.querybuilder;

import java.util.Map;

/**
 *
 */
public class UpdateQueryBuilder extends QueryBuilder {
    /**
     * Contains values in format -> columnName - placeholder
     */
    private Map<String, String> columns;

    public UpdateQueryBuilder(String table, Map<String, String> columns) {
        super(table);
        this.columns = columns;
    }

    @Override
    public QueryBuilder where(String column, String operator, String placeholder) {
        super.where(column, operator, placeholder);
        return this;
    }

    @Override
    protected String getBaseQuery() {
        StringBuilder sb = new StringBuilder("UPDATE `" + table + "` SET ");
        for (Map.Entry<String, String> column : columns.entrySet()) {
            sb.append(column.getKey() + " = @" +column.getValue() + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}
