package com.wjh.delay_demo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendServiceImpl {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendNormal(String rkey) throws Exception{
        rabbitTemplate.convertAndSend(DelayRabbitConfig.ORDER_EXCHANGE_NAME, DelayRabbitConfig.ORDER_ROUTING_KEY, rkey);
    }

    public void sendDelay(String rkey) throws Exception{
        rabbitTemplate.convertAndSend(DelayRabbitConfig.ORDER_DELAY_EXCHANGE, DelayRabbitConfig.ORDER_DELAY_ROUTING_KEY, rkey);
    }

}
