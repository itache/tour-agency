package me.itache.utils.db.querybuilder;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class SelectQueryBuilderTest {
    @Test
    public void shouldFormValidQueryWithoutWhere() {
        String expectedQuery = "SELECT * FROM `group` LIMIT 100;";
        String actualQuery = QueryBuilder.select("group")
                .getQuery();
        Assert.assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void shouldFormValidQueryWithJoin() {
        String expectedQuery = "SELECT * FROM `group` JOIN `desc` ON group.id = desc.g_id LIMIT 100;";
        String actualQuery = QueryBuilder.select("group").join("desc", "id", "g_id")
                .getQuery();
        Assert.assertEquals(expectedQuery, actualQuery);
    }
}
