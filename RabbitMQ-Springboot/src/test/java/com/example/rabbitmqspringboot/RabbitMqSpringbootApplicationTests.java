package com.example.rabbitmqspringboot;

import com.example.rabbitmqspringboot.domin.User;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RabbitMqSpringbootApplicationTests {

    //RabbitTemplate为我们封装了大量的RabbitMQ操作，已经由Starter提供，因此直接注入使用即可
    @Resource
    RabbitTemplate template;


    //单纯发送消息到队列
    @Test
    void publisher() {
        //使用convertAndSend方法一步到位，参数基本和之前是一样的
        //最后一个消息本体可以是Object类型，真是大大的方便
        template.convertAndSend("amq.direct", "my-yyds", "Hello World!");
    }


    //发送消息后等待消费返回响应结果
    @Test
    void publishers() {
        //会等待消费者消费然后返回响应结果
        Object res = template.convertSendAndReceive("amq.direct", "my-yyds", "消息");
        System.out.println("收到消费者响应："+res);
    }


    //发送user消息到队列
    @Test
    void publisherUser() {
        //使用convertAndSend方法一步到位，参数基本和之前是一样的
        //最后一个消息本体可以是Object类型，真是大大的方便
        template.convertAndSend("amq.direct", "my-yyds", new User(12,"测试发送消息"));
    }

}
