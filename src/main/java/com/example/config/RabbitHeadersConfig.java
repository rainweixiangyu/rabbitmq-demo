package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitHeadersConfig {

  private static final String headersExchange = "headersExchange";
  private static final String systemQueue = "systemQueue";
  private static final String applicationQueue = "applicationQueue";

  @Bean
  public HeadersExchange headersExchange(){
    return new HeadersExchange(headersExchange);
  }

  @Bean
  public Queue systemQueue(){
    return new Queue(systemQueue);
  }

  @Bean
  public Queue applicationQueue(){
    return new Queue(applicationQueue);
  }



  @Bean
  public Binding bindAllSystemError(HeadersExchange headersExchange, Queue systemQueue){
    HashMap<String,Object> systemError = new HashMap<String,Object>(){
      {
        put("layer","system");
        put("level","error");
      }
    };
    return BindingBuilder.bind(systemQueue).to(headersExchange).whereAll(systemError).match();
  }

  @Bean
  public Binding bindAnyApplicationError(HeadersExchange headersExchange, Queue applicationQueue){
    HashMap<String,Object> applicationError = new HashMap<String,Object>(){
      {
        put("layer","application");
        put("level","error");
      }
    };

    return BindingBuilder.bind(applicationQueue).to(headersExchange).whereAny(applicationError).match();
  }
}
