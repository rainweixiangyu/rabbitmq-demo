package com.example.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectSender {

  @Autowired
  private AmqpTemplate rabbitTemplate;

  public void send(){
    String routeKey = "directRountKey";
    String exchange = "directExchange";
    String context = "Direct Message.";
    System.out.println("DirectSender:"+context);

    this.rabbitTemplate.convertAndSend(exchange, routeKey, context);
  }
}
