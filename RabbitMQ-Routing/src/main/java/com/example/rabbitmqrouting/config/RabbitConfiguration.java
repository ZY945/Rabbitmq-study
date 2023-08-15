package com.example.rabbitmqrouting.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 伍六七
 * @date 2022/11/19 9:12
 */
@Configuration
public class RabbitConfiguration {

    @Bean("directExchange")
    public Exchange exchange() {
        return ExchangeBuilder.directExchange("amq.direct").build();
    }

    @Bean("yydsQueue")
    public Queue queue() {
        return QueueBuilder.nonDurable("yyds").build();
    }

    @Bean("binding")   //使用yyds1绑定
    public Binding binding(@Qualifier("directExchange") Exchange exchange,
                           @Qualifier("yydsQueue") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("yyds1")
                .noargs();
    }

    @Bean("binding2")   //使用yyds2绑定
    public Binding binding2(@Qualifier("directExchange") Exchange exchange,
                            @Qualifier("yydsQueue") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("yyds2")
                .noargs();
    }
}
