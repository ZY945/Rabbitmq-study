package com.example.rabbitmq.Callback.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 伍六七
 * @date 2022/11/19 9:12
 */
@Configuration
public class RabbitConfiguration {
    public static final String EXCHANGE_CALLBACK_TEST = "test.callback";


    public static final String QUEUE_CALLBACK_TEST = "queue_test_callback";


    public static final String ROUTINGKEY_CALLBACK_TEST = "queue_test_callback";

    @Bean(EXCHANGE_CALLBACK_TEST)//定义交换机Bean，可以很多个
    public Exchange EXCHANGE_CALLBACK_TEST() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_CALLBACK_TEST).durable(true).build();
    }

    @Bean(QUEUE_CALLBACK_TEST)       //定义消息队列
    public Queue QUEUE_CALLBACK_TEST() {
//        return QueueBuilder
//                .nonDurable(QUEUE_CALLBACK_TEST)     //非持久化类型
//                .build();
        return new Queue(QUEUE_CALLBACK_TEST);
    }

    @Bean("jacksonConverter")   //直接创建一个用于JSON转换的Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean("bingding")
    public Binding binding(@Qualifier(EXCHANGE_CALLBACK_TEST) Exchange exchange,
                           @Qualifier(QUEUE_CALLBACK_TEST) Queue queue) {
        //将我们刚刚定义的交换机和队列进行绑定
        return BindingBuilder
                .bind(queue)   //绑定队列
                .to(exchange)  //到交换机
                .with(ROUTINGKEY_CALLBACK_TEST)   //使用自定义的routingKey
                .noargs();
    }

}
