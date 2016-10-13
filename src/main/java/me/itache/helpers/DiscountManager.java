package me.itache.helpers;

/**
 * Calculates order final price
 */
public class DiscountManager {

    /**
     * @param price
     * @param maxDiscount
     * @param discountStep
     * @return final price like percent from price. Percent calculates like (100 - final discount)
     * where final discount - random number between 0 and maxDiscount aliquot discountStep.
     */
    public static int calculateFinalPrice(int price, int maxDiscount, int discountStep){
        return calculateFinalPrice(price, calculateFinalDiscount(maxDiscount, discountStep));
    }

    /**
     *
     * @param price
     * @param discount
     * @return final price like percent from price. Percent calculates like (100 - discount).
     */
    public static int calculateFinalPrice(int price, int discount){
        return (int) ((int) price * (1 - (double)discount/100));
    }

    /**
     *
     * @param maxDiscount
     * @param discountStep
     * @return discount like random number between 0 and maxDiscount aliquot discountStep
     * or 0 If discountStep < 1.
     */
    public static int calculateFinalDiscount(int maxDiscount, int discountStep) {
        if(discountStep < 1) {
            return 0;
        }
        int nsteps = (maxDiscount+1) / discountStep;
        return  discountStep*(int)(nsteps*Math.random());
    }
}
