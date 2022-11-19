package com.example.rabbitmqworkqueue.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author 伍六七
 * @date 2022/11/19 9:12
 */
@Configuration
public class RabbitConfiguration {
    /**
     * 绑定
     */
    @Bean("directExchange")//定义交换机Bean，可以很多个
    public Exchange exchange(){
        return ExchangeBuilder.directExchange("amq.direct").build();
    }

    /**
     * 配置并创建队列
     * 指定死信交换机和RoutingKey，并创建
     */
    @Bean("yydsQueue")
    public Queue queue(){
        return QueueBuilder
                .nonDurable("yyds")
                .build();
    }

    /**
     * 将正常交换机和队列进行绑定
     */
    @Bean("bingding")
    public Binding binding(@Qualifier("directExchange")Exchange exchange,
                           @Qualifier("yydsQueue")Queue queue){
        return BindingBuilder
                .bind(queue)   //绑定队列
                .to(exchange)  //到交换机
                .with("my-yyds")   //使用自定义的routingKey
                .noargs();
    }

    @Resource
    private CachingConnectionFactory connectionFactory;

    @Bean(name = "listenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(1);   //将PrefetchCount设定为1表示一次只能取一个
        return factory;
    }
}
