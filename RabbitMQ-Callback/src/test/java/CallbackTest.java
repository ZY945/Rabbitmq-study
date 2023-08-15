import com.example.rabbitmq.Callback.RabbitMqSpringbootApplication;
import com.example.rabbitmq.Callback.config.RabbitConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author 伍六七
 * @date 2023/8/15 15:49
 */
@SpringBootTest(classes = {RabbitMqSpringbootApplication.class})
@Slf4j
public class CallbackTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private void init() {
        rabbitTemplate.setConfirmCallback(((correlationData, b, s) -> {
            log.info("进入confirm方法");
            String id = correlationData == null ? "" : correlationData.getId();
            Message msg = correlationData == null ? null : correlationData.getReturnedMessage();
            if (msg == null || ObjectUtils.isEmpty(msg.getBody())) {
                log.error("MQ的确认机制ReturnedMessage不允许为空!");
                return;
            }
            String json = new String(msg.getBody(), StandardCharsets.UTF_8);
            log.info("离开confirm方法");
            //后续逻辑
        }));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("进入return方法");
            MessageProperties messageProperties = message.getMessageProperties();
            Object header = messageProperties.getHeader("spring_returned_message correlation");
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            log.error("交换机: {}，路由key: {}，原因: {}，退回了消息:{}", exchange, replyCode, replyText, message);
            log.info("离开return方法");
        });
    }

    private CorrelationData getId() {
        //correlationDataId相当于消息的唯一表示
        UUID correlationDataId = UUID.randomUUID();
        return new CorrelationData(correlationDataId.toString());
    }

    /**
     * 测试能否正常发送消息
     */
    @Test
    public void test() {
        rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE_CALLBACK_TEST,
                RabbitConfiguration.ROUTINGKEY_CALLBACK_TEST, "测试消息");
    }

    @Test
    public void testGoodRoute() {
        init();
        rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE_CALLBACK_TEST,
                RabbitConfiguration.ROUTINGKEY_CALLBACK_TEST, "测试消息",
                getId());
    }

    @Test
    public void testBadRoute() {
        init();
        rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE_CALLBACK_TEST,
                "bad", "测试消息无法路由到queue触发returnCallBack",
                getId());
    }
}
