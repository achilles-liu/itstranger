/**
 * 
 */
package org.rabbitmq.example.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

/**
 * rabbitmq-example
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-04
 */
@Slf4j
@RestController
public class ConfirmController {
	private @Autowired RabbitTemplate rabbitTpl;

	@GetMapping("/pconfirm")
	public String producerConfirm(String label) throws IOException {
		log.info("label:{}",label);
		ConnectionFactory fac = this.rabbitTpl.getConnectionFactory();
		Connection con = fac.createConnection();
		Channel channel = con.createChannel(false);
		channel.confirmSelect();
		channel.addConfirmListener((long deliveryTag, boolean multiple) -> {
			// Ack
			log.info("Ack --- deliveryTag:{},multiple:{}",deliveryTag,multiple);
		},(long deliveryTag, boolean multiple) -> {
			// Nack
			log.info("Nack --- deliveryTag:{},multiple:{}",deliveryTag,multiple);
		});
		String body = "this is from "+label;
		channel.basicPublish("itstrange.exchange", "itstrange-example-mq", true, com.rabbitmq.client.MessageProperties.PERSISTENT_BASIC, body.getBytes());
		con.close();
		log.info("send done!");
		return "done";
	}
	
	@GetMapping("/cconfirm")
	public String consumerConfirm(String label) throws IOException {
		log.info("label:{}",label);
		MessageProperties prop = new MessageProperties();
		prop.setMessageId(UUID.randomUUID().toString());
		prop.setContentType("text/plain");
		prop.setContentEncoding("utf-8");
		Message msg = new Message(label.getBytes(), prop);
		this.rabbitTpl.convertAndSend("itstrange.exchange", "itstrange-example-mq", msg);
		this.rabbitTpl.setConfirmCallback((CorrelationData correlationData, boolean ack,String cause) -> {
			if(ack) {
				log.info("消息id为:"+correlationData+"的消息，已经被ack成功");
			}else{
				log.error("消息id为:"+correlationData+"的消息，消息nack，失败的原因："+cause);
			}
		});
		log.info("send done!");
		return "done";
	}
}
