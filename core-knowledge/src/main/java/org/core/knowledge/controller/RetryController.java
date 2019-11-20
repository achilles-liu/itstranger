/**
 * 
 */
package org.core.knowledge.controller;

import org.core.knowledge.service.RetryService;
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
	
	@GetMapping("/retry/{name}")
	public String testRetry(@PathVariable String name) {
		log.info("name => {}",name);
		return this.retryService.testRetry(name);
	}
}
