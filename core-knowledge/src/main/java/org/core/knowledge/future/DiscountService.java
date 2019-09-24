/**
 * 
 */
package org.core.knowledge.future;

import java.text.NumberFormat;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-24
 */
public class DiscountService {
	
	public static void delay() {
	       
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String applyDiscount(Quote quote){
        return quote.getShop()+" price is " +apply(quote.getPrice(),quote.getDiscount());
    }
 
    private static String apply(double price,Discount discussion) {
        delay();
        return NumberFormat.getInstance().format(price * (100 - discussion.getPercent())/100);
    }
}
