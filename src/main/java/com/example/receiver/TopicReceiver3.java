package com.example.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.ymq")
public class TopicReceiver3 {

  @RabbitHandler
  public void process(String message){
    System.out.println("TopicReceiver3:" + message);
  }
}
