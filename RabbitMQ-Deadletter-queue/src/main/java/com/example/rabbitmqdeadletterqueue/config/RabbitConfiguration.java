package com.example.rabbitmqdeadletterqueue.config;

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

    /**
     * 创建一个用于JSON转换的Bean
     */
    @Bean("jacksonConverter")
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    //配置死信

    /**
     * 创建一个新的死信交换机
     */
    @Bean("directDlExchange")
    public Exchange dlExchange() {
        return ExchangeBuilder
                .directExchange("dlx.direct")
                .build();
    }

    /**
     * 创建一个新的死信队列
     */
    @Bean("yydsDlQueue")
    public Queue dlQueue() {
        return QueueBuilder
                .nonDurable("dl-yyds")
                .build();
    }

    /**
     * 将死信交换机和队列进行绑定
     */
    @Bean("dlBinding")
    public Binding dlBingding(@Qualifier("directDlExchange") Exchange dlExchange,
                              @Qualifier("yydsDlQueue") Queue dlQueue) {
        return BindingBuilder
                .bind(dlQueue)
                .to(dlExchange)
                .with("dl-yyds")
                .noargs();
    }


    //配置正常

    /**
     * 绑定
     */
    @Bean("directExchange")//定义交换机Bean，可以很多个
    public Exchange exchange() {
        return ExchangeBuilder.directExchange("amq.direct").build();
    }

    /**
     * 配置并创建队列
     * 指定死信交换机和RoutingKey，并创建
     */
    @Bean("yydsQueue")
    public Queue queue() {
        return QueueBuilder
                .nonDurable("yyds")
                .deadLetterExchange("dlx.direct")   //指定死信交换机
                .deadLetterRoutingKey("dl-yyds")   //指定死信RoutingKey
//                .ttl(5000) //设置5秒超时
                .maxLength(3)//设置最大长度
                .build();
    }

    /**
     * 将正常交换机和队列进行绑定
     */
    @Bean("bingding")
    public Binding binding(@Qualifier("directExchange") Exchange exchange,
                           @Qualifier("yydsQueue") Queue queue) {
        return BindingBuilder
                .bind(queue)   //绑定队列
                .to(exchange)  //到交换机
                .with("my-yyds")   //使用自定义的routingKey
                .noargs();
    }
}
