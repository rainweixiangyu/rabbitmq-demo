package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitDirectConfig {

  private static final String HELLO_QUEUE = "hello";
  private static final String DIRECT_QUEUE = "direct";
  private static final String DIRECT_EXCHANGE = "directExchange";
  private static final String DIRECT_ROUNTING_KEY = "directRountKey";


  @Bean
  public Queue queue(){
    return new Queue(HELLO_QUEUE);
  }

  @Bean
  public Queue directQueue(){
    return new Queue(DIRECT_QUEUE);
  }

  //----------配置默认的交换机模式，可以不需要配置以下----------
  @Bean
  DirectExchange directExchange(){
    return new DirectExchange(DIRECT_EXCHANGE);
  }

  @Bean
  Binding bindingExchangeQueue(Queue directQueue, DirectExchange directExchange){
    return BindingBuilder.bind(directQueue).to(directExchange).with(DIRECT_ROUNTING_KEY);
  }
}
