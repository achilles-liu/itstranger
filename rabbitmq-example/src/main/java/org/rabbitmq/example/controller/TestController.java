/**
 * 
 */
package org.rabbitmq.example.controller;

import java.io.IOException;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * rabbitmq-example
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-03
 */
@Slf4j
@RestController
public class TestController {
	
	@Data
	public static class User{
		private Long id;
		private String username;
		private String nickname;
	}
	
	private @Autowired RabbitTemplate rabbitTpl;
	
	@GetMapping("/testenv")
	public String testEnv() {
		User u = new User();
		u.setId(10L);
		u.setUsername("Jack Ma");
		u.setNickname("Jack");
		this.rabbitTpl.convertAndSend("itstrange-example-mq", u);
		return "test-env";
	}
	
	@GetMapping("/testtx")
	public String testTx() {
		ConnectionFactory cf = this.rabbitTpl.getConnectionFactory();
		Connection connection = cf.createConnection();
		// channel.txSelect()： 开启事务
		// channel.txCommit() ：提交事务
		// channel.txRollback() ：回滚事务
		// 建立信道 构造参数 true代表该信道开启 Transactional 事务模式
		Channel ch = connection.createChannel(true);
		long start = System.currentTimeMillis();
		for(int i = 0;i <= 1000;i++) {
			try {
				ch.basicPublish("itstrange.exchange", "itstrange-example-mq", true, 
						MessageProperties.PERSISTENT_BASIC, ("第" + (i + 1) + "条消息").getBytes());
				ch.txCommit();
			} catch (IOException e) {
				try {
					ch.txRollback();
				} catch (IOException e1) {
					log.error("{}",e1.getMessage());
				}
				log.error("{}",e.getMessage());
			}
		}
		log.info("事务方式单消息单事务提交下，10000条消息发送共耗时: " + (System.currentTimeMillis() - start) + "ms");
		return "test transaction";
	}
	
}
