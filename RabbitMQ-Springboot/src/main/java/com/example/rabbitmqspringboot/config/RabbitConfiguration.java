package com.example.rabbitmqspringboot.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Exchanger;

/**
 * @author 伍六七
 * @date 2022/11/19 9:12
 */
@Configuration
public class RabbitConfiguration {

    @Bean("directExchange")//定义交换机Bean，可以很多个
    public Exchange exchange(){
        return ExchangeBuilder.directExchange("amq.direct").build();
    }

    @Bean("yydsQueue")       //定义消息队列
    public Queue queue(){
        return QueueBuilder
                .nonDurable("yyds")     //非持久化类型
                .build();
    }

    @Bean("jacksonConverter")   //直接创建一个用于JSON转换的Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean("bingding")
    public Binding binding(@Qualifier("directExchange")Exchange exchange,
                           @Qualifier("yydsQueue")Queue queue){
        //将我们刚刚定义的交换机和队列进行绑定
        return BindingBuilder
                .bind(queue)   //绑定队列
                .to(exchange)  //到交换机
                .with("my-yyds")   //使用自定义的routingKey
                .noargs();
    }

}
