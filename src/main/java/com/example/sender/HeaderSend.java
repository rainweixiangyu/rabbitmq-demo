package com.example.sender;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HeaderSend {
  private static final String EXCHANGE_NAME = "header-exchange";

  public static void execute(String host, String userName, String password, Map<String,Object> headers ) {
    // 配置连接工厂
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(host);
    factory.setUsername(userName);
    factory.setPassword(password);

    Connection connection = null;
    Channel channel = null;
    try {
      // 建立TCP连接
      connection = factory.newConnection();
      // 在TCP连接的基础上创建通道
      channel = connection.createChannel();
      // 声明一个headers交换机
      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);
      String message = "headers-" + System.currentTimeMillis();

      // 生成发送消息的属性
      AMQP.BasicProperties props = new AMQP.BasicProperties
              .Builder()
              .headers(headers)
              .build();

      // 发送消息，并配置消息发展
      channel.basicPublish(EXCHANGE_NAME, "", props, message.getBytes("UTF-8"));
      System.out.println(" [HeaderSend] Sent '" + headers + "':'" + message + "'");
    }
    catch  (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (connection != null) {
        try {
          connection.close();
        }
        catch (Exception ignore) {}
      }
    }
  }
}
