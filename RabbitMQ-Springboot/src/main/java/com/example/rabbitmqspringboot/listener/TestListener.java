package com.example.rabbitmqspringboot.listener;

import com.example.rabbitmqspringboot.domin.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2022/11/19 9:46
 */
@Component  //注册为bean
public class TestListener {

    /**
     * 接收格式为Message
     */
//    @RabbitListener(queues = "yyds")//定义此方法到yyds队列
//    public void receiver(Message message){
//        System.out.println("一号消息队列监听器 "+message);
//    }

    /**
     * 接收格式为Message--把消息格式转化为String
     */
//    @RabbitListener(queues = "yyds")//定义此方法到yyds队列
//    public void receiver(String data){
//        System.out.println("一号消息队列监听器 "+data);
//    }

    /**
     * 接收消息并返回响应
     */
//    @RabbitListener(queues = "yyds")//定义此方法到yyds队列
//    public String receiver(String data){
//        System.out.println("一号消息队列监听器 "+data);
//        return "一号收到消息";
//    }


    /**
     * 指定messageConverter为我们刚刚创建的Bean名称
     */
    @RabbitListener(queues = "yyds", messageConverter = "jacksonConverter")
    public void receiver(User user) {  //直接接收User类型
        System.out.println(user);
    }

}
