package me.itache.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class LocalizedMailTest {
    @Test
    public void shouldFormCorrectEmailMessage(){
        Date now = new Date();
        LocalizedMail mail = new LocalizedMail(LocalizedMail.Type.ORDER_CREATED, "Some tour");
        Assert.assertEquals("You order the tour \"Some tour\". Our manager will contact you shortly.", mail.getMessage());
        Assert.assertEquals("You order the tour!", mail.getTopic());
    }
}
