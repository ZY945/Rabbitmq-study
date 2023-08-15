package com.example.rabbitmqpublishsubscribe.config;

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

    @Bean("fanoutExchange")
    public Exchange exchange() {
        //注意这里是fanoutExchange
        return ExchangeBuilder.fanoutExchange("amq.fanout").build();
    }

    @Bean("yydsQueue1")
    public Queue queue() {
        return QueueBuilder.nonDurable("yyds1").build();
    }

    @Bean("binding")
    public Binding binding(@Qualifier("fanoutExchange") Exchange exchange,
                           @Qualifier("yydsQueue1") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("yyds1")
                .noargs();
    }

    @Bean("yydsQueue2")
    public Queue queue2() {
        return QueueBuilder.nonDurable("yyds2").build();
    }

    @Bean("binding2")
    public Binding binding2(@Qualifier("fanoutExchange") Exchange exchange,
                            @Qualifier("yydsQueue2") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("yyds2")
                .noargs();
    }
}
