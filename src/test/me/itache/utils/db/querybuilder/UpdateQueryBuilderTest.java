package me.itache.utils.db.querybuilder;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class UpdateQueryBuilderTest {
    @Test
    public void shouldFormValidQuery() {
        String expectedQuery = "UPDATE `group` SET name = @name, price = @price WHERE id = @id;";
        Map<String, String> columns = new LinkedHashMap<>();
        columns.put("name", "name");
        columns.put("price","price");
        String actualQuery = QueryBuilder.update("group", columns)
                .where("id", "=", "id")
                .getQuery();
        Assert.assertEquals(expectedQuery, actualQuery);
    }
}
