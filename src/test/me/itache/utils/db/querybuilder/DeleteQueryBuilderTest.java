package me.itache.utils.db.querybuilder;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class DeleteQueryBuilderTest {
    @Test
    public void shouldFormValidQuery() {
        String expectedQuery = "DELETE FROM `group` WHERE id = @id;";
        String actualQuery = QueryBuilder.delete("group")
                .where("id", "=", "id")
                .getQuery();
        Assert.assertEquals(expectedQuery, actualQuery);
    }
}
