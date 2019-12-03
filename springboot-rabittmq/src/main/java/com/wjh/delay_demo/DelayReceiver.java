package com.wjh.delay_demo;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DelayReceiver {

    @Autowired
    SendServiceImpl sendService;

    Logger log = LoggerFactory.getLogger(DelayReceiver.class);
 
    @RabbitListener(queues = {DelayRabbitConfig.ORDER_QUEUE_NAME})
    public void orderNormalQueue(@Payload String message, Channel channel, Message messageer) throws Exception {
        System.out.println(message);
        sendService.sendDelay(message);
        channel.basicAck(messageer.getMessageProperties().getDeliveryTag(),false);
    }

}
