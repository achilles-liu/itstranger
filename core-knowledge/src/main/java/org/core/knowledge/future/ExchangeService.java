/**
 * 
 */
package org.core.knowledge.future;

import java.util.Random;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-24
 */
public class ExchangeService {
	
	public static void delay() {
		Random random = new Random();
		int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
    public static double getRate(String source,String target){
        delay();
        return 10;
    }
}
