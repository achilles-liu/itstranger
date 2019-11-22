/**
 * 
 */
package org.core.knowledge.service;

import org.core.knowledge.service.RetryService.Receipt;
import org.core.knowledge.service.RetryService.Receipts;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RetryServiceV3 {
	
	private @Autowired Callback callback;
	
	public Receipt testRetry(String name) {
		callback.doWork(name);
		return doWorkSync(name);
	}
	
	public Receipt doWorkSync(String name) {
		log.info("~~~do work sync start~~~");
		log.info("db operation => {}",name);
		log.info("~~~do work sync  done~~~");
		return Receipts.successReceit();
	}
}
