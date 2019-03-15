package com.example.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeadersSender {

  @Autowired
  private AmqpTemplate rabbitMQTemplate;

  public void send(String exchange, Message message){
    System.out.println("Sender --> Exchange:"+exchange+"-Message:"+message.toString());
    this.rabbitMQTemplate.send(exchange,"",message);
  }
}
