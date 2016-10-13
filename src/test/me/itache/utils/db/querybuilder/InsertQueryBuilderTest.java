package me.itache.utils.db.querybuilder;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class InsertQueryBuilderTest {
    @Test
    public void shouldFormValidQuery() {
        String expectedQuery = "INSERT INTO `group` (id, name) VALUES (@id, @name);";
        Map<String, String> columns = new LinkedHashMap<>();
        columns.put("id","id");
        columns.put("name", "name");
        String actualQuery = QueryBuilder.insert("group", columns)
                .getQuery();
        Assert.assertEquals(expectedQuery, actualQuery);
        //TODO
        //QueryBuilder.insert("group").values(Map<String, String>)
        //QueryBuilder.insert("group").value(String column, String placeholder).
    }
}
