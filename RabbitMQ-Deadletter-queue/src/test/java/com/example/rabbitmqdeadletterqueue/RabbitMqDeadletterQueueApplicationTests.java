package com.example.rabbitmqdeadletterqueue;

import com.example.rabbitmqdeadletterqueue.domin.User;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RabbitMqDeadletterQueueApplicationTests {

    //RabbitTemplate为我们封装了大量的RabbitMQ操作，已经由Starter提供，因此直接注入使用即可
    @Resource
    RabbitTemplate template;

    @Test
    void publisher() {
        for (int i = 1; i <= 4; i++)
            template.convertAndSend("amq.direct", "my-yyds", new User(i, "第" + i + "个消息"));
    }

}
