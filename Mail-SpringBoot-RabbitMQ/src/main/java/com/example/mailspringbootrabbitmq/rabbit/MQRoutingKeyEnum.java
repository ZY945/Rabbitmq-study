package com.example.mailspringbootrabbitmq.rabbit;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MQRoutingKeyEnum {


    SEND_FENPEI_EMAIL("inform.fenpei.email", "邮件分配通知");
    private String routingKey;
    private String routingName;
}
