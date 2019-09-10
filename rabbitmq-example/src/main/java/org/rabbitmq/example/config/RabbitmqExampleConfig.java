/**
 * 
 */
package org.rabbitmq.example.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * rabbitmq-example
 * <description></description>
 * @author Johnny Liu
 * @date 2019-09-03
 */
@Configuration
@EnableRabbit
public class RabbitmqExampleConfig implements RabbitListenerConfigurer{
	
	public @Bean RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
	
	public @Bean TopicExchange exchange(RabbitAdmin rabbitAdmin) {
		Map<String,Object> args = new HashMap<>();
		args.put("itstrange-exchange", "itstrange.exchange");
		TopicExchange exchange = new TopicExchange("itstrange.exchange",true,false,args);
		rabbitAdmin.declareExchange(exchange);
		return exchange;
	}
	
	public @Bean RabbitMQFactory rabbitMQFactory(@Qualifier("rabbitAdmin") RabbitAdmin rabbitAdmin,@Qualifier("exchange") TopicExchange exchange) {
		return new RabbitMQFactory(rabbitAdmin,exchange);
	}
	
	public @Bean MappingJackson2MessageConverter jackson2Converter() {
		return new MappingJackson2MessageConverter();
	}
	
	public @Bean MessageConverter  jackson2Converter2() {
		return new Jackson2JsonMessageConverter();
	}
	
	public @Bean RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jackson2Converter2());
		return template;
	}
	
	public @Bean DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(jackson2Converter());
		return factory;
	}

	public void configureRabbitListeners(RabbitListenerEndpointRegistrar arg0) {
		arg0.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

}
