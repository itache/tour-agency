package me.itache.helpers;

import org.junit.Assert;
import org.junit.Test;


public class DiscountManagerTest {
    @Test
    public void shouldProduceCorrectFinalPrice() {
        int steps = 1000;
        for(int i = 0; i < steps; i++) {
            Assert.assertTrue(DiscountManager.calculateFinalPrice(100,10,2) >= 90);
        }
    }

    @Test
    public void shouldProduceCorrectFinalDiscount() {
        int steps = 1000;
        for(int i = 0; i < steps; i++) {
            Assert.assertTrue(DiscountManager.calculateFinalDiscount(15,3) % 3 == 0);
        }
    }

    @Test
    public void shouldProduceCorrectFinalPriceByDiscount() {
        Assert.assertTrue(DiscountManager.calculateFinalPrice(150,3) == 145);
        Assert.assertTrue(DiscountManager.calculateFinalPrice(47,1) == 46);
        Assert.assertTrue(DiscountManager.calculateFinalPrice(113,5) == 107);
    }
}
