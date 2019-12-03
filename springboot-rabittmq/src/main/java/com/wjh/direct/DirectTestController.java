package com.wjh.direct;

import com.wjh.entity.Mail;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mq")
public class DirectTestController implements RabbitTemplate.ReturnCallback  {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value="/produce",produces = {"application/json;charset=UTF-8"})
    public void produce(String rkey) throws Exception{
        Mail m=new Mail("1","qqq",1f);
        rabbitTemplate.setReturnCallback(this);
        /*rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
            } else {
                System.out.println("HelloSender 消息发送成功 ");
            }
        });*/
        rabbitTemplate.convertAndSend("direct", rkey, m);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("sender return success" + message.toString()+"==="+replyCode+"==="+replyText+"==="+routingKey);
    }
}
