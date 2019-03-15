package com.example.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "systemQueue")
public class HeadersReceiver1 {

  @RabbitHandler
  public void process(String message){
    System.out.println("HeadersReceiver1:" + message);
  }
}
