package com.example.rabbitmqexchangeheaders.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component  //注册为bean
public class TestListener {

    /**
     * 接收消息并返回响应
     */
    @RabbitListener(queues = "yyds")//定义此方法到yyds队列
    public void receiver(String data) {
        System.out.println("一号消息队列监听器 " + data);
    }

}