/**
 * 
 */
package org.core.knowledge.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-24
 */
public class TestBase {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// 线程数: T = N * U * ( 1 + W/C )
		// N : cpu核数
		// U : cpu占比
		// 最佳线程数 = (线程等待时间与线程CPU时间之比 + 1) * CPU数目
		ExecutorService service = Executors.newFixedThreadPool(4);
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "test1-supply";
		},service);
		CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> System.out.println("test2-supply"));
		System.out.println("---------------------");
		future1.whenComplete((x,y) -> System.out.println("future1.whenComplete > "+x+","+y));
		future2.whenCompleteAsync((x,y) -> System.out.println("future2.whenCompleteAsync > "+x+","+y));
		future1.exceptionally(Throwable::toString);
		System.out.println("---------------------");
		CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> 10).thenApply(i -> 2*i);
		CompletableFuture<Integer> future4 = future3.handleAsync((i,j) -> 3*i);
		System.out.println(future4.get());
		System.out.println("---------------------");
		future4.thenAccept(System.out::println);
		future4.thenRunAsync(() -> System.out.println("next task"));
		System.out.println("---------------------");
		CompletableFuture<Integer> future5 = future3.thenCombine(future4, Integer::sum);
		future5.thenAccept(System.out::println);
		future3.thenAcceptBothAsync(future4, (x,y)-> System.out.println(x+","+y));
		System.out.println("---------------------");
		CompletableFuture<Integer> future6 = future3.applyToEither(future4, i -> i);
		future6.thenAccept(i -> System.out.println(i));
		future3.runAfterEitherAsync(future4,() -> System.out.println("thrid task"));
		System.out.println("---------------------");
		future3.thenComposeAsync(i -> CompletableFuture.supplyAsync(() -> i+1))
			.thenComposeAsync(i -> CompletableFuture.supplyAsync(() -> i+2))
			.thenCompose(i -> CompletableFuture.runAsync(() -> System.out.println("result -> "+i)));
	}

}
