/**
 * 
 */
package org.core.knowledge.controller;

import org.core.knowledge.service.RetryService;
import org.core.knowledge.service.RetryService.Receipt;
import org.core.knowledge.service.RetryServiceV2;
import org.core.knowledge.service.RetryServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-20
 */
@Slf4j
@RestController
public class RetryController {
	
	private @Autowired RetryService retryService;
	private @Autowired RetryServiceV2 retryService2;
	private @Autowired RetryServiceV3 retryService3;
	
	@GetMapping("/v1/retry/{name}")
	public String testRetry(@PathVariable String name) {
		log.info("name => {}",name);
		return this.retryService.testRetry(name);
	}
	
	@GetMapping("/v2/retry/{name}")
	public Receipt testRetry2(@PathVariable String name) {
		log.info("name => {}",name);
		return this.retryService2.testRetry(name);
	}
	
	@GetMapping("/v3/retry/{name}")
	public Receipt testRetry3(@PathVariable String name) {
		log.info("name => {}",name);
		return this.retryService3.testRetry(name);
	}
}
