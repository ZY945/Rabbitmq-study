package com.example.rabbitmqpublishsubscribe.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2022/11/19 9:46
 */
@Component
public class TestListener {
    @RabbitListener(queues = "yyds1")
    public void receiver(String data){   //这里直接接收String类型的数据
        System.out.println("邮件消息队列监听器 "+data);
    }

    @RabbitListener(queues = "yyds2")
    public void receiver2(String data){
        System.out.println("短信消息队列监听器 "+data);
    }

}