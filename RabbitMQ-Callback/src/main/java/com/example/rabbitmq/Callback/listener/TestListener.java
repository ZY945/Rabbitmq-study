package com.example.rabbitmq.Callback.listener;

import com.example.rabbitmq.Callback.config.RabbitConfiguration;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2022/11/19 9:46
 */
@Component  //注册为bean
public class TestListener {


    /**
     * 指定messageConverter为我们刚刚创建的Bean名称
     */
    @RabbitListener(queues = RabbitConfiguration.QUEUE_CALLBACK_TEST)
    public void receiver(String msg) {  //直接接收User类型
        System.out.println("接受消息");
        System.out.println(msg);
    }

}
