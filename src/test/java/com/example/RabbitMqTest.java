package com.example;

import com.example.receiver.HeadersReceive;
import com.example.sender.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {

  private ExecutorService executorService = Executors.newFixedThreadPool(10);

  @Value("spring.rabbitmq.host")
  private String rabbitmq_host;

  @Value("spring.rabbitmq.username")
  private String rabbitmq_user;

  @Value("spring.rabbitmq.password")
  private String rabbitmq_pwd;

  @Autowired
  private HelloSender helloSender;

  @Autowired
  private DirectSender directSender;

  @Autowired
  private FanoutSender fanoutSender;

  @Autowired
  private TopicSender topicSender;

  @Autowired
  private HeadersSender headersSender;

  @Autowired
  private HeadersReceive headersReceive;

  @Autowired
  private HeaderSend headerSend;

  @Test
  public void hello() throws Exception {
    helloSender.send();
  }

  @Test
  public void direct(){
    directSender.send();
  }

  @Test
  public void fanout(){
    String exchange = "fanoutExchange";
    String routingKey = "baidu.com";
    String message = "Message from Fanout";
    fanoutSender.send(exchange, routingKey, message);
  }

  @Test
  public void topicMessageTest(){
    String exchange = "topicExchange";
    String routeKey = "topic.message";
    String message = "Message from topic.message.";

    topicSender.send(exchange, routeKey, message);
  }

  @Test
  public void topicMessagesTest(){
    String exchange = "topicExchange";
    String routeKey = "topic.message.s";
    String message = "Message from topic.message.s";

    topicSender.send(exchange, routeKey, message);
  }

  @Test
  public void topicYmqTest(){
    String exchange = "topicExchange";
    String routeKey = "topic.ymq";
    String message = "Message from topic.ymq";

    topicSender.send(exchange, routeKey, message);
  }

  @Test
  public void headersAllTest(){
    String exchange = "headersExchange";
    String systemErrorLog = "System Error Log.";
    Message systemErrorMessage = MessageBuilder.withBody(systemErrorLog.getBytes())
            .setHeader("layer","system")
            .setHeader("level","error")
            .build();

    headersSender.send(exchange,systemErrorMessage);
  }

  @Test
  public void header() throws InterruptedException {

    // 消费者1：绑定 format=pdf,type=report
    executorService.submit(() -> {
      Map<String,Object> headers = new HashMap();
      headers.put("format","pdf");
      headers.put("type","report");
      headers.put("x-match","all");
      headersReceive.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
    });

    // 消费者2：绑定  format=pdf,type=log
    executorService.submit(() -> {
      Map<String,Object> headers = new HashMap();
      headers.put("format","pdf");
      headers.put("type","log");
      headers.put("x-match","any");
      headersReceive.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
    });

    // 消费者3：绑定  format=zip,type=report
    executorService.submit(() -> {
      Map<String,Object> headers = new HashMap();
      headers.put("format","zip");
      headers.put("type","report");
      headers.put("x-match","all");
      headersReceive.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
    });

    Thread.sleep(2* 1000);
    System.out.println("=============消息1===================");
    // 生产者1 ： format=pdf,type=reprot,x-match=all
    executorService.submit(() -> {
      Map<String,Object> headers = new HashMap();
      headers.put("format","pdf");
      headers.put("type","report");
      HeaderSend.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
    });

    Thread.sleep(5* 100);
    System.out.println("=============消息2===================");
    // 生产者2 ： format=pdf,x-match=any
    executorService.submit(() -> {
      Map<String,Object> headers = new HashMap();
      headers.put("format","pdf");
      HeaderSend.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
    });

    Thread.sleep(5* 100);
    System.out.println("=============消息3===================");
    // 生产者3 ： format=zip,type=log,x-match=all
    executorService.submit(() -> {
      Map<String,Object> headers = new HashMap();
      headers.put("format","zip");
      headers.put("type","log");
      HeaderSend.execute(rabbitmq_host, rabbitmq_user, rabbitmq_pwd, headers);
    });

    // sleep 10s
    Thread.sleep(10 * 1000);
  }

}
