/**
 * 
 */
package org.core.knowledge.service;

import org.core.knowledge.service.RetryService.Receipt;
import org.core.knowledge.service.RetryService.Receipts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-21
 */
@Slf4j
@Service
public class RetryServiceV2 {
	
	private @Autowired RetryTemplate retryTpl;
	
	public Receipt testRetry(String name) {
		try {
			this.retryTpl.registerListener(new RetryListener() {
				public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
					log.info("第一次重试之前调用");
					return true;
				}
				public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
						Throwable throwable) {
					log.info("每次异常时调用:"+context.getRetryCount());
				}
				public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
						Throwable throwable) {
					log.info("最后一次重试后调用:"+context.getRetryCount());
				}
			});
			
			Receipt result = retryTpl.execute(ctx -> {
				doWork();
				Receipt rec = Receipts.successReceit();
				rec.setData(name);
				return rec;
			});
			log.info("message => "+result.getMessage());
			return result;
		} catch (Exception e) {
			log.error("{}",e.getMessage());
			return Receipts.failureReceit();
		}
	}
	
	private void doWork() throws Exception {
		log.info("~~~call  flag~~~");
		throw new Exception("call remote peer");
	}
}
