/**
 * 
 */
package org.core.knowledge.service;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-22
 */
@Slf4j
@Component
public class Callback {
	
	@Slf4j
	@Component("v3RetryListener")
	public static class V3RetryListener implements RetryListener{

		public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
			log.info("第一次重试之前调用");
			return true;
		}

		public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
				Throwable throwable) {
			log.info("最后一次重试后调用:"+context.getRetryCount());
		}

		public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
				Throwable throwable) {
			log.info("每次异常时调用:"+context.getRetryCount());
		}
		
	}
	
	@Async
	@Retryable(value=Exception.class,maxAttempts=5,
		backoff=@Backoff(delay=3000),
		listeners= {"v3RetryListener"})
	public void doWork(String name){
		log.info("~~~call back start~~~");
		log.info("name => {}",name);
		log.info("~~~call back  done~~~");
		throw new RuntimeException("call remote peer");
	}
	
}
