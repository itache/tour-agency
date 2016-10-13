package me.itache.utils.db.querybuilder;

/**
 *
 */
public class DeleteQueryBuilder extends QueryBuilder {
    public DeleteQueryBuilder(String table) {
        super(table);
    }

    @Override
    public DeleteQueryBuilder where(String column, String operator, String placeholder) {
        super.where(column, operator, placeholder);
        return this;
    }

    @Override
    protected String getBaseQuery() {
        return "DELETE FROM `" + table + "`";
    }
}
