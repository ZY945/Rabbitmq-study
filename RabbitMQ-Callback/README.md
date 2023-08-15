# ConfirmCallback

- 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达
  Exchange 中</br>

# ReturnCallback

- 启动消息失败返回，比如路由不到队列时触发回调

# 消息确认模式

- AcknowledgeMode.NONE：自动确认
- AcknowledgeMode.AUTO：根据情况确认
- AcknowledgeMode.MANUAL：手动确认

```text
spring:
  rabbitmq:
    listener:
      direct:
        acknowledge-mode: manual
```