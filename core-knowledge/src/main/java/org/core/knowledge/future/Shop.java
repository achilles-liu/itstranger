/**
 * 
 */
package org.core.knowledge.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-24
 */
public class Shop {
	private String name;
	Random random = new Random();
	
	public Shop(String name) {
		this.name = name;
	}
	
	public static void delay() {
		Random random = new Random();
		int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	public double getPice0(String product) {
        delay();
        return random.nextDouble() * 100;
    }
	
	public Quote getPice(String product) {
        delay();
        return new Quote(this,random.nextDouble() * 100,Discount.SILVER);
    }
 
    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> (getPice0(product)));
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
}
