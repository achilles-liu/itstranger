/**
 * 
 */
package org.rabbitmq.example.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

/**
 * rabbitmq-example
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-03
 */
@Slf4j
@Component
@RabbitListener(queues= {"itstrange-example-mq"})
public class ExampleConsumer {
	
	/*@RabbitHandler
	public void process2(User msg) throws Exception {
		System.out.println(msg);
	}*/
	
	/*@RabbitHandler
	public void producerAck(Object message,Channel channel) {
		log.info("{}",message);
	}*/
	
	@RabbitHandler
	public void consumerAck(Object message,Channel channel) throws Exception {
		Message msg = (Message)message;
		String msgId = msg.getMessageProperties().getMessageId();
		log.info("msgId:{}",msgId);
		//channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
	}
}
