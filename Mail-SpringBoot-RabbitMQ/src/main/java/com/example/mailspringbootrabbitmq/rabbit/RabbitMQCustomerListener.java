package com.example.mailspringbootrabbitmq.rabbit;

import com.alibaba.fastjson.JSON;
import com.example.mailspringbootrabbitmq.Bean.MailVo;
import com.example.mailspringbootrabbitmq.Bean.WorkOrder;
import com.example.mailspringbootrabbitmq.mail_test.MailServiceImpl;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQCustomerListener {


    @Autowired
    private MailServiceImpl mailService;

    //监听email队列
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(Object msg, Message message, Channel channel) {

        String body = new String(message.getBody());
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (receivedRoutingKey.equals(MQRoutingKeyEnum.SEND_FENPEI_EMAIL.getRoutingKey())) {
            //分配邮件
            WorkOrder workOrder = JSON.parseObject(body, WorkOrder.class);
            MailVo mailVo = new MailVo();
            mailVo.setTo(workOrder.getMail());
            mailVo.setSub("工单提示");
            mailVo.setText(workOrder.getName()+","+workOrder.getPhone()+","+workOrder.getArrivalTime());
            mailService.sendSimpleMail(mailVo);
        }
    }

}
