package me.itache.utils.db.querybuilder;

import java.util.Map;

/**
 *
 */
public class InsertQueryBuilder extends QueryBuilder {
    /**
     * Contains values in format -> columnName - placeholder
     */
    private Map<String, String> columns;

    public InsertQueryBuilder(String table, Map<String, String> columns) {
        super(table);
        this.columns = columns;
    }

    @Override
    protected String getBaseQuery() {
        return "INSERT INTO `" + table + "`" + getColumnsString();
    }

    private String getColumnsString( ) {
        StringBuilder sb = new StringBuilder(" (");
        StringBuilder values = new StringBuilder("VALUES (");
        for (Map.Entry<String, String> column : columns.entrySet()) {
            sb.append( column.getKey() + ", ");
            values.append("@" +column.getValue() + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        values.delete(values.length() - 2, values.length());
        sb.append(") ");
        values.append(")");
        sb.append(values);
        return sb.toString();
    }
}
