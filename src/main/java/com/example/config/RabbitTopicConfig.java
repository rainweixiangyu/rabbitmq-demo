package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopicConfig {

  private static final String MESSAGE = "topic.message";
  private static final String MESSAGES = "topic.message.s";
  private static final String YMQ = "topic.ymq";
  private static final String TOPIC_EXCHANGE = "topicExchange";

  @Bean
  public Queue queueMessage(){
    return new Queue(MESSAGE);
  }

  @Bean
  public Queue queueMessages(){
    return new Queue(MESSAGES);
  }

  @Bean
  public Queue queueYmq(){
    return new Queue(YMQ);
  }

  /**
   * 交换机(Exchange) 描述：接收消息并且转发到绑定的队列，交换机不存储消息
   */
  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(TOPIC_EXCHANGE);
  }

  @Bean
  public Binding bindingExchangeMessage(Queue queueMessage, TopicExchange topicExchange){
    return BindingBuilder.bind(queueMessage).to(topicExchange).with("topic.message");
  }

  @Bean
  public Binding bindingExchangeMessages(Queue queueMessages, TopicExchange topicExchange){
    return BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.message.#");
  }

  @Bean
  public Binding bindingExchangeYmq(Queue queueYmq, TopicExchange topicExchange){
    return BindingBuilder.bind(queueYmq).to(topicExchange).with("topic.#");
  }
}
