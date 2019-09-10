/**
 * 
 */
package org.rabbitmq.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.InitializingBean;

/**
 * rabbitmq-example
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-03
 */
public class RabbitMQFactory implements InitializingBean{
	
	private RabbitAdmin rabbitAdmin;
	private TopicExchange exchange;
	
	public RabbitMQFactory(RabbitAdmin rabbitAdmin,TopicExchange exchange) {
		this.rabbitAdmin = rabbitAdmin;
		this.exchange = exchange;
	}

	public void afterPropertiesSet() throws Exception {
		Queue queue = new Queue("itstrange-example-mq");
		this.rabbitAdmin.declareQueue(queue);
		Binding exampleBinding = BindingBuilder.bind(queue).to(this.exchange).with("itstrange-example-mq");
		this.rabbitAdmin.declareBinding(exampleBinding);
	}

}
