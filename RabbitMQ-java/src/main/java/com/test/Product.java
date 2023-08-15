package com.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 伍六七
 * @date 2022/11/18 22:25
 */
public class Product {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        //设置连接
        factory.setHost("192.168.80.12");
        factory.setPort(5672);//注意这里写5672，是amqp协议端口
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setVirtualHost("/test");

        //创造连接
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare("yyds", false, false, false, null);
            channel.queueBind("yyds", "amq.direct", "my-yyds");
            channel.basicPublish("amq.direct", "my-yyds", null, "Hello World!".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
