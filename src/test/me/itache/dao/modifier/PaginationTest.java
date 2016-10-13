package me.itache.dao.modifier;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class PaginationTest {
    @Test
    public void shouldReturnCorrectPageCount(){
        Pagination pagination = new Pagination();
        pagination.setItemsCount(3);
        pagination.setItemsOnPageCount(2);
        Assert.assertEquals(2,pagination.getPageCount());
    }
}
