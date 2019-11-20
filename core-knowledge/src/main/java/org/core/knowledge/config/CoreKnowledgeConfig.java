/**
 * 
 */
package org.core.knowledge.config;

import java.util.concurrent.TimeUnit;

import org.core.knowledge.service.RetryService.Receipt;
import org.core.knowledge.service.RetryService.Receipts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-20
 */
@Configuration
public class CoreKnowledgeConfig {
	
	public @Bean Retryer<Receipt> retryer(){
		return RetryerBuilder.<Receipt>newBuilder()
				.retryIfException().retryIfResult(Predicates.equalTo(Receipts.failureReceit()))
				.withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
				.withStopStrategy(StopStrategies.stopAfterAttempt(5))
				.build();
	}
}
