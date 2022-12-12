package com.example.mailspringbootrabbitmq;

import com.alibaba.fastjson.JSON;
import com.example.mailspringbootrabbitmq.Bean.WorkOrder;
import com.example.mailspringbootrabbitmq.rabbit.MQRoutingKeyEnum;
import com.example.mailspringbootrabbitmq.rabbit.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class MailSpringBootRabbitMqApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
//        MailBean mailBean = new MailBean("zhangyang_2002@foxmail.com","派单内容","工程师姓名,工程师电话,工程师到达时间");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(simpleDateFormat.format(date));//2022-12-12 19:42:17
        String format = simpleDateFormat.format(date);

        WorkOrder workOrder = new WorkOrder();
        workOrder.setMail("zhangyang_2002@foxmail.com");
        workOrder.setArrivalTime(format);
        workOrder.setName("张三");
        workOrder.setPhone("123456789");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPICS_INFORM, MQRoutingKeyEnum.SEND_FENPEI_EMAIL.getRoutingKey(), JSON.toJSONString(workOrder));

    }

}
