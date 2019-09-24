/**
 * 
 */
package org.core.knowledge.future;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-24
 */
public class TestBase2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PriceService priceDemo = new PriceService();
        Long start = System.currentTimeMillis();
        System.out.println(priceDemo.findPrices("product1"));
        System.out.println("服务耗时："+(System.currentTimeMillis()-start));
        System.out.println("--------------------------");
        Long start1 = System.currentTimeMillis();
        System.out.println(priceDemo.findPrices2("product1"));
        System.out.println("服务耗时："+(System.currentTimeMillis()-start1));
        System.out.println("--------------------------");
        Long start2 = System.currentTimeMillis();
        System.out.println(priceDemo.findPrices3("product1"));
        System.out.println("服务耗时："+(System.currentTimeMillis()-start2));
        System.out.println("--------------------------");
        Long start3 = System.currentTimeMillis();
        System.out.println(priceDemo.findPrices4("product1"));
        System.out.println("服务耗时："+(System.currentTimeMillis()-start3));
        System.out.println("--------------------------");
        Long start4 = System.currentTimeMillis();
        priceDemo.findPrices5("product1");
        System.out.println("服务耗时："+(System.currentTimeMillis()-start4));
	}

}
