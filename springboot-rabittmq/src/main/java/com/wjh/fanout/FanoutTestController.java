package com.wjh.fanout;

import com.wjh.entity.Mail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mq")
public class FanoutTestController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value="/fanout",produces = {"application/json;charset=UTF-8"})
    public void produce(String rkey) throws Exception{
        Mail m=new Mail("1",rkey,1f);
        rabbitTemplate.convertAndSend("fanoutExchange", rkey, rkey);
    }

}
