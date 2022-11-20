package com.example.rabbitmqrouting.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2022/11/19 9:46
 */
@Component  //注册为bean
public class TestListener {

    /**
     * 测试
     */
//    @RabbitListener(queues = "yyds")//定义此方法到yyds队列
//    public void receiver(String data){
//        System.out.println("一号消息队列监听器 "+data);
//    }

    @RabbitListener(queues = "yyds")//定义此方法到yyds队列
    public void receiver(String data){
        System.out.println("一号消息队列监听器 "+data);
    }
}
