package com.example.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {

  @Autowired
  AmqpTemplate rabbitTemplate;

  public void send(String exchange, String routingKey, String message){
    System.out.println("Exchange:"+exchange+"-routingKey:"+routingKey +"-Message:"+message);
    this.rabbitTemplate.convertAndSend(exchange,routingKey, message);
  }
}
