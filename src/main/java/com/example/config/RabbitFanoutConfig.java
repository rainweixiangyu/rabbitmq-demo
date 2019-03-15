package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitFanoutConfig {

  private static final String BAIDU_QUEUE = "baidu.com";
  private static final String GOOGLE_QUEUE = "google.com";
  private static final String FANOUT_EXCHANGE = "fanoutExchange";

  @Bean
  public Queue baiduQueue(){
    return new Queue(BAIDU_QUEUE);
  }

  @Bean
  public Queue googleQueue(){
    return new Queue(GOOGLE_QUEUE);
  }

  /**
   * 任何发送到Fanout Exchange的消息都会被转发到与该Exchange绑定(Binding)的所有队列上。
   */
  @Bean
  public FanoutExchange fanoutExchange() {
    return new FanoutExchange(FANOUT_EXCHANGE);
  }

  @Bean
  public Binding bindingExchangeBaiduQueue(Queue baiduQueue, FanoutExchange fanoutExchange){
    return BindingBuilder.bind(baiduQueue).to(fanoutExchange);
  }

  @Bean
  public Binding bindingExchangeGoogleQueue(Queue googleQueue, FanoutExchange fanoutExchange){
    return BindingBuilder.bind(googleQueue).to(fanoutExchange);
  }
}
