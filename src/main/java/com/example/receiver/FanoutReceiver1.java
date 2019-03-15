package com.example.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "baidu.com")
public class FanoutReceiver1 {

  @RabbitHandler
  public void process(String message){
    System.out.println("FanoutReceiver1:" + message);
  }
}
