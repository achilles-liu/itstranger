/**
 * 
 */
package org.core.knowledge.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-24
 */
public class PriceService {
	
	private List<Shop> shops = Arrays.asList(new Shop("shop1"),
            new Shop("shop2"),
            new Shop("shop3"),
            new Shop("shop4"),
            new Shop("shop5"),
            new Shop("shop6"),
            new Shop("shop7"),
            new Shop("shop8"),
            new Shop("shop9"),
            new Shop("shop10"),
            new Shop("shop11"),
            new Shop("shop12"),
            new Shop("shop13"),
            new Shop("shop14"),
            new Shop("shop15"),
            new Shop("shop16")
    );
	
	/**
	 * 服务耗时：27797
	 * @param product
	 * @return
	 */
	public List<String> findPrices(String product){
        return shops.stream().map(shop -> String.format("%s price is %.2f ",shop.getName(),shop.getPice0(product)))
                .collect(Collectors.toList());
    }
	
	/**
	 * 服务耗时：8512
	 * @param product
	 * @return
	 */
	public List<String> findPrices2(String product){
		List<CompletableFuture<String>> priceFuture = shops.stream().map(shop -> CompletableFuture.supplyAsync(
				() -> String.format("%s price is %.2f ", shop.getName(), shop.getPice0(product))))
		.collect(Collectors.toList());
		return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	/**
	 * 服务耗时：2320
	 * @param product
	 * @return
	 */
	public List<String> findPrices3(String product){
		Executor executor = Executors.newCachedThreadPool();
		List<CompletableFuture<String>> priceFuture = shops.stream().map(shop -> CompletableFuture.supplyAsync(
				() -> String.format("%s price is %.2f ", shop.getName(), shop.getPice0(product)),executor))
		.collect(Collectors.toList());
		return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	/**
	 * 服务耗时：3226
	 * @param product
	 * @return
	 */
	public List<String> findPrices4(String product){
		Executor executor = Executors.newCachedThreadPool();
		List<CompletableFuture<String>> priceFuture = shops.stream()
	             .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPice(product), executor)
	                     .thenCombine(CompletableFuture.supplyAsync(() -> ExchangeService.getRate("USD", "CNY"), executor),
	                             (quote, rate) -> new Quote(quote.getShop(), quote.getPrice() * rate, quote.getDiscount())))//这返回的是异步处理
	             .map(future -> future.thenCompose(quote ->
	                     CompletableFuture.supplyAsync(() -> DiscountService.applyDiscount(quote), executor)))
	             .collect(Collectors.toList());
		return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	/**
	 * 服务耗时：14
	 * @param product
	 * @param executor
	 */
	public void findPrices5(String product){
		Executor executor = Executors.newCachedThreadPool();
		long start = System.currentTimeMillis();
		CompletableFuture<?>[] priceFuture = shops.stream()
	             .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPice(product), executor)
	                     .thenCombine(CompletableFuture.supplyAsync(() -> ExchangeService.getRate("USD", "CNY"), executor),
	                             (quote, rate) -> new Quote(quote.getShop(), quote.getPrice() * rate, quote.getDiscount())))//这返回的是异步处理
	             .map(future -> future.thenCompose(quote ->CompletableFuture.supplyAsync(() -> DiscountService.applyDiscount(quote), executor)))
	             .map(future -> future.thenAccept(i -> System.out.println(i + "(done in " + (System.currentTimeMillis() - start) + " msecs")))
	             .toArray(size->new  CompletableFuture[size]);
		CompletableFuture.allOf(priceFuture).thenAccept(i -> System.out.println(" all done"));
		CompletableFuture.anyOf(priceFuture).thenAccept(i -> System.out.println("fastest anyOf done " + i));
	}

}
